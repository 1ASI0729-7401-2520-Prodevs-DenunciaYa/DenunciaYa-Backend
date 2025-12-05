package community.domain.services;

import com.denunciayabackend.community.domain.model.entities.Comment;
import com.denunciayabackend.community.domain.model.queries.GetCommentsByPostIdQuery;

import java.util.List;
import java.util.Optional;

public interface CommentQueryService {
    List<Comment> handle(GetCommentsByPostIdQuery query);
    Optional<Comment> findById(Long id);
}
