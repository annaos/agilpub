package com.example.agilpub.models.repositories;

import com.example.agilpub.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    public Iterable<User> findByUsername(String username);
}
