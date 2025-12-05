package community.interfaces.rest.transform;

import com.denunciayabackend.community.domain.model.commands.CreateCommentCommand;
import com.denunciayabackend.community.interfaces.rest.resources.CreateCommentResource;

public class CreateCommentCommandFromResourceAssembler {

    public static CreateCommentCommand toCommandFromResource(CreateCommentResource resource, Long postId) {
        Long userId = null;
        try {
            userId = Long.parseLong(resource.userId());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid userId format. Must be a number.");
        }

        return new CreateCommentCommand(
                postId,
                userId,
                resource.author(),
                resource.content()
        );
    }
}
