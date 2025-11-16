package com.denunciayabackend.community.interfaces.rest.resources;

public record CreatePostResource(
        String userId,
        String author,
        String content,
        String imageUrl
) {}
