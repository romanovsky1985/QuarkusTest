package my.example.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import my.example.entity.BetLine;
import jakarta.persistence.EntityManager;
import jakarta.inject.Inject;
import org.hibernate.StatelessSession;
import io.quarkus.hibernate.orm.PersistenceUnit;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class BetLineRepository {
    @Inject
    @PersistenceUnit("<default>")
    StatelessSession originSession;

    public List<BetLine> getActual() {
        return originSession.createQuery("select b from BetLine b where b.deleted = false order by b.id", BetLine.class)
                .getResultList();
    }

    public void persistLine(BetLine line) {
        line.setId(null);
        line.setCreated(LocalDateTime.now());
        line.setUpdated(null);
        line.setDeleted(false);
        originSession.insert(line);
    }

    public void updateLine(long id, BetLine update) {
        BetLine line = originSession.createQuery("select b from BetLine b where b.id = :id", BetLine.class)
                .setParameter("id", id)
                .getSingleResult();
        line.setTeam1(update.getTeam1());
        line.setTeam2(update.getTeam2());
        line.setWin1(update.getWin1());
        line.setDraw(update.getDraw());
        line.setWin2(update.getWin2());
        line.setUpdated(LocalDateTime.now());
        originSession.update(line);
    }

    public void setDeleted(long id) {
        BetLine line = originSession.createQuery("select b from BetLine b where b.id = :id", BetLine.class)
                .setParameter("id", id)
                .getSingleResult();
        line.setUpdated(LocalDateTime.now());
        line.setDeleted(true);
        originSession.update(line);
    }

    public void setDeletedAll() {
        List<BetLine> lines = getActual();
        for (var line : lines) {
            line.setDeleted(true);
            line.setUpdated(LocalDateTime.now());
            originSession.update(line);
        }
    }
}
