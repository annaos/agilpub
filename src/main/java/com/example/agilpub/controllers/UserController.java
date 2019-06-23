package com.example.agilpub.controllers;

import com.example.agilpub.models.User;
import com.example.agilpub.models.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
//@CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin(origins = "*", maxAge = 3600,
        allowedHeaders={"*"})
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return (List<User>) userRepository.findAll();
    }

    @GetMapping("/users/{username}")
    public User getUserByUsername(@PathVariable String username) {
        try {
            return (User) userRepository.findByUsername(username).iterator().next();
        }catch (NoSuchElementException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with username " + username + " does not exist.");
        }
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    void addUser(@RequestBody User user) {
        try {
            userRepository.findByUsername(user.getUsername()).iterator().next();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with username " + user.getUsername() + " already exist.");
        }catch (NoSuchElementException ex) {
            userRepository.save(user);
        }
    }
}
