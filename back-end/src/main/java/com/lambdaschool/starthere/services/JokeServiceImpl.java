package com.lambdaschool.starthere.services;

import com.lambdaschool.starthere.exceptions.ResourceNotFoundException;
import com.lambdaschool.starthere.models.Joke;
import com.lambdaschool.starthere.models.User;
import com.lambdaschool.starthere.repository.JokeRepository;
import com.lambdaschool.starthere.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service(value = "jokeService")
public class JokeServiceImpl implements JokeService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JokeRepository jokeRepository;

    @Override
    public List<Joke> findAll() {
        List<Joke> all = new ArrayList<>();

        jokeRepository.findAll().iterator().forEachRemaining(all::add);

        return all;
    }

    @Override
    public Joke findById(long id) {
        return jokeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Could not find the joke with ID: " + id));
    }

    @Override
    public List<Joke> findPublicJokes() {
        return jokeRepository.getPublicJokes();
    }

    @Override
    public List<Joke> findJokesByOwner(long id) {
        List<Joke> jokes = jokeRepository.getJokesByUser(id);

        jokes.forEach(joke -> joke.setOwner(null));

        return jokes;
    }

    @Override
    public List<Joke> findJokesBySetup(String setupString) {
        return null;
    }

    @Override
    public List<Joke> findJokesByPunchline(String punchlineString) {
        return null;
    }

    @Override
    public void delete(long id, boolean isAdmin) {

    }

    @Override
    public Joke save(Joke joke, boolean isAdmin) {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();

        if (joke.getOwner()
                .getUsername()
                .equalsIgnoreCase(authentication.getName()) || isAdmin)
        {
            return jokeRepository.save(joke);
        } else
        {
            throw new ResourceNotFoundException((authentication.getName() + "not authorized to make change"));
        }
    }

    @Override
    public Joke update(Joke joke, long id, boolean isAdmin) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = userRepository.findByUsername(authentication.getName());
        Joke currentJoke = jokeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Could not find joke with ID: " + id));

        if(!isAdmin) {
            boolean found = false;

            for(Joke j: currentUser.getJokes()) {
                if(j.getId() == id) {
                    found = true;
                    break;
                }
            }

            if(!found) {
                throw new ResourceNotFoundException(id + " You do not own this joke or you are not an admin");
            }
        }


        if(joke.getOwner() != null) {
            currentJoke.setOwner(joke.getOwner());
        }
        if(joke.getPunchline() != null) {
            currentJoke.setPunchline(joke.getPunchline());
        }
        if(joke.getSetup() != null) {
            currentJoke.setSetup(joke.getSetup());
        }
        if(joke.isIsprivate() != currentJoke.isIsprivate()) {
            currentJoke.setIsprivate(joke.isIsprivate());
        }

        return jokeRepository.save(currentJoke);
    }
}
