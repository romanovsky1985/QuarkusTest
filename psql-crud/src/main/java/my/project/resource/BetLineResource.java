package my.project.resource;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import io.quarkiverse.freemarker.TemplatePath;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import my.project.model.BetLine;
import my.project.repository.BetLineRepository;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.common.jaxrs.ResponseImpl;
import org.jboss.resteasy.reactive.server.handlers.ResponseHandler;
import org.jboss.resteasy.reactive.server.jaxrs.ResponseBuilderImpl;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import io.quarkus.panache.common.Sort;

@Path("/crud")
public class BetLineResource {
    @Inject
    @TemplatePath("index.html")
    private Template index;

    @Inject
    private BetLineRepository repository;

    public static URI indexUri = URI.create("/crud");

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getIndex() throws TemplateException, IOException {
        Map<String, Object> pageMap = new HashMap<>();


        pageMap.put("lines", repository.list("deleted", Sort.ascending("id"), Boolean.FALSE));
        StringWriter pageWriter = new StringWriter();
        index.process(pageMap, pageWriter);
        return pageWriter.toString();
    }

    @POST
    @Path("/delete/{id}")
    public RestResponse<String> delete(@PathParam("id") Long id) {
        repository.delete(id);
        return RestResponse.seeOther(indexUri);
    }

    @POST
    @Path("/update/{id}")
    public RestResponse<String> update(@PathParam("id") Long id,
            @FormParam("team1") String team1,
            @FormParam("team2") String team2,
            @FormParam("win1") Double win1,
            @FormParam("draw") Double draw,
            @FormParam("win2") Double win2

    ) {
        repository.update(id, team1, team2, win1, draw, win2);
        return RestResponse.seeOther(indexUri);
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public RestResponse<String> create(
            @FormParam("team1") String team1,
            @FormParam("team2") String team2,
            @FormParam("win1") Double win1,
            @FormParam("draw") Double draw,
            @FormParam("win2") Double win2
    ) {
        repository.create(team1, team2, win1, draw, win2);
        return RestResponse.seeOther(indexUri);
    }
}
