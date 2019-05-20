package com.example.agilpub.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private final Date createdDate;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="OWNER_ID")
    private final User owner;

    @ManyToMany
    @JoinTable(
            name="DOCUMENT_TAG",
            joinColumns=@JoinColumn(name="DOCUMENT_ID", referencedColumnName="ID"),
            inverseJoinColumns=@JoinColumn(name="TAG_ID", referencedColumnName="ID"))
    private List<Tag> tags;

    @OneToMany(mappedBy = "version")
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

    @Override
    public String toString() {
        return "Document{" +
                "name='" + name + '\'' +
                '}';
    }
}
