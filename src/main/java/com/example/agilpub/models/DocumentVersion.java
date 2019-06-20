package com.example.agilpub.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table
@Data
public class DocumentVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private final Date createdDate;
    private final int version;
    private final String file;//TODO how save files?

    @OneToMany(mappedBy = "docVersion", fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"docVersion", "owner"})
    private List<Comment> comments;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="DOCUMENT_ID")
    @JsonIgnoreProperties({"owner", "tags", "versions"})
    private final Document document;

    public DocumentVersion(Document document, String file) {
        this.document = document;
        this.file = file;
        this.version = document.getVersions().size() + 1;
        this.createdDate = new Date();
    }

    public DocumentVersion() {
        this.document = null;
        this.file = null;
        this.version = 1;
        this.createdDate = new Date();
    }

    @Override
    public String toString() {
        return "DocumentVersion{" +
                "file='" + file + '\'' +
                '}';
    }
}
