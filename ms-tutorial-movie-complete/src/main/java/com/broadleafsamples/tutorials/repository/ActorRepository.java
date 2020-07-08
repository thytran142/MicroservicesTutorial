package com.broadleafsamples.tutorials.repository;

import com.broadleafsamples.tutorials.domain.Actor;
import com.broadleafsamples.tutorials.domain.Movie;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "actors", path = "actors")
public interface ActorRepository extends PagingAndSortingRepository<Actor, Long> {
    List<Actor> findByMovies(@Param("title") String title);
}
