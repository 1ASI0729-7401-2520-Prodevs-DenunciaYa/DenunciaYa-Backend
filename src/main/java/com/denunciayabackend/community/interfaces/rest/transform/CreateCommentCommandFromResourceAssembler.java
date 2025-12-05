package com.denunciayabackend.community.interfaces.rest.transform;

import com.denunciayabackend.community.domain.model.commands.CreateCommentCommand;
import com.denunciayabackend.community.interfaces.rest.resources.CreateCommentResource;

public class CreateCommentCommandFromResourceAssembler {

    public static CreateCommentCommand toCommandFromResource(CreateCommentResource resource, Long postId) {
        if (resource == null) {
            throw new IllegalArgumentException("CreateCommentResource is required");
        }

        String rawUserId = resource.userId();
        if (rawUserId == null || rawUserId.isBlank()) {
            throw new IllegalArgumentException("userId is required");
        }

        Long userId;
        try {
            userId = Long.valueOf(rawUserId.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid userId format. Must be a number.", e);
        }

        return new CreateCommentCommand(
                postId,
                userId,
                resource.author(),
                resource.content()
        );
    }
}
