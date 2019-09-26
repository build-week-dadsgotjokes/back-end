package com.lambdaschool.starthere.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "userjokeslikes")
public class UserJokeLikes implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "userid")
    @JsonIgnoreProperties("likedJokes")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "jokeid")
    @JsonIgnoreProperties("likedUsers")
    private Joke joke;

    public UserJokeLikes() {}

    public UserJokeLikes(User user, Joke joke) {
        this.user = user;
        this.joke = joke;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Joke getJoke() {
        return joke;
    }

    public void setJoke(Joke joke) {
        this.joke = joke;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof UserJokeLikes))
        {
            return false;
        }
        UserJokeLikes userRoles = (UserJokeLikes) o;
        return getUser().equals(userRoles.getUser()) && getJoke().equals(userRoles.getJoke());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getUser(), getJoke());
    }
}
