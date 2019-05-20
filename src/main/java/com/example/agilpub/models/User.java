package com.example.agilpub.models;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private final String email;
    private boolean admin;
    private String password;
    private final String username;

    @OneToMany(mappedBy = "owner")
    private List<Document> files;
    @OneToMany(mappedBy = "owner")
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

    public Collection<GrantedAuthority> getAuthorities() {
        return new ArrayList<GrantedAuthority>();
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
