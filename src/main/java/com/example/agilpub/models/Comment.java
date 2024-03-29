package com.example.agilpub.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
@Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(length = 9999)
    private String toText;
    private String text;
    private String selection;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="OWNER_ID")
    @JsonIgnoreProperties({"files", "comments", "scores"})
    private final User owner;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="VERSION_ID")
    @JsonIgnoreProperties({"document", "comments"})
    private final DocumentVersion version;

    private final Date createdDate;

    public Comment(User owner, DocumentVersion documentVersion) {
        this.owner = owner;
        this.version = documentVersion;
        this.createdDate = new Date();
    }

    public Comment() {
        this.owner = null;
        this.version = null;
        this.createdDate = new Date();
    }

    @Override
    public String toString() {
        return "Comment{" +
                "text='" + text + '\'' +
                '}';
    }

}
