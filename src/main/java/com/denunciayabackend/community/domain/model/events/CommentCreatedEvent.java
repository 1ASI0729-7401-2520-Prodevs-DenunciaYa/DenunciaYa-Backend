package com.denunciayabackend.community.domain.model.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CommentCreatedEvent extends ApplicationEvent {
    private final Long commentId;
    private final Long postId;
    private final String userId;
    private final String author;
    private final String content;

    public CommentCreatedEvent(Object source, Long commentId, Long postId, String userId, String author, String content) {
        super(source);
        this.commentId = commentId;
        this.postId = postId;
        this.userId = userId;
        this.author = author;
        this.content = content;
    }
}
