package my.project.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import my.project.model.BetLine;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class BetLineRepository implements PanacheRepository<BetLine> {
    public BetLine findById(Long id) {
        return find("id", id).firstResult();
    }

    @Transactional
    public void delete(Long id) {
        BetLine betLine = findById(id);
        betLine.setUpdated(LocalDateTime.now());
        betLine.setDeleted(true);
        persist(betLine);
    }

    @Transactional
    public void update(Long id, String team1, String team2, Double win1, Double draw, Double win2) {
        BetLine betLine = findById(id);
        betLine.setTeam1(team1);
        betLine.setTeam2(team2);
        betLine.setWin1(win1);
        betLine.setDraw(draw);
        betLine.setWin2(win2);
        betLine.setUpdated(LocalDateTime.now());
        persist(betLine);
    }

    @Transactional
    public void create(String team1, String team2, Double win1, Double draw, Double win2) {
        BetLine betLine = new BetLine();
        betLine.setTeam1(team1);
        betLine.setTeam2(team2);
        betLine.setWin1(win1);
        betLine.setDraw(draw);
        betLine.setWin2(win2);
        betLine.setDeleted(false);
        betLine.setCreated(LocalDateTime.now());
        persist(betLine);
    }

}
