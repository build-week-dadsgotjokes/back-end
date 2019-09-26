package com.lambdaschool.starthere.controllers;

import com.lambdaschool.starthere.models.Joke;
import com.lambdaschool.starthere.models.User;
import com.lambdaschool.starthere.models.UserJokeLikes;
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

    @GetMapping(value = "/search/{setup}", produces = {"application/json"})
    public ResponseEntity<?> searchJokes(@PathVariable String setup) {
        return new ResponseEntity<>(jokeService.searchJokes(setup), HttpStatus.OK);
    }

    @GetMapping(value = "/auth/likes/mine", produces = {"application/json"})
    public ResponseEntity<?> getMyLikes(Principal principal) {
        if(principal == null || principal.getName() == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User myUser = userService.findByName(principal.getName());

        return new ResponseEntity<>(jokeService.getLikedJokes(myUser.getUserid()), HttpStatus.OK);
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

    @PostMapping(value = "/auth/like/{jokeid}", produces = {"application/json"})
    public ResponseEntity<?> likeJoke(Principal principal, @PathVariable long jokeid) {
        if(principal == null || principal.getName() == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User currentUser = userService.findByName(principal.getName());
        Joke foundJoke = jokeService.findById(jokeid);

        for(UserJokeLikes u: foundJoke.getLikedUsers()) {
            if(u.getUser().getUserid() == currentUser.getUserid()) {
                return new ResponseEntity<>("This user has already liked this joke! If you want to unlike use the unlike route", HttpStatus.BAD_REQUEST);
            }
        }

        jokeService.insertLikedJoke(foundJoke.getId(), currentUser.getUserid());

        return new ResponseEntity<>("Your user has now liked the joke", HttpStatus.OK);
    }

    @DeleteMapping(value = "/auth/unlike/{jokeid}", produces = {"application/json"})
    public ResponseEntity<?> unlikeJoke(Principal principal, @PathVariable long jokeid) {
        if(principal == null || principal.getName() == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User currentUser = userService.findByName(principal.getName());
        Joke foundJoke = jokeService.findById(jokeid);

        boolean found = false;
        for(UserJokeLikes u: foundJoke.getLikedUsers()) {
            if(u.getUser().getUserid() == currentUser.getUserid()) {
                found = true;
                break;
            }
        }

        if(!found) {
            return new ResponseEntity<>("This user has not yet liked this post", HttpStatus.BAD_REQUEST);
        }

        foundJoke.getLikedUsers().remove(currentUser);

        jokeService.update(foundJoke, foundJoke.getId(), true);

        return new ResponseEntity<>("Your user has now unliked the joke", HttpStatus.OK);
    }

    @DeleteMapping(value = "/auth/delete/{id}", produces = {"application/json"})
    public ResponseEntity<?> deleteJoke(HttpServletRequest request,Principal principal, @PathVariable long id) {
        if(principal == null || principal.getName() == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        jokeService.delete(id, request.isUserInRole("ADMIN"));

        return new ResponseEntity<>("Joke has been deleted", HttpStatus.OK);
    }
}
