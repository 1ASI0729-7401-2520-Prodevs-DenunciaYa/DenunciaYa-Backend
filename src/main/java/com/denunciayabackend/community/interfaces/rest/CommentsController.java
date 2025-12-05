package com.denunciayabackend.community.interfaces.rest;

import com.denunciayabackend.community.domain.model.queries.GetCommentsByPostIdQuery;
import com.denunciayabackend.community.domain.services.CommentCommandService;
import com.denunciayabackend.community.domain.services.CommentQueryService;
import com.denunciayabackend.community.interfaces.rest.resources.CommentResource;
import com.denunciayabackend.community.interfaces.rest.resources.CreateCommentResource;
import com.denunciayabackend.community.interfaces.rest.transform.CommentResourceFromEntityAssembler;
import com.denunciayabackend.community.interfaces.rest.transform.CreateCommentCommandFromResourceAssembler;
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
@RequestMapping(value = "/api/v1/posts/{postId}/comments", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Comments", description = "Endpoints for managing comments under posts")
public class CommentsController {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CommentsController.class);

    private final CommentCommandService commentCommandService;
    private final CommentQueryService commentQueryService;

    public CommentsController(CommentCommandService commentCommandService,
                              CommentQueryService commentQueryService) {
        this.commentCommandService = commentCommandService;
        this.commentQueryService = commentQueryService;
    }

    @GetMapping
    public ResponseEntity<List<CommentResource>> getCommentsByPost(@PathVariable Long postId) {
        var query = new GetCommentsByPostIdQuery(postId);
        var comments = commentQueryService.handle(query);

        if (comments.isEmpty()) return ResponseEntity.notFound().build();

        var resources = comments.stream()
                .map(CommentResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }

    @PostMapping
    public ResponseEntity<CommentResource> createComment(@PathVariable Long postId,
                                                         @RequestBody CreateCommentResource resource) {
        try {
            // Validaciones mínimas en el controller
            if (resource.author() == null || resource.author().isBlank()
                    || resource.content() == null || resource.content().isBlank()) {
                return ResponseEntity.badRequest().build();
            }

            var command = CreateCommentCommandFromResourceAssembler.toCommandFromResource(resource, postId);
            var commentId = commentCommandService.handle(command);
            var createdComment = commentQueryService.findById(commentId);

            if (createdComment.isEmpty()) return ResponseEntity.badRequest().build();

            var resourceResponse = CommentResourceFromEntityAssembler.toResourceFromEntity(createdComment.get());
            return new ResponseEntity<>(resourceResponse, HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            // Entrada inválida => 400 (ej: userId malformado)
            return ResponseEntity.badRequest().build();
        } catch (Exception ex) {
            // Registrar para depuración y devolver 500 genérico
            logger.error("Error creating comment for post " + postId, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
