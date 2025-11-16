package com.denunciayabackend.community.application.internal.commandservice;

import com.denunciayabackend.community.domain.model.aggregates.Post;
import com.denunciayabackend.community.domain.model.commands.CreatePostCommand;
import com.denunciayabackend.community.domain.model.commands.DeletePostCommand;
import com.denunciayabackend.community.domain.model.commands.LikePostCommand;
import com.denunciayabackend.community.domain.services.PostCommandService;
import com.denunciayabackend.community.infrastructure.persistence.jpa.repositories.PostRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostCommandServiceImpl implements PostCommandService {

    private final PostRepository postRepository;

    public PostCommandServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Long handle(CreatePostCommand command) {
        var post = new Post(command);
        try {
            postRepository.save(post);
        } catch (Exception e) {
            throw new RuntimeException("Error saving post: " + e.getMessage(), e);
        }
        return post.getId();
    }

    @Override
    public void handle(DeletePostCommand command) {
        if (!postRepository.existsById(command.postId()))
            throw new IllegalArgumentException("Post not found with id: " + command.postId());

        try {
            postRepository.deleteById(command.postId());
        } catch (Exception e) {
            throw new RuntimeException("Error deleting post: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Post> handle(LikePostCommand command) {
        var post = postRepository.findById(command.postId())
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + command.postId()));
        try {
            post.incrementLikes();
            postRepository.save(post);
            return Optional.of(post);
        } catch (Exception e) {
            throw new RuntimeException("Error updating post likes: " + e.getMessage(), e);
        }
    }
}
