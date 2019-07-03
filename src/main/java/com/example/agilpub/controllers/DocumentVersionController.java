package com.example.agilpub.controllers;

import com.example.agilpub.models.Document;
import com.example.agilpub.models.DocumentVersion;
import com.example.agilpub.models.repositories.DocumentRepository;
import com.example.agilpub.models.repositories.DocumentVersionRepository;
import com.example.agilpub.models.repositories.UserRepository;
import com.example.agilpub.storage.FileSystemStorageService;
import com.example.agilpub.storage.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
//@CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin(origins = "*", maxAge = 3600,
        allowedHeaders={"*"})
public class DocumentVersionController {
    private static final Logger logger = LoggerFactory.getLogger(DocumentVersionController.class);

    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;
    private final DocumentVersionRepository documentVersionRepository;
    private final StorageService storageService;

    public DocumentVersionController(
            DocumentRepository documentRepository,
            UserRepository userRepository,
            DocumentVersionRepository documentVersionRepository,
            FileSystemStorageService storageService
    ) {
        this.documentRepository = documentRepository;
        this.userRepository = userRepository;
        this.documentVersionRepository = documentVersionRepository;
        this.storageService = storageService;
    }

    @GetMapping("/documentversions/{documentId}")
    public List<DocumentVersion> getDocumentVersions(@PathVariable String documentId) {
        Document document = documentRepository.findById(Long.parseLong(documentId)).orElseThrow(() -> new EntityNotFoundException(documentId));
        return (List<DocumentVersion>) documentVersionRepository.findByDocument(document);
    }

    @GetMapping("/documentversion/{versionId}")
    public DocumentVersion getDocumentVersion(@PathVariable String versionId) {
        DocumentVersion documentVersion = documentVersionRepository.findById(Long.parseLong(versionId)).orElseThrow(() -> new EntityNotFoundException(versionId));
        return (DocumentVersion) documentVersion;
    }

    @GetMapping("/documentversion/{versionId}/file")
    public ResponseEntity<Resource> getFileByDocumentVersion(@PathVariable String versionId, HttpServletRequest request) {
        DocumentVersion documentVersion = documentVersionRepository.findById(Long.parseLong(versionId)).orElseThrow(() -> new EntityNotFoundException(versionId));

        Resource resource = storageService.loadAsResource(documentVersion.getFilename());

        // Try to determine filename's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine filename type.");
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }


    @PostMapping("/documentversion")
    public void addDocument(@RequestBody DocumentVersion documentVersion) {
        documentRepository.save(documentVersion.getDocument());
        documentVersionRepository.save(documentVersion);
    }

    @PostMapping(value = "/api/files")
    @ResponseStatus(HttpStatus.OK)
    public void handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        storageService.store(file);
    }
}
