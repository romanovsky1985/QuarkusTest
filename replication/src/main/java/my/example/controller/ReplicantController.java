package my.example.controller;

import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import my.example.entity.BetLine;
import my.example.mapper.BetLineMapper;
import my.example.repository.BetLineRepository;
import my.example.service.ReplicationService;
import org.hibernate.StatelessSession;
import io.quarkus.hibernate.orm.PersistenceUnit;

import java.util.List;

@Path("/replicant")
public class ReplicantController {
    @Location("replicant.html")
    Template replicantTemplate;

    @Inject
    @PersistenceUnit("replicant")
    StatelessSession replicantSession;

//    @Inject
//    StatelessSession originSession;

    @Inject
    ReplicationService replicationService;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String show() {
        List<BetLine> lines = replicantSession
                .createQuery("select b from BetLine b where b.deleted = false", BetLine.class)
                .getResultList();
        return replicantTemplate.data("lines", lines).render();
    }

}