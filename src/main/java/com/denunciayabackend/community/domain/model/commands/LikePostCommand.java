package com.denunciayabackend.community.domain.model.commands;

public record LikePostCommand(Long postId, String userId) {

    public LikePostCommand {
        if (postId == null || postId <= 0) {
            throw new IllegalArgumentException("postId must not be null or less than 1");
        }
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("userId must not be null or blank");
        }
    }
}
