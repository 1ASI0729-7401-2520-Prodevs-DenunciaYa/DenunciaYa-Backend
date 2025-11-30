package com.denunciayabackend.authoritiesPanel.interfaces.rest;
import com.denunciayabackend.authoritiesPanel.domain.model.commands.AssignComplaintCommand;
import com.denunciayabackend.authoritiesPanel.domain.model.commands.ReassignComplaintCommand;
import com.denunciayabackend.authoritiesPanel.domain.model.entities.ComplaintAssignment;
import com.denunciayabackend.authoritiesPanel.domain.services.ComplaintAssignmentService;
import com.denunciayabackend.authoritiesPanel.interfaces.rest.resources.ComplaintAssignmentResource;
import com.denunciayabackend.authoritiesPanel.interfaces.rest.resources.CreateComplaintAssignmentResource;
import com.denunciayabackend.authoritiesPanel.interfaces.rest.resources.UpdateComplaintAssignmentResource;
import com.denunciayabackend.authoritiesPanel.interfaces.rest.transform.ComplaintAssignmentResourceAssembler;
import com.denunciayabackend.authoritiesPanel.interfaces.rest.transform.CreateComplaintAssignmentCommandFromResourceAssembler;
import com.denunciayabackend.authoritiesPanel.interfaces.rest.transform.UpdateComplaintAssignmentCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/complaint-assignments")
@Tag(name = "Complaint Assignments", description = "Endpoints for managing complaint assignments")
public class ComplaintAssignmentController {

    private final ComplaintAssignmentService assignmentService;

    public ComplaintAssignmentController(ComplaintAssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @PostMapping
    @Operation(summary = "Assign complaint to responsible")
    public ResponseEntity<ComplaintAssignmentResource> assignComplaint(
            @RequestBody CreateComplaintAssignmentResource resource) {

        var command = CreateComplaintAssignmentCommandFromResourceAssembler.toCommand(resource);
        String assignmentId = assignmentService.handle(command);

        ComplaintAssignment assignment = assignmentService.getAssignmentById(Long.valueOf(assignmentId));
        ComplaintAssignmentResource response = ComplaintAssignmentResourceAssembler.toResourceFromEntity(assignment);

        return new ResponseEntity<>(response, CREATED);
    }

    @PutMapping("/{assignmentId}/reassign")
    @Operation(summary = "Reassign complaint to different responsible")
    public ResponseEntity<ComplaintAssignmentResource> reassignComplaint(
            @PathVariable String assignmentId,
            @RequestBody ReassignComplaintCommand command) {

        assignmentService.handle(command);

        // Obtener la asignación actualizada
        ComplaintAssignment assignment = assignmentService.getAssignmentById(Long.valueOf(assignmentId));
        ComplaintAssignmentResource response = ComplaintAssignmentResourceAssembler.toResourceFromEntity(assignment);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{assignmentId}/status")
    @Operation(summary = "Update assignment status")
    public ResponseEntity<ComplaintAssignmentResource> updateAssignmentStatus(
            @PathVariable String assignmentId,
            @RequestBody UpdateComplaintAssignmentResource resource) {

        var command = UpdateComplaintAssignmentCommandFromResourceAssembler.toCommand(assignmentId, resource);
        assignmentService.handle(command);

        // Obtener la asignación actualizada
        ComplaintAssignment assignment = assignmentService.getAssignmentById(Long.valueOf(assignmentId));
        ComplaintAssignmentResource response = ComplaintAssignmentResourceAssembler.toResourceFromEntity(assignment);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/responsible/{responsibleId}/active")
    @Operation(summary = "Get active assignments for responsible")
    public ResponseEntity<List<ComplaintAssignmentResource>> getActiveAssignments(
            @PathVariable String responsibleId) {

        List<ComplaintAssignment> assignments = assignmentService.getActiveAssignmentsByResponsible(responsibleId);
        List<ComplaintAssignmentResource> resources =
                ComplaintAssignmentResourceAssembler.toResourceListFromEntities(assignments);

        return ResponseEntity.ok(resources);
    }

    @GetMapping("/complaint/{complaintId}/history")
    @Operation(summary = "Get assignment history for complaint")
    public ResponseEntity<List<ComplaintAssignmentResource>> getAssignmentHistory(
            @PathVariable String complaintId) {

        List<ComplaintAssignment> history = assignmentService.getAssignmentHistoryByComplaint(complaintId);
        List<ComplaintAssignmentResource> resources =
                ComplaintAssignmentResourceAssembler.toResourceListFromEntities(history);

        return ResponseEntity.ok(resources);
    }

    @GetMapping("/responsible/{responsibleId}/count")
    @Operation(summary = "Get active complaints count for responsible")
    public ResponseEntity<Long> getActiveComplaintsCount(
            @PathVariable String responsibleId) {

        long count = assignmentService.getActiveComplaintsCountByResponsible(responsibleId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/{assignmentId}")
    @Operation(summary = "Get assignment by ID")
    public ResponseEntity<ComplaintAssignmentResource> getAssignmentById(
            @PathVariable Long assignmentId) {

        ComplaintAssignment assignment = assignmentService.getAssignmentById(assignmentId);
        ComplaintAssignmentResource resource = ComplaintAssignmentResourceAssembler.toResourceFromEntity(assignment);

        return ResponseEntity.ok(resource);
    }
}