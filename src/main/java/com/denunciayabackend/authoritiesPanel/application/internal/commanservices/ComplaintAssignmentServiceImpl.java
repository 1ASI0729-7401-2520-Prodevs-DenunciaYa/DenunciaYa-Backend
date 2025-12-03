package com.denunciayabackend.authoritiesPanel.application.internal.commanservices;

import com.denunciayabackend.authoritiesPanel.domain.model.commands.*;
import com.denunciayabackend.authoritiesPanel.domain.model.entities.ComplaintAssignment;
import com.denunciayabackend.authoritiesPanel.domain.model.valueobjects.AssignmentStatus;
import com.denunciayabackend.authoritiesPanel.domain.services.ComplaintAssignmentService;
import com.denunciayabackend.authoritiesPanel.infrastructure.persistence.jpa.repositories.ComplaintAssignmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComplaintAssignmentServiceImpl implements ComplaintAssignmentService {

    private final ComplaintAssignmentRepository complaintAssignmentRepository;

    public ComplaintAssignmentServiceImpl(ComplaintAssignmentRepository complaintAssignmentRepository) {
        this.complaintAssignmentRepository = complaintAssignmentRepository;
    }

    @Override
    public ComplaintAssignment handle(AssignComplaintCommand command) {
        // Validar que la denuncia no esté ya asignada activamente
        if (isComplaintAlreadyAssigned(command.complaintId())) {
            throw new IllegalStateException("La denuncia ya está asignada a otro responsable");
        }

        // Crear nueva asignación
        ComplaintAssignment assignment = ComplaintAssignment.create(
                command.complaintId(),
                command.responsibleId(),
                command.assignedBy(),
                command.notes()
        );

        // Guardar en repositorio
        return complaintAssignmentRepository.save(assignment);
    }

    @Override
    public ComplaintAssignment handle(UpdateAssignmentStatusCommand command) {
        // Buscar asignación
        ComplaintAssignment assignment = complaintAssignmentRepository.findById(command.assignmentId())
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la asignación con ID: " + command.assignmentId()));

        // Actualizar estado
        assignment.updateStatus(command.status(), command.notes());

        return complaintAssignmentRepository.save(assignment);
    }

    @Override
    public ComplaintAssignment handle(ReassignComplaintCommand command) {
        // Buscar asignación
        ComplaintAssignment assignment = complaintAssignmentRepository.findById(command.assignmentId())
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la asignación con ID: " + command.assignmentId()));

        // Validar que la asignación esté activa
        if (!assignment.isActive()) {
            throw new IllegalStateException("No se puede reasignar una asignación inactiva");
        }

        // Reasignar
        assignment.reassign(command.newResponsibleId(), command.reassignedBy(), command.notes());

        return complaintAssignmentRepository.save(assignment);
    }

    @Override
    public Optional<ComplaintAssignment> getAssignmentById(String id) {
        return complaintAssignmentRepository.findById(id);
    }

    @Override
    public Optional<ComplaintAssignment> getActiveAssignmentByComplaintId(String complaintId) {
        return complaintAssignmentRepository.findByComplaintIdAndStatus(complaintId, AssignmentStatus.ACTIVE);
    }

    @Override
    public List<ComplaintAssignment> getAssignmentHistoryByComplaint(String complaintId) {
        return complaintAssignmentRepository.findByComplaintIdOrderByAssignedDateDesc(complaintId);
    }

    @Override
    public List<ComplaintAssignment> getActiveAssignmentsByResponsible(String responsibleId) {
        return complaintAssignmentRepository.findByResponsibleIdAndStatus(responsibleId, AssignmentStatus.ACTIVE);
    }

    @Override
    public List<ComplaintAssignment> getAllAssignmentsByResponsible(String responsibleId) {
        return complaintAssignmentRepository.findByResponsibleIdOrderByAssignedDateDesc(responsibleId);
    }

    @Override
    public long countActiveAssignmentsByResponsible(String responsibleId) {
        return complaintAssignmentRepository.countByResponsibleIdAndStatus(responsibleId, AssignmentStatus.ACTIVE);
    }

    @Override
    public boolean isComplaintAlreadyAssigned(String complaintId) {
        return complaintAssignmentRepository.existsActiveAssignmentForComplaint(complaintId);
    }
}