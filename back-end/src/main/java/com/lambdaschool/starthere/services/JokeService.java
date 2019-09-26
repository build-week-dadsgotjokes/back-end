package com.lambdaschool.starthere.services;

import com.lambdaschool.starthere.models.Joke;
import com.lambdaschool.starthere.models.UserJokeLikes;

import java.util.List;

public interface JokeService {
    public List<Joke> findAll();
    public Joke findById(long id);

    public List<Joke> findPublicJokes();
    public List<Joke> findJokesByOwner(long id);

    public List<Joke> findJokesBySetup(String setupString);
    public List<Joke> findJokesByPunchline(String punchlineString);

    List<Joke> searchJokes(String setup);
    List<UserJokeLikes> getLikedJokes(long user);
    void insertLikedJoke(long jokeid, long userid);

    void delete(long id, boolean isAdmin);

    Joke save(Joke joke, boolean isAdmin);

    Joke update(Joke joke, long id, boolean isAdmin);
}
