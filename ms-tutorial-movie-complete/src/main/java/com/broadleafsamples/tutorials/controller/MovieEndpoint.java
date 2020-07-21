package com.broadleafsamples.tutorials.controller;

import com.broadleafsamples.tutorials.domain.Actor;
import com.broadleafsamples.tutorials.domain.Movie;
import com.broadleafsamples.tutorials.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/movies")
public class MovieEndpoint {

    @Autowired
    private MovieService service;

    @GetMapping(value = "/")
    public @ResponseBody Iterable<Movie> movies() {
        return service.findAllMovies();
    }

    @GetMapping(value = "/{movieId}")
    public @ResponseBody Movie getMovie(@PathVariable Long movieId) {
        return service.findById(movieId).get();
    }

    @GetMapping(value = "/{actorId}/actors")
    public @ResponseBody Iterable<Actor> getActorsForMovies(@PathVariable Long actorId) {
        return service.findById(actorId).get().getActors();
    }
}
