package com.example.agilpub.controllers;

import com.example.agilpub.models.Document;
import com.example.agilpub.models.DocumentVersion;
import com.example.agilpub.models.User;
import com.example.agilpub.models.repositories.DocumentRepository;
import com.example.agilpub.models.repositories.DocumentVersionRepository;
import com.example.agilpub.models.repositories.UserRepository;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
//@CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin(origins = "*", maxAge = 3600,
        allowedHeaders={"*"})
public class DocumentVersionController {

    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;
    private final DocumentVersionRepository documentVersionRepository;

    public DocumentVersionController(
            DocumentRepository documentRepository,
            UserRepository userRepository,
            DocumentVersionRepository documentVersionRepository
    ) {
        this.documentRepository = documentRepository;
        this.userRepository = userRepository;
        this.documentVersionRepository = documentVersionRepository;
    }

    @GetMapping("/documentversions/{documentId}")
    public List<DocumentVersion> getDocumentVersions(@PathVariable String documentId) {
        Document document = documentRepository.findById(Long.parseLong(documentId)).orElseThrow(() -> new EntityNotFoundException(documentId));
        return (List<DocumentVersion>) documentVersionRepository.findByDocument(document);
    }

    @PostMapping("/documentversions")
    void addDocument(@RequestBody DocumentVersion documentVersion) {
        documentVersionRepository.save(documentVersion);
    }

}
