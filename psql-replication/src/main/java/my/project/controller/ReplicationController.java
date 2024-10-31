package my.project.controller;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import io.quarkiverse.freemarker.TemplatePath;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import my.project.service.ReplicationService;

import java.io.IOException;
import java.io.StringWriter;


@Path("/replication")
public class ReplicationController {
    @Inject
    @TemplatePath("index.html")
    private Template index;

    @Inject
    ReplicationService replicationService;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getIndex() throws TemplateException, IOException {
        StringWriter pageWriter = new StringWriter();
        index.process(null, pageWriter);
        return pageWriter.toString();
    }

    @POST
    @Produces(MediaType.TEXT_HTML)
    public String post() throws  TemplateException, IOException {
        replicationService.replicate();
        return getIndex();
    }

}
