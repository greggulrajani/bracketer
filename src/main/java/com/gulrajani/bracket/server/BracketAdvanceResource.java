package com.gulrajani.bracket.server;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.gulrajani.bracket.entity.Match;
import com.gulrajani.bracket.service.BracketService;
import com.gulrajani.bracket.service.MixingStrategy;

@Path("/brackets")
@Produces(MediaType.APPLICATION_JSON)
public class BracketAdvanceResource {

    BracketService service = new BracketService(new MixingStrategy());

    public BracketAdvanceResource() {
        service.createTournament("new");
        service.addUser("greg");
        service.addUser("bob");
        service.addUser("xander");
        service.addUser("annabel");
        service.addUser("elijha");
        service.addUser("gregor");
        service.addUser("sasha");
        service.addUser("betty");
        service.addUser("fred");
        service.addUser("barney");
        service.addUser("dino");
        service.addUser("wilmha");
        service.addUser("george");
        service.addUser("elrow");
        service.addUser("nathan");
        service.addUser("wend");
        service.startTournament("new");
    }

    @PUT
    @Path("/{winner}/{loser}")
    public List<Match> advanceWinner(@PathParam("winner") long winner, @PathParam("loser") long loser) {
        System.out.println(winner + " " + loser);
        service.advance(winner, loser);
        return service.getBrackets();
    }

    @DELETE
    @Path("/{remover}/{keeper}")
    public List<Match> remove(@PathParam("winner") long remover, @PathParam("loser") long keeper) {
        service.remove(remover, keeper);
        return service.getBrackets();

    }

    @GET
    public List<Match> get() {
        return service.getBrackets();
    }

}
