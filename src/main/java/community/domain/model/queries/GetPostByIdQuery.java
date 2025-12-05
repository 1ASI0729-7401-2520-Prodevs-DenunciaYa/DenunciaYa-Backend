package community.domain.model.queries;

public record GetPostByIdQuery (Long postId){
    public GetPostByIdQuery{
        if(postId == null || postId<=0){
            throw new IllegalArgumentException("Post Id must not be null and must be greater than zero.");
        }
    }
}
