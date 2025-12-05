package community.interfaces.rest.resources;

import java.util.Date;

public record CommentResource(
        Long id,
        Long  userId,
        String author,
        String content,
        Date createdAt
) {}
