package com.example.agilpub.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Entity
@Table
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private final String username;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"owner", "tags", "versions", "scores"})
    private List<Document> files;

    @OneToMany(mappedBy = "owner")
    @JsonIgnoreProperties("owner")
    private List<Comment> comments;

    @OneToMany(mappedBy = "owner")
    @JsonIgnore
    private List<Score> scores;

    public User(String username) {
        if (username != null && !"".equals(username)) {
            this.username = username;
        } else {
            throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
        }
    }

    public User() {
        this.username = null;
    }

    public String getUsername() {
        return this.username;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                '}';
    }
}
