package com.broadleafsamples.tutorials.controller;

import com.broadleafsamples.tutorials.domain.Actor;
import com.broadleafsamples.tutorials.domain.Movie;
import com.broadleafsamples.tutorials.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/actors")
public class ActorEndpoint {

    @Autowired
    private ActorService service;

    @GetMapping(value = "/")
    public @ResponseBody Iterable<Actor> movies() {
        return service.getAllActors();
    }

    @GetMapping(value = "/{actorId}")
    public @ResponseBody Actor getActor(@PathVariable Long actorId) {
        return service.findById(actorId).get();
    }

    @GetMapping(value = "/{actorId}/movies")
    public @ResponseBody Iterable<Movie> getMoviesForActor(@PathVariable Long actorId) {
        return service.findById(actorId).get().getMovies();
    }
}
