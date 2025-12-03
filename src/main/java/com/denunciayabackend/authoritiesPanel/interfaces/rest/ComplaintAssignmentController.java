package com.denunciayabackend.authoritiesPanel.interfaces.rest;

import com.denunciayabackend.authoritiesPanel.domain.services.ComplaintAssignmentService;
import com.denunciayabackend.authoritiesPanel.interfaces.rest.resources.*;
import com.denunciayabackend.authoritiesPanel.interfaces.rest.transform.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/complaint-assignments")
@Tag(name = "Complaint Assignments", description = "Endpoints for managing complaint assignments")
public class ComplaintAssignmentController {

    private final ComplaintAssignmentService assignmentService;

    public ComplaintAssignmentController(ComplaintAssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @PostMapping
    @Operation(summary = "Assign a complaint to a responsible person")
    public ResponseEntity<ComplaintAssignmentResource> assignComplaint(
            @RequestBody CreateComplaintAssignmentResource resource) {

        var command = ComplaintAssignmentCommandAssembler.toAssignCommandFromResource(resource);
        var assignment = assignmentService.handle(command);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ComplaintAssignmentResource.fromEntity(assignment));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get assignment by ID")
    public ResponseEntity<ComplaintAssignmentResource> getAssignmentById(@PathVariable String id) {
        return assignmentService.getAssignmentById(id)
                .map(assignment -> ResponseEntity.ok(ComplaintAssignmentResource.fromEntity(assignment)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/complaint/{complaintId}/active")
    @Operation(summary = "Get active assignment for a complaint")
    public ResponseEntity<ComplaintAssignmentResource> getActiveAssignmentByComplaint(
            @PathVariable String complaintId) {

        return assignmentService.getActiveAssignmentByComplaintId(complaintId)
                .map(assignment -> ResponseEntity.ok(ComplaintAssignmentResource.fromEntity(assignment)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/complaint/{complaintId}/history")
    @Operation(summary = "Get assignment history for a complaint")
    public ResponseEntity<List<ComplaintAssignmentResource>> getAssignmentHistory(
            @PathVariable String complaintId) {

        var assignments = assignmentService.getAssignmentHistoryByComplaint(complaintId);
        var resources = assignments.stream()
                .map(ComplaintAssignmentResource::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(resources);
    }

    @GetMapping("/responsible/{responsibleId}/active")
    @Operation(summary = "Get active assignments for a responsible person")
    public ResponseEntity<List<ComplaintAssignmentResource>> getActiveAssignmentsByResponsible(
            @PathVariable String responsibleId) {

        var assignments = assignmentService.getActiveAssignmentsByResponsible(responsibleId);
        var resources = assignments.stream()
                .map(ComplaintAssignmentResource::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(resources);
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Update assignment status")
    public ResponseEntity<ComplaintAssignmentResource> updateAssignmentStatus(
            @PathVariable String id,
            @RequestBody UpdateComplaintAssignmentResource resource) {

        var command = UpdateAssignmentCommandAssembler.toCommandFromResource(id, resource);
        var assignment = assignmentService.handle(command);

        return ResponseEntity.ok(ComplaintAssignmentResource.fromEntity(assignment));
    }

    @PutMapping("/{id}/reassign")
    @Operation(summary = "Reassign complaint to another responsible")
    public ResponseEntity<ComplaintAssignmentResource> reassignComplaint(
            @PathVariable String id,
            @RequestBody ReassignComplaintResource resource) {

        var command = ReassignCommandAssembler.toCommandFromResource(id, resource);
        var assignment = assignmentService.handle(command);

        return ResponseEntity.ok(ComplaintAssignmentResource.fromEntity(assignment));
    }

    @GetMapping("/complaint/{complaintId}/is-assigned")
    @Operation(summary = "Check if a complaint is already assigned")
    public ResponseEntity<Boolean> isComplaintAlreadyAssigned(@PathVariable String complaintId) {
        var isAssigned = assignmentService.isComplaintAlreadyAssigned(complaintId);
        return ResponseEntity.ok(isAssigned);
    }

    @GetMapping("/responsible/{responsibleId}/count")
    @Operation(summary = "Count active assignments for a responsible person")
    public ResponseEntity<Long> countActiveAssignmentsByResponsible(
            @PathVariable String responsibleId) {

        var count = assignmentService.countActiveAssignmentsByResponsible(responsibleId);
        return ResponseEntity.ok(count);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleIllegalStateException(IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}