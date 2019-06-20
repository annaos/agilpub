package com.example.agilpub.models.repositories;

import com.example.agilpub.models.Document;
import com.example.agilpub.models.DocumentVersion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentVersionRepository extends CrudRepository<DocumentVersion, Long> {

    public Iterable<DocumentVersion> findByDocument(Document document);

}
