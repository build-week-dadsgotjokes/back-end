package com.lambdaschool.starthere.services;

import com.lambdaschool.starthere.models.Joke;

import java.util.List;

public interface JokeService {
    public List<Joke> findAll();
    public Joke findById(long id);

    public List<Joke> findPublicJokes();
    public List<Joke> findJokesByOwner(long id);

    public List<Joke> findJokesBySetup(String setupString);
    public List<Joke> findJokesByPunchline(String punchlineString);

    void delete(long id, boolean isAdmin);

    Joke save(Joke joke, boolean isAdmin);

    Joke update(Joke joke, long id, boolean isAdmin);
}
