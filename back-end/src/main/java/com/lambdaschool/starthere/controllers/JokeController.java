package com.lambdaschool.starthere.controllers;

import com.lambdaschool.starthere.models.Joke;
import com.lambdaschool.starthere.models.User;
import com.lambdaschool.starthere.services.JokeService;
import com.lambdaschool.starthere.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/jokes")
public class JokeController {
    @Autowired
    private UserService userService;

    @Autowired
    private JokeService jokeService;

    @GetMapping(value = "/public", produces = {"application/json"})
    public ResponseEntity<?> getPublicJokes() {
        return new ResponseEntity<>(jokeService.findPublicJokes(), HttpStatus.OK);
    }

    @GetMapping(value = "/auth/mine", produces = {"application/json"})
    public ResponseEntity<?> getMyJokes(Principal principal) {
        if(principal == null || principal.getName() == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User myUser = userService.findByName(principal.getName());

        return new ResponseEntity<>(jokeService.findJokesByOwner(myUser.getUserid()), HttpStatus.OK);
    }

    @PostMapping(value = "/auth/create", produces = {"application/json"})
    public ResponseEntity<?> createJoke(HttpServletRequest request, Principal principal, @Valid @RequestBody Joke joke) {
        if(principal == null || principal.getName() == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User myUser = userService.findByName(principal.getName());

        joke.setOwner(myUser);

        joke = jokeService.save(joke, true);

        return new ResponseEntity<>(joke, HttpStatus.CREATED);
    }

    @PutMapping(value = "/auth/update/{id}", produces = {"application/json"})
    public ResponseEntity<?> updateJoke(HttpServletRequest request, Principal principal, @Valid @RequestBody Joke updateJoke, @PathVariable long id) {
        if(principal == null || principal.getName() == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        updateJoke = jokeService.update(updateJoke, id, request.isUserInRole("ADMIN"));

        return new ResponseEntity<>(updateJoke, HttpStatus.ACCEPTED);
    }
}
