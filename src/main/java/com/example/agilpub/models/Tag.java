package com.example.agilpub.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Entity
@Table
@Data
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @ManyToMany(fetch=FetchType.EAGER, mappedBy="tags")
    @JsonIgnoreProperties({"owner", "tags", "versions"})
    private Set<Document> documents = new HashSet<>();

    public Tag(String name) {
        this.name = name;
    }

    public Tag() {
        this.name = null;
    }

    public void addDocument(Document document) {
        this.documents.add(document);
        document.getTags().add(this);
    }

    public void removeDocument(Document document) {
        this.documents.remove(document);
        document.getTags().remove(this);
    }

    @Override
    public String toString() {
        return "Tag{" +
                "name='" + name + '\'' +
                "documents='" + this.documents.size() + '\'' +
                '}';
    }

}
