package com.lambdaschool.starthere.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

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

    private boolean isprivate;

    @ManyToOne
    @JoinColumn(name = "userid")
    @JsonIgnoreProperties("jokes")
    private User owner;

    public Joke() {}

    public Joke(User owner, String setup, String punchline, boolean isprivate) {
        this.owner = owner;
        this.setup = setup;
        this.punchline = punchline;
        this.isprivate = isprivate;
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
