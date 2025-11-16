package com.denunciayabackend.community.domain.model.commands;

public record CreateCommentCommand(Long postId,  Long userId, String author, String content) {

    public CreateCommentCommand {
        if (postId == null || postId <= 0) {
            throw new IllegalArgumentException("postId must not be null or less than 1");
        }
        if (userId == null ) {
            throw new IllegalArgumentException("userId must not be null or blank");
        }
        if (author == null || author.isBlank()) {
            throw new IllegalArgumentException("author must not be null or blank");
        }
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("content must not be null or blank");
        }
    }
}