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
    private String text;
    private int position; //TODO

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="OWNER_ID")
    @JsonIgnoreProperties({"files", "comments"})
    private final User owner;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="DOCVERSION_ID")
    @JsonIgnoreProperties({"document", "comments"})
    private final DocumentVersion docVersion;

    private final Date createdDate;

    public Comment(User owner, DocumentVersion documentVersion) {
        this.owner = owner;
        this.docVersion = documentVersion;
        this.createdDate = new Date();
    }

    public Comment() {
        this.owner = null;
        this.docVersion = null;
        this.createdDate = new Date();
    }

    @Override
    public String toString() {
        return "Comment{" +
                "text='" + text + '\'' +
                '}';
    }

}
