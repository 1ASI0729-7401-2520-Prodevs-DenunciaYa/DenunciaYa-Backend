package community.interfaces.rest.resources;

public record CreateCommentResource(
        Long postId,
        String userId,
        String author,
        String content
) {}
