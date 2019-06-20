package com.example.agilpub.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

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
    private String filename;

    //TODO only for com.example.agilpub.controllers.DocumentVersionController.addDocument - maybe delete?
    @Transient
    private MultipartFile file;

    @OneToMany(mappedBy = "docVersion", fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"docVersion", "owner"})
    private List<Comment> comments;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="DOCUMENT_ID")
    @JsonIgnoreProperties({"owner", "tags", "versions"})
    private final Document document;

    public DocumentVersion(Document document, String filename) {
        this.document = document;
        this.filename = filename;
        this.version = document.getVersions().size() + 1;
        this.createdDate = new Date();
    }

    public DocumentVersion() {
        this.document = null;
        this.filename = null;
        this.version = 1;
        this.createdDate = new Date();
    }

    @Override
    public String toString() {
        return "DocumentVersion{" +
                "filename='" + filename + '\'' +
                '}';
    }
}
