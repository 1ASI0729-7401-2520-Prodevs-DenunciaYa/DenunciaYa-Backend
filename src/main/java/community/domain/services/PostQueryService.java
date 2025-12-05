package community.domain.services;

import com.denunciayabackend.community.domain.model.aggregates.Post;
import com.denunciayabackend.community.domain.model.queries.GetAllPostQuery;
import com.denunciayabackend.community.domain.model.queries.GetPostByIdQuery;

import java.util.List;
import java.util.Optional;

public interface PostQueryService {
    List<Post> handle(GetAllPostQuery query);
    Optional<Post> handle(GetPostByIdQuery query);
}
