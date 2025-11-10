package com.denunciayabackend.community.domain.model.events;

import org.springframework.context.ApplicationEvent;
import lombok.Getter;

@Getter
public class PostDeletedEvent extends ApplicationEvent {
    private final Long postId;

    public PostDeletedEvent(Object source, Long postId) {
        super(source);
        this.postId = postId;
    }
}
