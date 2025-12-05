package community.interfaces.rest.transform;

import com.denunciayabackend.community.domain.model.entities.Comment;
import com.denunciayabackend.community.interfaces.rest.resources.CommentResource;

public class CommentResourceFromEntityAssembler {

    public static CommentResource toResourceFromEntity(Comment entity) {
        return new CommentResource(
                entity.getId(),
                entity.getUserId().userId(),
                entity.getAuthor(),
                entity.getContent(),
                entity.getCreatedAt()
        );
    }
}
