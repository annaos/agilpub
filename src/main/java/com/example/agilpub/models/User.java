package com.example.agilpub.models;

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
    private final String email;
    private boolean admin;
    private String password;
    private final String username;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"owner", "tags", "versions"})
    private List<Document> files;

    @OneToMany(mappedBy = "owner")
    @JsonIgnoreProperties("owner")
    private List<Comment> comments;

    public User(String username, String password, String email) {
        if (username != null && !"".equals(username) && password != null) {
            this.username = username;
            this.password = password;
            this.email = email;
        } else {
            throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
        }
    }

    public User() {
        this.username = null;
        this.email = null;
    }


    public String getPassword() {
        return this.password;
    }

    public String getUsername() {
        return this.username;
    }

    public boolean isEnabled() {
        return true;
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }


    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                '}';
    }
}
