package com.example.agilpub.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private float score;
    private final Date createdDate;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="OWNER_ID")
    @JsonIgnoreProperties({"files", "comments", "scores"})
    private final User owner;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name="DOCUMENT_TAG",
            joinColumns=@JoinColumn(name="DOCUMENT_ID"),
            inverseJoinColumns=@JoinColumn(name="TAG_ID"))
    @JsonIgnoreProperties("documents")
    private Set<Tag> tags = new HashSet<>();

    @OneToMany(mappedBy = "document", fetch=FetchType.LAZY)
    @JsonIgnoreProperties({"document", "comments"})
    private List<DocumentVersion> versions;

    @OneToMany(mappedBy = "document")
    @JsonIgnore
    private List<Score> scores;

    public Document(String name, User owner) {
        this.name = name;
        this.owner = owner;
        this.createdDate = new Date();
    }

    public float getScore() {
        if (this.scores != null && this.scores.size() > 0) {
            float score = 0;
            for (Score scoreEntity: this.scores) {
                score += scoreEntity.getScore();
            }
            return score / this.scores.size();
        }
        return 0;
    }

    public Document() {
        this.name = null;
        this.owner = null;
        this.createdDate = new Date();
    }

    public void addTag(Tag tag) {
        if (this.tags == null) {
            this.tags = new HashSet<Tag>();
        }
        this.tags.add(tag);
        tag.getDocuments().add(this);
    }

    public void removeTag(Tag tag) {
        if (this.tags == null) {
            this.tags = new HashSet<Tag>();
        }
        this.tags.remove(tag);
        tag.getDocuments().remove(this);
    }

    public Set<Tag> getTags() {
        if (this.tags == null) {
            this.tags = new HashSet<Tag>();
        }
        return this.tags;
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
