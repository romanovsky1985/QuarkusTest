package my.example.controller;

import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.smallrye.common.annotation.Blocking;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import my.example.model.NaturalFactorization;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Path("/factorization")
public class FactorizationManager {
    private final Map<Long, String> data =
            new ConcurrentHashMap<>();

    @Location("manager.html")
    Template managerTemplate;

    @Channel("natural")
    Emitter<Long> naturalEmitter;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String show() {
        List<String> lines = data.keySet().stream()
                .sorted()
                .map(data::get)
                .toList();
        return managerTemplate.data("lines", lines).render();
    }

    @POST
    @Produces(MediaType.TEXT_HTML)
    public String append(@FormParam("number") Long number) {
        if (!data.containsKey(number)) {
            data.put(number, number + " = calculating...");
            naturalEmitter.send(number);
        }
        return show();
    }

    @Incoming("factorization")
    @Blocking
    public void set(NaturalFactorization factorization) {
        data.put(factorization.getNumber(), factorization.toString());
    }
}
