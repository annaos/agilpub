package com.example.agilpub;

import com.example.agilpub.models.Document;
import com.example.agilpub.models.DocumentVersion;
import com.example.agilpub.models.User;
import com.example.agilpub.models.repositories.DocumentRepository;
import com.example.agilpub.models.repositories.DocumentVersionRepository;
import com.example.agilpub.models.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

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
    CommandLineRunner init(UserRepository userRepository, DocumentRepository documentRepository, DocumentVersionRepository documentVersionRepository) {
        return args -> {
            Stream.of("John", "Julie", "Jennifer", "Helen", "Rachel").forEach(name -> {
                User user = new User(name, "test", name.toLowerCase() + "@domain.com");
                userRepository.save(user);
            });
            userRepository.findAll().forEach(System.out::println);
            User user = userRepository.findById((long)1).orElseThrow(() -> new EntityNotFoundException("1"));
            Stream.of("Homomorphic Signature", "Algebra", "Combinatorics").forEach(name -> {
                Document document = new Document(name, user);
                documentRepository.save(document);
            });
            documentRepository.findAll().forEach(System.out::println);
            Optional<Document> documentOptional = documentRepository.findById((long)6);
            if (documentOptional.isPresent()) {
                Document document = documentOptional.get();
                Stream.of("Version1", "Version2", "Version3").forEach(name -> {
                    DocumentVersion documentVersion = new DocumentVersion(document, name);
                    documentVersionRepository.save(documentVersion);
                });
            }
            documentVersionRepository.findAll().forEach(System.out::println);
        };
    }

    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:4200");
            }
        };
    }

}


