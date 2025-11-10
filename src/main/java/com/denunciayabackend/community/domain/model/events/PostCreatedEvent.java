package com.denunciayabackend.community.domain.model.events;

import org.springframework.context.ApplicationEvent;
import lombok.Getter;

@Getter
public class PostCreatedEvent extends ApplicationEvent {
    private final Long postId;
    private final String userId;
    private final String author;
    private final String content;
    private final String imageUrl;

    public PostCreatedEvent(Object source, Long postId, String userId, String author, String content, String imageUrl) {
        super(source);
        this.postId = postId;
        this.userId = userId;
        this.author = author;
        this.content = content;
        this.imageUrl = imageUrl;
    }
}
