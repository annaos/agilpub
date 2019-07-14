package com.example.agilpub.controllers;

import com.example.agilpub.models.Document;
import com.example.agilpub.models.Tag;
import com.example.agilpub.models.User;
import com.example.agilpub.models.repositories.DocumentRepository;
import com.example.agilpub.models.repositories.TagRepository;
import com.example.agilpub.models.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.lang.reflect.Array;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600,
        allowedHeaders={"*"})
public class TagController {

    private final TagRepository tagRepository;
    private final DocumentRepository documentRepository;

    public TagController(TagRepository tagRepository, DocumentRepository documentRepository) {
        this.tagRepository = tagRepository;
        this.documentRepository = documentRepository;
    }

    @PostMapping("/tag")
    @ResponseStatus(HttpStatus.OK)
    void addTag(@RequestBody Tag tag) {
        long documentId = tag.getDocuments().iterator().next().getId();
        Document document = documentRepository.findById((documentId)).orElseThrow(() -> new EntityNotFoundException(String.valueOf(documentId)));
        List<Tag> tags = (List<Tag>) tagRepository.findByName(tag.getName());
        if (tags.size()>0) {
            tag = tags.get(0);
        }

        tag.setDocuments(null);
        tag.addDocument(document);
        tagRepository.save(tag);
        documentRepository.save(document);
    }

    @GetMapping("/tag/{tagId}")
    @ResponseStatus(HttpStatus.OK)
    void deleteTag(@PathVariable String tagId) {
        Tag tag = tagRepository.findById(Long.parseLong(tagId)).orElseThrow(() -> new EntityNotFoundException(String.valueOf(tagId)));
        long documentId = tag.getDocuments().iterator().next().getId();
        Document document = documentRepository.findById(documentId).orElseThrow(() -> new EntityNotFoundException(String.valueOf(documentId)));

        document.removeTag(tag);
        documentRepository.save(document);
        tagRepository.delete(tag);
    }

    @GetMapping("/tags/{tagId}")
    @ResponseStatus(HttpStatus.OK)
    Document[] getDocumentsByTag(@PathVariable String tagId) {
        Tag tag = tagRepository.findById(Long.parseLong(tagId)).orElseThrow(() -> new EntityNotFoundException(String.valueOf(tagId)));
        return (Document[]) tag.getDocuments().toArray();
    }

    @GetMapping("/tags")
    @ResponseStatus(HttpStatus.OK)
    List<Tag> getTags() {
        return (List<Tag>) tagRepository.findAll();
    }

}
