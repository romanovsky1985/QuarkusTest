package my.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import io.quarkus.hibernate.orm.PersistenceUnit;

@Entity
public class BetLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String team1;
    private String team2;
    private Double win1;
    private Double draw;
    private Double win2;

    private LocalDateTime created;
    private LocalDateTime updated;
    private Boolean deleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public Double getWin1() {
        return win1;
    }

    public void setWin1(Double win1) {
        this.win1 = win1;
    }

    public Double getDraw() {
        return draw;
    }

    public void setDraw(Double draw) {
        this.draw = draw;
    }

    public Double getWin2() {
        return win2;
    }

    public void setWin2(Double win2) {
        this.win2 = win2;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "BetLine{" +
                "id=" + id +
                ", team1='" + team1 + '\'' +
                ", team2='" + team2 + '\'' +
                ", win1=" + win1 +
                ", draw=" + draw +
                ", win2=" + win2 +
                ", created=" + created +
                ", updated=" + updated +
                ", deleted=" + deleted +
                '}';
    }

    public static BetLine random() {
        BetLine line = new BetLine();
        Random random = ThreadLocalRandom.current();
        List<String> teams = List.of("Спартак", "Торпедо", "Локомотив", "Зенит", "Кубань",
                "Динамо", "ЦСКА", "Ростов", "Терек", "Амкар", "Рубин", "Химки", "Ахмат");
        int i =0;
        int j = 0;
        while (i == j) {
            i = random.nextInt(teams.size());
            j = random.nextInt(teams.size());
        }
        line.setTeam1(teams.get(i));
        line.setTeam2(teams.get(j));
        line.setWin1(1.0 + 2.0 * (random.nextInt(100) / 100.0));
        line.setDraw(3.0 + random.nextInt(100) / 100.0);
        line.setWin2(1.0 + 2.0 * (random.nextInt(100) / 100.0));
        return line;
    }
}
