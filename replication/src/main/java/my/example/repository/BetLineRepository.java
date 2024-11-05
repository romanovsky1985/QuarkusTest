package my.example.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import my.example.entity.BetLine;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class BetLineRepository implements PanacheRepository<BetLine> {
    public List<BetLine> getActual() {
        return list("deleted", Sort.by("id"), Boolean.FALSE);
    }

    @Transactional
    public void persistLine(BetLine line) {
        line.setId(null);
        line.setCreated(LocalDateTime.now());
        line.setUpdated(null);
        line.setDeleted(false);
        persist(line);
    }

    @Transactional
    public void updateLine(long id, BetLine update) {
        BetLine line = findById(id);
        line.setTeam1(update.getTeam1());
        line.setTeam2(update.getTeam2());
        line.setWin1(update.getWin1());
        line.setDraw(update.getDraw());
        line.setWin2(update.getWin2());
        line.setUpdated(LocalDateTime.now());
    }

    @Transactional
    public void setDeleted(long id) {
        BetLine line = findById(id);
        line.setUpdated(LocalDateTime.now());
        line.setDeleted(true);
    }

    @Transactional
    public void setDeletedAll() {
        update("deleted = true");
    }
}
