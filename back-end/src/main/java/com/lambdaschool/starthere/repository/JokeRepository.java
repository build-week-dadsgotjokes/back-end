package com.lambdaschool.starthere.repository;

import com.lambdaschool.starthere.models.Joke;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JokeRepository extends CrudRepository<Joke, Long> {
    @Query(value = "SELECT * FROM jokes WHERE isprivate = false", nativeQuery = true)
    List<Joke> getPublicJokes();

    @Query(value = "SELECT * FROM jokes WHERE userid = :id", nativeQuery = true)
    List<Joke> getJokesByUser(long id);
}
