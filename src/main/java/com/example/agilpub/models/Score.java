package com.example.agilpub.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Entity
@Table
@Data
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="OWNER_ID")
    @JsonIgnoreProperties({"files", "comments", "scores"})
    private final User owner;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="DOCUMENT_ID")
    @JsonIgnoreProperties(value = {"owner", "tags", "versions", "scores"})
    private final Document document;

    private Integer score;

    public Score(Document document, User user, int score) {
        this.score = score;
        this.owner = user;
        this.document = document;
    }

    public Score() {
        this.score = null;
        this.owner = null;
        this.document = null;
    }

}
