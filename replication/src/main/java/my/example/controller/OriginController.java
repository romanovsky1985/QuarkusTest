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

import java.util.List;

@Path("/origin")
public class OriginController {
    @Location("origin.html")
    Template originTemplate;

    @Inject
    BetLineRepository lineRepository;

    @Inject
    BetLineMapper lineMapper;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String show() {
        List<BetLine> lines = lineRepository.getActual();
        return originTemplate.data("lines", lines).render();
    }

    @POST
    @Produces(MediaType.TEXT_HTML)
    @Path("/generate")
    public String generate(@FormParam("count") Integer count) {
        for (int i = 0; i < count; i++) {
            lineRepository.persistLine(BetLine.random());
        }
        return show();
    }

    @POST
    @Produces(MediaType.TEXT_HTML)
    @Path("/delete/{id}")
    public String delete(@PathParam("id") Long id) {
        lineRepository.setDeleted(id);
        return show();
    }

    @POST
    @Produces(MediaType.TEXT_HTML)
    @Path("/clear")
    public String clear() {
        lineRepository.setDeletedAll();
        return show();
    }

    @POST
    @Produces(MediaType.TEXT_HTML)
    @Path("/create")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String create(MultivaluedMap<String, String> line) {
        lineRepository.persistLine(lineMapper.map(line));
        return show();
    }

    @POST
    @Produces(MediaType.TEXT_HTML)
    @Path("/update/{id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String update(@PathParam("id") Long id, MultivaluedMap<String, String> line) {
        lineRepository.updateLine(id, lineMapper.map(line));
        return show();
    }
}
