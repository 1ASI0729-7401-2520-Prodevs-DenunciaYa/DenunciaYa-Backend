package com.denunciayabackend.complaintCreation.interfaces;

import com.denunciayabackend.complaintCreation.domain.model.commands.UpdateComplaintStatusCommand;
import com.denunciayabackend.complaintCreation.domain.model.valueobjects.ComplaintStatus;
import com.denunciayabackend.complaintCreation.domain.services.ComplaintCommandService;
import com.denunciayabackend.complaintCreation.domain.services.ComplaintQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/complaints")
@CrossOrigin(origins = "*")
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

    @GetMapping
    public ResponseEntity<List<ComplaintResource>> getAllComplaints() {
        var complaints = complaintQueryService.findAllComplaints();
        var resources = complaints.stream()
                .map(complaintResourceAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{complaintId}")
    public ResponseEntity<ComplaintResource> getComplaintById(@PathVariable String complaintId) {
        var complaint = complaintQueryService.findComplaintByComplaintId(complaintId)
                .orElseThrow(() -> new RuntimeException("Complaint not found with id: " + complaintId));
        var resource = complaintResourceAssembler.toResourceFromEntity(complaint);
        return ResponseEntity.ok(resource);
    }

    @PostMapping
    public ResponseEntity<ComplaintResource> createComplaint(@RequestBody CreateComplaintResource resource) {
        var command = createCommandAssembler.toCommandFromResource(resource);
        var complaint = complaintCommandService.handle(command);
        var complaintResource = complaintResourceAssembler.toResourceFromEntity(complaint);
        return ResponseEntity.ok(complaintResource);
    }

    @PutMapping("/{complaintId}")
    public ResponseEntity<ComplaintResource> updateComplaint(
            @PathVariable String complaintId,
            @RequestBody UpdateComplaintResource resource) {
        var command = updateCommandAssembler.toCommandFromResource(complaintId, resource);
        var complaint = complaintCommandService.handle(command);
        var complaintResource = complaintResourceAssembler.toResourceFromEntity(complaint);
        return ResponseEntity.ok(complaintResource);
    }

    @DeleteMapping("/{complaintId}")
    public ResponseEntity<Void> deleteComplaint(@PathVariable String complaintId) {
        complaintCommandService.handle(new com.denunciayabackend.complaintCreation.domain.model.commands.DeleteComplaintCommand(complaintId));
        return ResponseEntity.noContent().build();
    }

    // ELIMINADO: getComplaintsByUser ya que no hay userId

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ComplaintResource>> getComplaintsByStatus(@PathVariable String status) {
        var complaintStatus = ComplaintStatus.valueOf(status.toUpperCase());
        var complaints = complaintQueryService.findComplaintsByStatus(complaintStatus);
        var resources = complaints.stream()
                .map(complaintResourceAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @PatchMapping("/{complaintId}/status")
    public ResponseEntity<ComplaintResource> updateComplaintStatus(
            @PathVariable String complaintId,
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
    @GetMapping("/department/{department}/city/{city}")
    public ResponseEntity<List<ComplaintResource>> getComplaintsByLocation(
            @PathVariable String department,
            @PathVariable String city) {
        var complaints = complaintQueryService.findComplaintsByDepartmentAndCity(department, city);
        var resources = complaints.stream()
                .map(complaintResourceAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }
}

record UpdateComplaintStatusRequest(String status, String updateMessage) { }