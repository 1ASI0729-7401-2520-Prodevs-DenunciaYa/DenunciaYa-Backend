package community.domain.services;

import com.denunciayabackend.community.domain.model.aggregates.Post;
import com.denunciayabackend.community.domain.model.commands.CreatePostCommand;
import com.denunciayabackend.community.domain.model.commands.DeletePostCommand;
import com.denunciayabackend.community.domain.model.commands.LikePostCommand;

import java.util.Optional;

public interface PostCommandService {
    Long handle(CreatePostCommand command);
    void handle(DeletePostCommand command);
    Optional<Post> handle(LikePostCommand command);
}
