package com.example.agilpub.controllers;

import com.example.agilpub.models.Comment;
import com.example.agilpub.models.DocumentVersion;
import com.example.agilpub.models.repositories.CommentRepository;
import com.example.agilpub.models.repositories.DocumentRepository;
import com.example.agilpub.models.repositories.DocumentVersionRepository;
import com.example.agilpub.models.repositories.UserRepository;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600,
        allowedHeaders={"*"})
public class CommentController {
    private final DocumentRepository documentRepository;
    private final DocumentVersionRepository documentVersionRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public CommentController(
            DocumentRepository documentRepository,
            UserRepository userRepository,
            CommentRepository commentRepository,
            DocumentVersionRepository documentVersionRepository
    ) {
        this.documentRepository = documentRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.documentVersionRepository = documentVersionRepository;
    }

    @GetMapping("/comments")
    public List<Comment> getComments() {
        return (List<Comment>) commentRepository.findAll();
    }

    @GetMapping("/comments/{versionId}")
    public List<Comment> getComments(@PathVariable String versionId) {
        DocumentVersion version = documentVersionRepository.findById(Long.parseLong(versionId)).orElseThrow(() -> new EntityNotFoundException(versionId));
        return (List<Comment>) commentRepository.findByVersion(version);
    }

    @PostMapping("/comment")
    public Comment addComment(@RequestBody Comment comment) {
        commentRepository.save(comment);
        return comment;
    }

    @GetMapping("/comment/{commentId}")
    public Comment getCommentById(@PathVariable String commentId) {
        return (Comment) commentRepository.findById(Long.parseLong(commentId)).orElseThrow(() -> new EntityNotFoundException(commentId));
    }

    @GetMapping("/comment/delete/{commentId}")
    public void deleteCommentById(@PathVariable String commentId) {
        Comment comment = (Comment) commentRepository.findById(Long.parseLong(commentId)).orElseThrow(() -> new EntityNotFoundException(commentId));
        commentRepository.delete(comment);
    }
}
