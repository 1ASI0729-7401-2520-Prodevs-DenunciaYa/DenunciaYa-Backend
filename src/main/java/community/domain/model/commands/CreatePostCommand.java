package community.domain.model.commands;

public record CreatePostCommand(String userId,String author,String content,String imageUrl,Integer likes) {
public CreatePostCommand {


    if (userId == null || userId.isBlank()) {
        throw new IllegalArgumentException("userId must not be null or blank");
    }
    if (author == null || author.isBlank()) {
        throw new IllegalArgumentException("author must not be null or blank");
    }
    if (content == null || content.isBlank()) {
        throw new IllegalArgumentException("content must not be null or blank");
    }
    // imageUrl es opcional: puede ser null o vacía cuando no se sube imagen



}

}
