package com.denunciayabackend.community.application.internal.commandservice;

import com.denunciayabackend.community.domain.model.aggregates.Post;
import com.denunciayabackend.community.domain.model.commands.CreateCommentCommand;
import com.denunciayabackend.community.domain.model.commands.DeleteCommentCommand;
import com.denunciayabackend.community.domain.model.entities.Comment;
import com.denunciayabackend.community.domain.model.valueobjects.UserId;
import com.denunciayabackend.community.domain.services.CommentCommandService;
import com.denunciayabackend.community.infrastructure.persistence.jpa.repositories.CommentRepository;
import com.denunciayabackend.community.infrastructure.persistence.jpa.repositories.PostRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentCommandServiceImpl implements CommentCommandService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentCommandServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public Long handle(CreateCommentCommand command) {
        Post post = postRepository.findById(command.postId())
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        Comment comment = new Comment(
                post,
                new UserId(command.userId()),
                command.author(),
                command.content()
        );

        commentRepository.save(comment);
        return comment.getId();
    }

    @Override
    public void handle(DeleteCommentCommand command) {
        if (!commentRepository.existsById(command.commentId())) {
            throw new IllegalArgumentException("Comment not found");
        }
        commentRepository.deleteById(command.commentId());
    }
}
