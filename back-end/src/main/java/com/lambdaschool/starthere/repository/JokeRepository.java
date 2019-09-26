package com.lambdaschool.starthere.repository;

import com.lambdaschool.starthere.models.Joke;
import com.lambdaschool.starthere.models.UserJokeLikes;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface JokeRepository extends CrudRepository<Joke, Long> {
    @Query(value = "SELECT * FROM jokes WHERE isprivate = false", nativeQuery = true)
    List<Joke> getPublicJokes();

    @Query(value = "SELECT * FROM jokes WHERE userid = :id", nativeQuery = true)
    List<Joke> getJokesByUser(long id);

    @Query(value = "SELECT * FROM jokes WHERE UPPER(setup) LIKE CONCAT('%', UPPER(:setup), '%') AND isprivate = false ", nativeQuery = true)
    List<Joke> searchJokes(String setup);

    @Query(value = "SELECT * FROM userjokeslikes WHERE userid = :user", nativeQuery = true)
    List<UserJokeLikes> getLikedJokes(long user);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO userjokeslikes(jokeid, userid) VALUES(:jokeid, :userid)", nativeQuery = true)
    void insertLikedJoke(long jokeid, long userid);
}
