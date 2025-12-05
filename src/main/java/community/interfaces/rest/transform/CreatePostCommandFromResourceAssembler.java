package community.interfaces.rest.transform;

import com.denunciayabackend.community.domain.model.commands.CreatePostCommand;
import com.denunciayabackend.community.interfaces.rest.resources.CreatePostResource;

public class CreatePostCommandFromResourceAssembler {
    public static CreatePostCommand toCommandFromResource(CreatePostResource resource) {
        return new CreatePostCommand(
                resource.userId(),
                resource.author(),
                resource.content(),
                resource.imageUrl(),
                0
        );
    }
}
