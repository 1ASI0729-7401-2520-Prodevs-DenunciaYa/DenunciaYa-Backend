package com.denunciayabackend.community.interfaces.rest;

import com.denunciayabackend.community.domain.model.commands.CreatePostCommand;
import com.denunciayabackend.community.domain.model.commands.DeletePostCommand;
import com.denunciayabackend.community.domain.model.queries.GetAllPostQuery;
import com.denunciayabackend.community.domain.model.queries.GetPostByIdQuery;
import com.denunciayabackend.community.domain.services.PostCommandService;
import com.denunciayabackend.community.domain.services.PostQueryService;
import com.denunciayabackend.community.interfaces.rest.resources.CreatePostResource;
import com.denunciayabackend.community.interfaces.rest.resources.PostResource;
import com.denunciayabackend.community.interfaces.rest.transform.CreatePostCommandFromResourceAssembler;
import com.denunciayabackend.community.interfaces.rest.transform.PostResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/posts", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Posts", description = "Post-related endpoints for the community module")
public class PostsController {

    private final PostCommandService postCommandService;
    private final PostQueryService postQueryService;

    public PostsController(PostCommandService postCommandService, PostQueryService postQueryService) {
        this.postCommandService = postCommandService;
        this.postQueryService = postQueryService;
    }

    @Operation(summary = "Get all posts", description = "Retrieve all posts from the community")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Posts retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No posts found")
    })
    @GetMapping
    public ResponseEntity<List<PostResource>> getAllPosts() {
        var posts = postQueryService.handle(new GetAllPostQuery());
        if (posts.isEmpty()) return ResponseEntity.notFound().build();

        var resources = posts.stream()
                .map(PostResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }

    @Operation(summary = "Get post by ID", description = "Retrieve a single post by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post found"),
            @ApiResponse(responseCode = "404", description = "Post not found")
    })
    @GetMapping("/{postId}")
    public ResponseEntity<PostResource> getPostById(@PathVariable Long postId) {
        var post = postQueryService.handle(new GetPostByIdQuery(postId));
        return post.map(value ->
                ResponseEntity.ok(PostResourceFromEntityAssembler.toResourceFromEntity(value))
        ).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new post", description = "Create a new post in the community")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Post created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<PostResource> createPost(@RequestBody CreatePostResource resource) {
        var command = CreatePostCommandFromResourceAssembler.toCommandFromResource(resource);
        var postId = postCommandService.handle(command);
        var post = postQueryService.handle(new GetPostByIdQuery(postId));

        if (post.isEmpty()) return ResponseEntity.badRequest().build();

        var postResource = PostResourceFromEntityAssembler.toResourceFromEntity(post.get());
        return new ResponseEntity<>(postResource, HttpStatus.CREATED);
    }

    @Operation(summary = "Delete post", description = "Delete an existing post by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Post deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Post not found")
    })
    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId) {
        postCommandService.handle(new DeletePostCommand(postId));
        return ResponseEntity.noContent().build();
    }
}
