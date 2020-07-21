package com.broadleafsamples.tutorials.service;

import com.broadleafsamples.tutorials.domain.Movie;
import com.broadleafsamples.tutorials.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MovieService {
    @Autowired
    private MovieRepository repository;

    public Optional<Movie> findById(Long movieId) {
        return repository.findById(movieId);
    }

    public Iterable<Movie> findAllMovies() {
        return repository.findAll();
    }
}
