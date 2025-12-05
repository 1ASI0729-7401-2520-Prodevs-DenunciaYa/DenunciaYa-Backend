package community.interfaces.rest.transform;

import com.denunciayabackend.community.domain.model.aggregates.Post;
import com.denunciayabackend.community.interfaces.rest.resources.PostResource;

import java.util.stream.Collectors;

public class PostResourceFromEntityAssembler {
    public static PostResource toResourceFromEntity(Post entity) {
        return new PostResource(
                entity.getId(),
                entity.getUserId(),
                entity.getAuthor(),
                entity.getContent(),
                entity.getImageUrl(),
                entity.getLikes(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getComments().stream()
                        .map(CommentResourceFromEntityAssembler::toResourceFromEntity)
                        .collect(Collectors.toList())
        );
    }
}
