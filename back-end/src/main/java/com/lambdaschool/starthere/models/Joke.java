package com.lambdaschool.starthere.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "jokes")
public class Joke extends Auditable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String setup;

    @Column(nullable = false)
    private String punchline;

    @Column(columnDefinition = "boolean default false")
    private boolean isprivate;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "savedUsers",
            joinColumns = { @JoinColumn(name = "jokeid") },
            inverseJoinColumns = { @JoinColumn(name = "userid") }
    )
    @JsonIgnoreProperties("savedJokes")
    private List<User> savedUsers = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "likedUsers",
            joinColumns = { @JoinColumn(name = "jokeid") },
            inverseJoinColumns = { @JoinColumn(name = "userid") }
    )
    @JsonIgnoreProperties("likedJokes")
    private List<User> likedUsers = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "userid")
    @JsonIgnoreProperties("jokes")
    private User owner;

    public Joke() {}

    public Joke(User owner, String setup, String punchline, boolean isprivate, List<User> savedUsers, List<User> likedUsers) {
        this.owner = owner;
        this.setup = setup;
        this.punchline = punchline;
        this.isprivate = isprivate;

        this.savedUsers = savedUsers;
        this.likedUsers = likedUsers;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSetup() {
        return setup;
    }

    public void setSetup(String setup) {
        this.setup = setup;
    }

    public String getPunchline() {
        return punchline;
    }

    public void setPunchline(String punchline) {
        this.punchline = punchline;
    }

    public boolean isIsprivate() {
        return isprivate;
    }

    public void setIsprivate(boolean isprivate) {
        this.isprivate = isprivate;
    }



    public List<User> getSavedUsers() {
        return savedUsers;
    }

    public void setSavedUsers(List<User> savedUsers) {
        this.savedUsers = savedUsers;
    }

    public List<User> getLikedUsers() {
        return likedUsers;
    }

    public void setLikedUsers(List<User> likedUsers) {
        this.likedUsers = likedUsers;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
