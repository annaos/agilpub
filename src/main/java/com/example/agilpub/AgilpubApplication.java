package com.example.agilpub;

import com.example.agilpub.models.*;
import com.example.agilpub.models.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class AgilpubApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgilpubApplication.class, args);
    }

    @Bean
    CommandLineRunner init(
            UserRepository userRepository,
            DocumentRepository documentRepository,
            DocumentVersionRepository documentVersionRepository,
            TagRepository tagRepository,
            CommentRepository commentRepository
    ) {
        return args -> {
            Stream.of("John", "Julie", "Jennifer", "Helen", "Rachel").forEach(name -> {
                User user = new User(name, "test", name.toLowerCase() + "@domain.com");
                userRepository.save(user);
            });
            User user = userRepository.findById((long)1).orElseThrow(() -> new EntityNotFoundException("1"));
            Stream.of("Homomorphic Signature", "Algebra", "Combinatorics").forEach(name -> {
                Document document = new Document(name, user);
                documentRepository.save(document);
            });
            Optional<Document> documentOptional = documentRepository.findById((long)6);
            if (documentOptional.isPresent()) {
                Document document = documentOptional.get();
                Stream.of("Version1", "Version2", "Version3").forEach(name -> {
                    DocumentVersion documentVersion = new DocumentVersion(document, name);
                    documentVersionRepository.save(documentVersion);
                });

                Optional<DocumentVersion> docVersionOptional = documentVersionRepository.findById((long)9);
                if (docVersionOptional.isPresent()) {
                    DocumentVersion docVersion = docVersionOptional.get();
                    Stream.of("MyComment 1", "This is amazing work!").forEach(name -> {
                        Comment comment = new Comment(user, docVersion);
                        comment.setText(name);
                        commentRepository.save(comment);
                    });
                }

                Stream.of("Tag1", "Tag2").forEach(name -> {
                    Tag tag = new Tag(name);
                    tagRepository.save(tag);
                    tag.addDocument(document);
                    tagRepository.save(tag);
                });
            }
            userRepository.findAll().forEach(System.out::println);
            documentRepository.findAll().forEach(System.out::println);
            documentVersionRepository.findAll().forEach(System.out::println);
            tagRepository.findAll().forEach(System.out::println);
            commentRepository.findAll().forEach(System.out::println);
        };
    }


}


