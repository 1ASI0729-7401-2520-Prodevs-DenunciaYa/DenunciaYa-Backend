package community.application.internal.queryservices;

import com.denunciayabackend.community.domain.model.aggregates.Post;
import com.denunciayabackend.community.domain.model.queries.GetAllPostQuery;
import com.denunciayabackend.community.domain.model.queries.GetPostByIdQuery;
import com.denunciayabackend.community.domain.services.PostQueryService;
import com.denunciayabackend.community.infrastructure.persistence.jpa.repositories.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostQueryServiceImpl implements PostQueryService {

    private final PostRepository postRepository;

    public PostQueryServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> handle(GetAllPostQuery query) {
        try {
            return postRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving posts: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Post> handle(GetPostByIdQuery query) {
        try {
            return postRepository.findById(query.postId());
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving post with id " + query.postId() + ": " + e.getMessage(), e);
        }
    }
}
