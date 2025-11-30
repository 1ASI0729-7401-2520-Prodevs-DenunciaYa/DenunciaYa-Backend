package com.denunciayabackend.authoritiesPanel.application.internal.commanservices;

import com.denunciayabackend.authoritiesPanel.domain.model.commands.*;
import com.denunciayabackend.authoritiesPanel.domain.model.entities.ComplaintAssignment;
import com.denunciayabackend.authoritiesPanel.domain.model.valueobjects.AssignmentStatus;
import com.denunciayabackend.authoritiesPanel.domain.services.ComplaintAssignmentService;
import com.denunciayabackend.authoritiesPanel.infrastructure.persistence.jpa.repositories.ComplaintAssignmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ComplaintAssignmentServiceImpl implements ComplaintAssignmentService {

    private final ComplaintAssignmentRepository assignmentRepository;

    public ComplaintAssignmentServiceImpl(ComplaintAssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    @Override
    public String handle(AssignComplaintCommand command) {
        // Validar que no exista una asignación activa para esta denuncia
        assignmentRepository.findActiveAssignmentByComplaintId(command.complaintId())
                .ifPresent(assignment -> {
                    assignment.updateStatus(AssignmentStatus.REASSIGNED);
                    assignmentRepository.save(assignment);
                });

        // Crear nueva asignación
        var assignment = new ComplaintAssignment(
                command.complaintId(),
                command.responsibleId(),
                command.assignedBy(),
                AssignmentStatus.ACTIVE
        );

        assignmentRepository.save(assignment);
        return assignment.getId().toString();
    }

    @Override
    public void handle(ReassignComplaintCommand command) {
        var assignment = assignmentRepository.findById(Long.valueOf(command.assignmentId()))
                .orElseThrow(() -> new IllegalArgumentException("Assignment not found with id: " + command.assignmentId()));

        assignment.reassign(command.newResponsibleId(), command.assignedBy());
        assignmentRepository.save(assignment);
    }

    @Override
    public void handle(UpdateAssignmentStatusCommand command) {
        var assignment = assignmentRepository.findById(Long.valueOf(command.assignmentId()))
                .orElseThrow(() -> new IllegalArgumentException("Assignment not found with id: " + command.assignmentId()));

        assignment.updateStatus(command.newStatus());
        assignmentRepository.save(assignment);
    }

    @Override
    public List<ComplaintAssignment> getActiveAssignmentsByResponsible(String responsibleId) {
        return assignmentRepository.findByResponsibleIdAndStatus(responsibleId, AssignmentStatus.ACTIVE);
    }

    @Override
    public long getActiveComplaintsCountByResponsible(String responsibleId) {
        return assignmentRepository.countByResponsibleIdAndStatus(responsibleId, AssignmentStatus.ACTIVE);
    }

    @Override
    public List<ComplaintAssignment> getAssignmentHistoryByComplaint(String complaintId) {
        return assignmentRepository.findByComplaintId(complaintId);
    }

    @Override
    public ComplaintAssignment getAssignmentById(Long assignmentId) {
        return assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new IllegalArgumentException("Assignment not found with id: " + assignmentId));
    }

    @Override
    public String handle(CreateComplaintAssignmentCommand command) {
        return "";
    }

    @Override
    public String handle(com.denunciayabackend.complaintCreation.domain.model.commands.AssignComplaintCommand command) {
        return "";
    }
}