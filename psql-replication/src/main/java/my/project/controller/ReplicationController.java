package my.project.controller;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import io.quarkiverse.freemarker.TemplatePath;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Qualifier;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import my.project.entity.BetLine;
import jakarta.persistence.EntityManager;
import io.quarkus.hibernate.orm.PersistenceUnit;
import my.project.service.ReplicationService;


import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.List;


@Path("/replication")
public class ReplicationController {
    @Inject
    @TemplatePath("index.html")
    private Template index;

    @Inject
    private ReplicationService replicationService;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getIndex() throws TemplateException, IOException {
        //List<BetLine> lines = entityManager.createQuery("SELECT b FROM BetLine b ORDER BY id").getResultList();
        replicationService.replicate();
        Map<String, Object> pageMap = new HashMap<>();
        //pageMap.put("lines", lines);
        StringWriter pageWriter = new StringWriter();
        index.process(pageMap, pageWriter);
        return pageWriter.toString();
    }

}
