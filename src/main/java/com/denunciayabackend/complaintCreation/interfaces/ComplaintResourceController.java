package com.denunciayabackend.complaintCreation.interfaces;

import com.denunciayabackend.complaintCreation.domain.exceptions.ComplaintNotFoundException;
import com.denunciayabackend.complaintCreation.domain.model.commands.DeleteComplaintCommand;
import com.denunciayabackend.complaintCreation.domain.model.commands.UpdateComplaintStatusCommand;
import com.denunciayabackend.complaintCreation.domain.model.valueobjects.ComplaintStatus;
import com.denunciayabackend.complaintCreation.domain.services.ComplaintCommandService;
import com.denunciayabackend.complaintCreation.domain.services.ComplaintQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/complaints")
@CrossOrigin(origins = "*")
@Tag(name = "Complaints", description = "Complaints Management API")
public class ComplaintResourceController {

    private final ComplaintCommandService complaintCommandService;
    private final ComplaintQueryService complaintQueryService;
    private final ComplaintResourceFromEntityAssembler complaintResourceAssembler;
    private final CreateComplaintCommandFromResourceAssembler createCommandAssembler;
    private final UpdateComplaintCommandFromResourceAssembler updateCommandAssembler;

    public ComplaintResourceController(ComplaintCommandService complaintCommandService,
                                       ComplaintQueryService complaintQueryService,
                                       ComplaintResourceFromEntityAssembler complaintResourceAssembler,
                                       CreateComplaintCommandFromResourceAssembler createCommandAssembler,
                                       UpdateComplaintCommandFromResourceAssembler updateCommandAssembler) {
        this.complaintCommandService = complaintCommandService;
        this.complaintQueryService = complaintQueryService;
        this.complaintResourceAssembler = complaintResourceAssembler;
        this.createCommandAssembler = createCommandAssembler;
        this.updateCommandAssembler = updateCommandAssembler;
    }

    @Operation(summary = "Get all complaints", description = "Returns a list of all complaints in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of complaints"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<ComplaintResource>> getAllComplaints() {
        var complaints = complaintQueryService.findAllComplaints();
        var resources = complaints.stream()
                .map(complaintResourceAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @Operation(summary = "Get complaint by ID", description = "Returns a specific complaint based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Complaint found successfully"),
            @ApiResponse(responseCode = "404", description = "Complaint not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{complaintId}")
    public ResponseEntity<ComplaintResource> getComplaintById(
            @Parameter(description = "Complaint ID", example = "703625", required = true)
            @PathVariable String complaintId) {

        var complaint = complaintQueryService.findComplaintByComplaintId(complaintId)
                .orElseThrow(() -> new ComplaintNotFoundException(complaintId));
        var resource = complaintResourceAssembler.toResourceFromEntity(complaint);
        return ResponseEntity.ok(resource);
    }

    @Operation(summary = "Create new complaint", description = "Creates a new complaint in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Complaint created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<ComplaintResource> createComplaint(
            @Parameter(description = "Complaint data to create", required = true)
            @RequestBody CreateComplaintResource resource) {

        var command = createCommandAssembler.toCommandFromResource(resource);
        var complaint = complaintCommandService.handle(command);
        var complaintResource = complaintResourceAssembler.toResourceFromEntity(complaint);
        return ResponseEntity.ok(complaintResource);
    }

    @Operation(summary = "Update complaint", description = "Updates data of an existing complaint")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Complaint updated successfully"),
            @ApiResponse(responseCode = "404", description = "Complaint not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{complaintId}")
    public ResponseEntity<ComplaintResource> updateComplaint(
            @Parameter(description = "Complaint ID to update", example = "703625", required = true)
            @PathVariable String complaintId,
            @Parameter(description = "Updated complaint data", required = true)
            @RequestBody UpdateComplaintResource resource) {

        var command = updateCommandAssembler.toCommandFromResource(complaintId, resource);
        var complaint = complaintCommandService.handle(command);
        var complaintResource = complaintResourceAssembler.toResourceFromEntity(complaint);
        return ResponseEntity.ok(complaintResource);
    }

    @Operation(summary = "Delete complaint", description = "Permanently deletes a complaint from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Complaint deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Complaint not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{complaintId}")
    public ResponseEntity<Void> deleteComplaint(
            @Parameter(description = "Complaint ID to delete", example = "703625", required = true)
            @PathVariable String complaintId) {

        complaintCommandService.handle(new DeleteComplaintCommand(complaintId));
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get complaints by status", description = "Returns complaints filtered by status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of complaints"),
            @ApiResponse(responseCode = "400", description = "Invalid status"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/status/{status}")
    public ResponseEntity<List<ComplaintResource>> getComplaintsByStatus(
            @Parameter(description = "Status to filter complaints", example = "PENDING", required = true)
            @PathVariable String status) {

        try {
            var complaintStatus = ComplaintStatus.valueOf(status.toUpperCase());
            var complaints = complaintQueryService.findComplaintsByStatus(complaintStatus);
            var resources = complaints.stream()
                    .map(complaintResourceAssembler::toResourceFromEntity)
                    .toList();
            return ResponseEntity.ok(resources);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status: " + status);
        }
    }

    @Operation(summary = "Update complaint status", description = "Updates the status of a specific complaint")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status updated successfully"),
            @ApiResponse(responseCode = "404", description = "Complaint not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping("/{complaintId}/status")
    public ResponseEntity<ComplaintResource> updateComplaintStatus(
            @Parameter(description = "Complaint ID", example = "703625", required = true)
            @PathVariable String complaintId,
            @Parameter(description = "Status update data", required = true)
            @RequestBody UpdateComplaintStatusRequest request) {

        var command = new UpdateComplaintStatusCommand(
                complaintId,
                ComplaintStatus.fromJsonValue(request.status()),
                request.updateMessage()
        );
        var complaint = complaintCommandService.handle(command);
        var resource = complaintResourceAssembler.toResourceFromEntity(complaint);
        return ResponseEntity.ok(resource);
    }

    @Operation(summary = "Get complaints by location", description = "Returns complaints filtered by department and city")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of complaints"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/department/{department}/city/{city}")
    public ResponseEntity<List<ComplaintResource>> getComplaintsByLocation(
            @Parameter(description = "Department to filter", example = "Lima", required = true)
            @PathVariable String department,
            @Parameter(description = "City to filter", example = "Lima", required = true)
            @PathVariable String city) {

        var complaints = complaintQueryService.findComplaintsByDepartmentAndCity(department, city);
        var resources = complaints.stream()
                .map(complaintResourceAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }
}

record UpdateComplaintStatusRequest(String status, String updateMessage) { }