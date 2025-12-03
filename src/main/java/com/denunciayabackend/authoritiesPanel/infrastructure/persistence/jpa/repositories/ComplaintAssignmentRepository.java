package com.denunciayabackend.authoritiesPanel.infrastructure.persistence.jpa.repositories;

import com.denunciayabackend.authoritiesPanel.domain.model.entities.ComplaintAssignment;
import com.denunciayabackend.authoritiesPanel.domain.model.valueobjects.AssignmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComplaintAssignmentRepository extends JpaRepository<ComplaintAssignment, String> {

    // Encontrar asignación activa actual de una denuncia
    Optional<ComplaintAssignment> findByComplaintIdAndStatus(String complaintId, AssignmentStatus status);

    // Encontrar todas las asignaciones de una denuncia (historial)
    List<ComplaintAssignment> findByComplaintIdOrderByAssignedDateDesc(String complaintId);

    // Encontrar asignaciones activas por responsable
    List<ComplaintAssignment> findByResponsibleIdAndStatus(String responsibleId, AssignmentStatus status);

    // Encontrar todas las asignaciones de un responsable
    List<ComplaintAssignment> findByResponsibleIdOrderByAssignedDateDesc(String responsibleId);

    // Contar denuncias activas por responsable
    long countByResponsibleIdAndStatus(String responsibleId, AssignmentStatus status);

    // Verificar si una denuncia ya tiene asignación activa
    @Query("SELECT CASE WHEN COUNT(ca) > 0 THEN true ELSE false END " +
            "FROM ComplaintAssignment ca " +
            "WHERE ca.complaintId = :complaintId AND ca.status = 'ACTIVE'")
    boolean existsActiveAssignmentForComplaint(@Param("complaintId") String complaintId);

    // Encontrar asignaciones por estado
    List<ComplaintAssignment> findByStatusOrderByAssignedDateDesc(AssignmentStatus status);

    // Encontrar asignaciones por quien asignó
    List<ComplaintAssignment> findByAssignedByOrderByAssignedDateDesc(String assignedBy);
}