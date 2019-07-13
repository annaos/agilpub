package com.example.agilpub.controllers;

import com.example.agilpub.models.Document;
import com.example.agilpub.models.Score;
import com.example.agilpub.models.User;
import com.example.agilpub.models.repositories.DocumentRepository;
import com.example.agilpub.models.repositories.ScoreRepository;
import com.example.agilpub.models.repositories.UserRepository;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600,
        allowedHeaders={"*"})
public class ScoreController {

    private final ScoreRepository scoreRepository;
    private final UserRepository userRepository;
    private final DocumentRepository documentRepository;

    public ScoreController(
            ScoreRepository scoreRepository,
            UserRepository userRepository,
            DocumentRepository documentRepository) {
        this.userRepository = userRepository;
        this.scoreRepository = scoreRepository;
        this.documentRepository = documentRepository;
    }

    @PostMapping("/score")
    public Score addScore(@RequestBody Score score) {
        List<Score> scores = (List<Score>) scoreRepository.findByDocumentAndOwner(score.getDocument(), score.getOwner());
        if (scores.size() > 0) {
            int scoreValue = score.getScore();
            score = scores.get(0);
            score.setScore(scoreValue);
        }
        scoreRepository.save(score);
        return score;
    }

    @GetMapping("/score/{ownerId}/{documentId}")
    public Score myScore(@PathVariable String ownerId, @PathVariable String documentId) {
        User user = userRepository.findById(Long.parseLong(ownerId)).orElseThrow(() -> new EntityNotFoundException(ownerId));
        Document document = documentRepository.findById(Long.parseLong(documentId)).orElseThrow(() -> new EntityNotFoundException(documentId));
        List<Score> scores = (List<Score>) scoreRepository.findByDocumentAndOwner(document, user);

        if (scores.size() > 0) {
            return scores.get(0);
        }
        return null;
    }

}
