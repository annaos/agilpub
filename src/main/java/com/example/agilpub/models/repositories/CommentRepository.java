package com.example.agilpub.models.repositories;

import com.example.agilpub.models.Comment;
import com.example.agilpub.models.DocumentVersion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {

    public Iterable<Comment> findByVersion(DocumentVersion version);

}
