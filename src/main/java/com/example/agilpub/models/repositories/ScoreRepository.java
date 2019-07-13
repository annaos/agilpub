package com.example.agilpub.models.repositories;

import com.example.agilpub.models.Document;
import com.example.agilpub.models.Score;
import com.example.agilpub.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreRepository extends CrudRepository<Score, Long> {

    public Iterable<Score> findByDocumentAndOwner(Document document, User owner);
}
