package com.broadleafsamples.tutorials.repository;

import com.broadleafsamples.tutorials.domain.Actor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActorRepository extends PagingAndSortingRepository<Actor, Long> {
    List<Actor> findByMovies(@Param("title") String title);
}
