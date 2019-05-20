package com.example.agilpub.models.repositories;

import com.example.agilpub.models.Document;
import com.example.agilpub.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends CrudRepository<Document, Long> {

    public Iterable<Document> findByOwner(User user);
}
