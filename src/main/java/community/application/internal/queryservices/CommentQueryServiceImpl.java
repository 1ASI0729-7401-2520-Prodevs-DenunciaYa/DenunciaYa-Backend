package community.application.internal.queryservices;

import com.denunciayabackend.community.domain.model.entities.Comment;
import com.denunciayabackend.community.domain.model.queries.GetCommentsByPostIdQuery;
import com.denunciayabackend.community.domain.services.CommentQueryService;
import com.denunciayabackend.community.infrastructure.persistence.jpa.repositories.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentQueryServiceImpl implements CommentQueryService {

    private final CommentRepository commentRepository;

    public CommentQueryServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Comment> handle(GetCommentsByPostIdQuery query) {
        return commentRepository.findByPostId(query.postId());
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id);
    }
}
