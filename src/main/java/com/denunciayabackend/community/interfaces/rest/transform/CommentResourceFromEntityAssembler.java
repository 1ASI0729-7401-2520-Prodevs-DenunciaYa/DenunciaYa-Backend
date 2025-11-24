package com.denunciayabackend.community.interfaces.rest.transform;

import com.denunciayabackend.community.domain.model.entities.Comment;
import com.denunciayabackend.community.interfaces.rest.resources.CommentResource;

import java.util.Date;
import java.time.ZoneId;

public class CommentResourceFromEntityAssembler {

    public static CommentResource toResourceFromEntity(Comment entity) {
        Date createdAt = Date.from(entity.getCreatedAt().atZone(ZoneId.systemDefault()).toInstant());

        return new CommentResource(
                entity.getId(),
                entity.getUserId().userId(),
                entity.getAuthor(),
                entity.getContent(),
                createdAt
        );
    }
}
