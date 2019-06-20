package com.example.agilpub.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Entity
@Table
@Data
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private final Date createdDate;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="OWNER_ID")
    @JsonIgnoreProperties({"files", "comments"})
    private final User owner;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="DOCUMENT_TAG",
            joinColumns=@JoinColumn(name="DOCUMENT_ID"),
            inverseJoinColumns=@JoinColumn(name="TAG_ID"))
    @JsonIgnoreProperties("documents")
    private Set<Tag> tags = new HashSet<>();

    @OneToMany(mappedBy = "document", fetch=FetchType.EAGER)
    @JsonIgnoreProperties({"document", "comments"})
    private List<DocumentVersion> versions;

    public Document(String name, User owner) {
        this.name = name;
        this.owner = owner;
        this.createdDate = new Date();
    }

    public Document() {
        this.name = null;
        this.owner = null;
        this.createdDate = new Date();
    }

    public void addTag(Tag tag) {
        this.tags.add(tag);
        tag.getDocuments().add(this);
    }

    public void removeTag(Tag tag) {
        this.tags.remove(tag);
        tag.getDocuments().remove(this);
    }

    @Override
    public String toString() {
        return "Document{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

}
