package com.example.agilpub.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String text;
    private int position; //TODO

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="OWNER_ID")
    private final User owner;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="DOCVERSION_ID")
    private final DocumentVersion docVersion;

    private final Date createdDate;

}
