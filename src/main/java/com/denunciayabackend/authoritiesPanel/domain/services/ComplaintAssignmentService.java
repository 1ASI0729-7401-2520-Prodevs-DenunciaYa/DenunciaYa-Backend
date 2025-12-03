package com.denunciayabackend.authoritiesPanel.domain.services;

import com.denunciayabackend.authoritiesPanel.domain.model.commands.*;
import com.denunciayabackend.authoritiesPanel.domain.model.entities.ComplaintAssignment;

import java.util.List;
import java.util.Optional;

public interface ComplaintAssignmentService {

    // Crear nueva asignación
    ComplaintAssignment handle(AssignComplaintCommand command);

    // Actualizar estado de asignación
    ComplaintAssignment handle(UpdateAssignmentStatusCommand command);

    // Reasignar denuncia
    ComplaintAssignment handle(ReassignComplaintCommand command);

    // Obtener asignación por ID
    Optional<ComplaintAssignment> getAssignmentById(String id);

    // Obtener asignación activa de una denuncia
    Optional<ComplaintAssignment> getActiveAssignmentByComplaintId(String complaintId);

    // Obtener historial de asignaciones de una denuncia
    List<ComplaintAssignment> getAssignmentHistoryByComplaint(String complaintId);

    // Obtener asignaciones activas por responsable
    List<ComplaintAssignment> getActiveAssignmentsByResponsible(String responsibleId);

    // Obtener todas las asignaciones de un responsable
    List<ComplaintAssignment> getAllAssignmentsByResponsible(String responsibleId);

    // Contar denuncias activas por responsable
    long countActiveAssignmentsByResponsible(String responsibleId);

    // Verificar si una denuncia ya está asignada
    boolean isComplaintAlreadyAssigned(String complaintId);
}