package community.domain.model.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PostDeletedEvent extends ApplicationEvent {
    private final Long postId;

    public PostDeletedEvent(Object source, Long postId) {
        super(source);
        this.postId = postId;
    }
}
