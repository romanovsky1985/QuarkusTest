package my.example.service;

import io.quarkus.hibernate.orm.PersistenceUnit;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import my.example.entity.BetLine;
import org.hibernate.StatelessSession;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@ApplicationScoped
public class ReplicationService {

    public Map<String, Number> replicate(StatelessSession origin, StatelessSession replicant) {
        Map<String, Number> result = new HashMap<>();
        LocalDateTime maxCreated = replicant
                .createQuery("select max(b.created) from BetLine b", LocalDateTime.class)
                .getSingleResult();
        LocalDateTime maxUpdated = replicant
                .createQuery("select max(b.updated) from BetLine b", LocalDateTime.class)
                .getSingleResult();
        List<BetLine> createdLines;
        if (maxCreated == null) {
            createdLines = origin
                    .createQuery("select b from BetLine b", BetLine.class)
                    .getResultList();
        } else {
            createdLines = origin
                    .createQuery("select b from BetLine b where b.created > :maxCreated", BetLine.class)
                    .setParameter("maxCreated", maxCreated)
                    .getResultList();
        }
        for (var line : createdLines) {
            replicant.insert(line);
        }
        result.put("created", createdLines.size());
        List<BetLine> updatedLines;
        if (maxUpdated == null) {
            updatedLines = origin
                    .createQuery("select b from BetLine b where b.updated is not null", BetLine.class)
                    .getResultList();
        } else {
            updatedLines = origin
                    .createQuery("select b from BetLine b where b.updated > :maxUpdated", BetLine.class)
                    .setParameter("maxUpdated", maxUpdated)
                    .getResultList();
        }
        for (var line : updatedLines) {
            replicant.update(line);
        }
        result.put("updated", updatedLines.size());
        return result;
    }

}
