package com.denunciayabackend.authoritiesPanel.interfaces.rest;

import com.denunciayabackend.authoritiesPanel.domain.model.commands.DeleteResponsibleCommand;
import com.denunciayabackend.authoritiesPanel.domain.model.commands.UpdateResponsibleProfileCommand;
import com.denunciayabackend.authoritiesPanel.domain.model.queries.GetAllResponsibleQuery;
import com.denunciayabackend.authoritiesPanel.domain.model.queries.GetResponsibleByIdQuery;
import com.denunciayabackend.authoritiesPanel.domain.model.queries.GetResponsibleWithComplaintCountQuery;
import com.denunciayabackend.authoritiesPanel.domain.model.queries.SearchResponsibleQuery;
import com.denunciayabackend.authoritiesPanel.domain.services.ResponsibleCommandService;
import com.denunciayabackend.authoritiesPanel.domain.services.ResponsibleQueryService;
import com.denunciayabackend.authoritiesPanel.interfaces.rest.resources.CreateResponsibleResource;
import com.denunciayabackend.authoritiesPanel.interfaces.rest.resources.ResponsibleResource;
import com.denunciayabackend.authoritiesPanel.interfaces.rest.resources.UpdateResponsibleResource;
import com.denunciayabackend.authoritiesPanel.interfaces.rest.transform.CreateResponsibleCommandFromResourceAssembler;
import com.denunciayabackend.authoritiesPanel.interfaces.rest.transform.ResponsibleResourceFromAssembler;
import com.denunciayabackend.authoritiesPanel.interfaces.rest.transform.UpdateResponsibleCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/responsibles", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Responsibles", description = "Endpoints for managing responsibles")
public class ResponsibleController {

    private final ResponsibleQueryService responsibleQueryService;
    private final ResponsibleCommandService responsibleCommandService;

    public ResponsibleController(ResponsibleQueryService responsibleQueryService,
                                 ResponsibleCommandService responsibleCommandService) {
        this.responsibleQueryService = responsibleQueryService;
        this.responsibleCommandService = responsibleCommandService;
    }

    // ---------------------- CREATE ----------------------
    @Operation(summary = "Create Responsible", description = "Creates a new responsible")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Responsible created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<ResponsibleResource> createResponsible(@RequestBody CreateResponsibleResource resource) {
        var command = CreateResponsibleCommandFromResourceAssembler.toCommand(resource);
        Long createdId = responsibleCommandService.handle(command);

        if (createdId != null) {
            ResponsibleQueryService.ResponsibleService dto = responsibleQueryService.handle(new GetResponsibleByIdQuery(createdId));
            return new ResponseEntity<>(ResponsibleResourceFromAssembler.fromDTO(dto), CREATED);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    // ---------------------- GET BY ID ----------------------
    @Operation(summary = "Get Responsible by ID", description = "Retrieves a responsible by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Responsible found successfully"),
            @ApiResponse(responseCode = "404", description = "Responsible not found")
    })
    @GetMapping("{id}")
    public ResponseEntity<ResponsibleResource> getResponsibleById(@PathVariable Long id) { // CAMBIADO A Long
        ResponsibleQueryService.ResponsibleService dto = responsibleQueryService.handle(new GetResponsibleByIdQuery(id));
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(ResponsibleResourceFromAssembler.fromDTO(dto));
    }

    // ---------------------- GET ALL ----------------------
    @Operation(summary = "Get All Responsibles", description = "Retrieves all responsibles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Responsibles retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<ResponsibleResource>> getAllResponsibles() {
        List<ResponsibleQueryService.ResponsibleService> dtos = responsibleQueryService.handle(new GetAllResponsibleQuery());
        List<ResponsibleResource> resources = dtos.stream()
                .map(ResponsibleResourceFromAssembler::fromDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }

    // ---------------------- SEARCH ----------------------
    @Operation(summary = "Search Responsibles", description = "Search responsibles by keyword (name or role)")
    @GetMapping("/search")
    public ResponseEntity<List<ResponsibleResource>> searchResponsibles(@RequestParam String keyword) {
        List<ResponsibleQueryService.ResponsibleService> dtos = responsibleQueryService.handle(new SearchResponsibleQuery(keyword));
        List<ResponsibleResource> resources = dtos.stream()
                .map(ResponsibleResourceFromAssembler::fromDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }

    // ---------------------- UPDATE ----------------------
    @Operation(summary = "Update Responsible", description = "Updates a responsible's profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Responsible updated successfully"),
            @ApiResponse(responseCode = "404", description = "Responsible not found")
    })
    @PutMapping("{id}")
    public ResponseEntity<ResponsibleResource> updateResponsible(@PathVariable Long id, // CAMBIADO A Long
                                                                 @RequestBody UpdateResponsibleResource resource) {
        var command = UpdateResponsibleCommandFromResourceAssembler.toCommand(String.valueOf(id), resource);
        responsibleCommandService.handle(command);

        ResponsibleQueryService.ResponsibleService dto = responsibleQueryService.handle(new GetResponsibleByIdQuery(id));
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(ResponsibleResourceFromAssembler.fromDTO(dto));
    }

    // ---------------------- DELETE ----------------------
    @Operation(summary = "Delete Responsible", description = "Deletes a responsible by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Responsible deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Responsible not found")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteResponsible(@PathVariable Long id) { // CAMBIADO A Long
        responsibleCommandService.handle(new DeleteResponsibleCommand(id));
        return ResponseEntity.ok().build();
    }
}