package my.project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class BetLine {
    @Id
    private Long id;
    private String team1;
    private String team2;
    private Double win1;
    private Double draw;
    private Double win2;
    private Boolean deleted;
    private LocalDateTime created;
    private LocalDateTime updated;

    public void setId(Long id) {
        this.id = id;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public void setWin1(Double win1) {
        this.win1 = win1;
    }

    public void setDraw(Double draw) {
        this.draw = draw;
    }

    public void setWin2(Double win2) {
        this.win2 = win2;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public Long getId() {
        return id;
    }

    public String getTeam1() {
        return team1;
    }

    public String getTeam2() {
        return team2;
    }

    public Double getWin1() {
        return win1;
    }

    public Double getDraw() {
        return draw;
    }

    public Double getWin2() {
        return win2;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }
}
