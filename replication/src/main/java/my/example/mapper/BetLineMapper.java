package my.example.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.MultivaluedMap;
import my.example.entity.BetLine;

@ApplicationScoped
public class BetLineMapper {
    public BetLine map(MultivaluedMap<String, String> mp) {
        BetLine line = new BetLine();
        line.setTeam1(mp.getFirst("team1"));
        line.setTeam2(mp.getFirst("team2"));
        line.setWin1(Double.parseDouble(mp.getFirst("win1")));
        line.setDraw(Double.parseDouble(mp.getFirst("draw")));
        line.setWin2(Double.parseDouble(mp.getFirst("win2")));
        return line;
    }
}
