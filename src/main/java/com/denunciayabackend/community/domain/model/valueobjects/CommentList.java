package com.denunciayabackend.community.domain.model.valueobjects;

import com.denunciayabackend.community.domain.model.entities.Comment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommentList {

    private final List<Comment> comments;

    public CommentList() {
        this.comments = new ArrayList<>();
    }

    public CommentList(List<Comment> comments) {
        this.comments = new ArrayList<>(comments);
    }

    public void add(Comment comment) {
        if (comment == null)
            throw new IllegalArgumentException("Comment cannot be null");
        this.comments.add(comment);
    }

    public List<Comment> getAll() {
        return Collections.unmodifiableList(comments);
    }

    public int count() {
        return comments.size();
    }
}
