package community.domain.model.commands;

public record DeletePostCommand(Long postId) {
    public DeletePostCommand{
        if(postId==null){
            throw new IllegalArgumentException("courseId cannot be null ");

        }
    }
}
