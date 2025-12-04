package com.denunciayabackend.community.interfaces.rest;

import com.denunciayabackend.community.domain.model.commands.DeletePostCommand;
import com.denunciayabackend.community.domain.model.queries.GetAllPostQuery;
import com.denunciayabackend.community.domain.model.queries.GetPostByIdQuery;
import com.denunciayabackend.community.domain.services.PostCommandService;
import com.denunciayabackend.community.domain.services.PostQueryService;
import com.denunciayabackend.community.interfaces.rest.resources.CreatePostResource;
import com.denunciayabackend.community.interfaces.rest.resources.PostResource;
import com.denunciayabackend.community.interfaces.rest.transform.CreatePostCommandFromResourceAssembler;
import com.denunciayabackend.community.interfaces.rest.transform.PostResourceFromEntityAssembler;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/posts", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Posts", description = "Post-related endpoints for the community module")
public class PostsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostsController.class);

    private final PostCommandService postCommandService;
    private final PostQueryService postQueryService;
    private final ObjectMapper objectMapper;

    public PostsController(PostCommandService postCommandService, PostQueryService postQueryService, ObjectMapper objectMapper) {
        this.postCommandService = postCommandService;
        this.postQueryService = postQueryService;
        this.objectMapper = objectMapper;
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
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createPost(@RequestBody CreatePostResource resource) {
        try {
            if (resource.content() == null || resource.content().isBlank()) {
                LOGGER.warn("Invalid createPost request: missing content - resource={}", resource);
                return ResponseEntity.badRequest().body("content must not be null or blank");
            }
             var command = CreatePostCommandFromResourceAssembler.toCommandFromResource(resource);
             var postId = postCommandService.handle(command);
             var post = postQueryService.handle(new GetPostByIdQuery(postId));

             if (post.isEmpty()) return ResponseEntity.badRequest().body("Could not create post");

             var postResource = PostResourceFromEntityAssembler.toResourceFromEntity(post.get());
            return new ResponseEntity<>(postResource, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            LOGGER.warn("Invalid input for createPost: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            LOGGER.error("Error creating post: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating post");
        }
    }

    // New endpoint: accept multipart/form-data with part 'post' (JSON) and optional part 'image' (file)
    @Operation(summary = "Create a new post with image upload", description = "Create a new post and upload an image file")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Post created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createPostMultipart(
            @RequestPart("post") String postJson,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        CreatePostResource resource;
        try {
            resource = objectMapper.readValue(postJson, CreatePostResource.class);
        } catch (Exception e) {
            LOGGER.warn("Invalid post JSON in multipart request: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Invalid post JSON: " + e.getMessage());
        }

        try {

            // 1. Validar contenido mínimo
            if (resource.content() == null || resource.content().isBlank()) {
                return ResponseEntity.badRequest().body("content must not be null or blank");
            }

            String imageUrl = null;

            // 2. Si viene imagen → Guardarla
            if (image != null && !image.isEmpty()) {
                Path uploadDir = Paths.get("uploads");
                Files.createDirectories(uploadDir);

                String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
                Path filePath = uploadDir.resolve(fileName);

                Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                imageUrl = "/uploads/" + fileName;
            }

            // 3. Construir resource definitivo (incluye imagen si existe)
            CreatePostResource finalResource = new CreatePostResource(
                    resource.userId(),
                    resource.author(),
                    resource.content(),
                    imageUrl
            );

            // 4. Enviar al servicio de aplicación (usar ensamblador existente y contrato actual)
            var command = CreatePostCommandFromResourceAssembler.toCommandFromResource(finalResource);
            var postId = postCommandService.handle(command);
            var post = postQueryService.handle(new GetPostByIdQuery(postId));

            if (post.isEmpty()) return ResponseEntity.badRequest().body("Could not create post");

            // 5. Retornar entidad creada
            var response = PostResourceFromEntityAssembler.toResourceFromEntity(post.get());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload image: " + e.getMessage());
        }
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
