package com.example.agilpub.controllers;

import com.example.agilpub.models.Document;
import com.example.agilpub.models.User;
import com.example.agilpub.models.repositories.DocumentRepository;
import com.example.agilpub.models.repositories.UserRepository;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
//@CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin(origins = "*", maxAge = 3600,
        allowedHeaders={"x-auth-token", "x-requested-with", "x-xsrf-token"})
public class DocumentController {

    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;

    public DocumentController(DocumentRepository documentRepository, UserRepository userRepository) {
        this.documentRepository = documentRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/documents")
    public List<Document> getDocuments() {
        return (List<Document>) documentRepository.findAll();
    }

    @GetMapping("/documents/{userId}")
    public List<Document> getDocuments(@PathVariable String userId) {
        User user = userRepository.findById(Long.parseLong(userId)).orElseThrow(() -> new EntityNotFoundException(userId));;
        return (List<Document>) documentRepository.findByOwner(user);
    }

    @PostMapping("/documents")
    void addDocument(@RequestBody Document document) {
        documentRepository.save(document);
    }

}
