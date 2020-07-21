package com.broadleafsamples.tutorials.service;

import com.broadleafsamples.tutorials.domain.Actor;
import com.broadleafsamples.tutorials.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ActorService {
    @Autowired
    private ActorRepository repository;

    public Optional<Actor> findById(Long movieId) {
        return repository.findById(movieId);
    }

    public Iterable<Actor> getAllActors() {
        return repository.findAll();
    }
}
