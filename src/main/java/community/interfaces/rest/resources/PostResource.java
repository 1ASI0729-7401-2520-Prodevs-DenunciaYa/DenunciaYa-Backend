package community.interfaces.rest.resources;

import java.util.Date;
import java.util.List;

public record PostResource(
        Long id,
        String userId,
        String author,
        String content,
        String imageUrl,
        Integer likes,
        Date createdAt,
        Date updatedAt,
        List<CommentResource> comments
) {}
