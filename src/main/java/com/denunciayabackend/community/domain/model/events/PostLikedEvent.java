package com.denunciayabackend.community.domain.model.events;

import org.springframework.context.ApplicationEvent;
import lombok.Getter;

@Getter
public class PostLikedEvent extends ApplicationEvent {
    private final Long postId;
    private final String userId;

    public PostLikedEvent(Object source, Long postId, String userId) {
        super(source);
        this.postId = postId;
        this.userId = userId;
    }
}
