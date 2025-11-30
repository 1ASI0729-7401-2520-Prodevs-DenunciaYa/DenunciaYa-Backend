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
public interface ComplaintAssignmentRepository extends JpaRepository<ComplaintAssignment, Long> {

    // Encontrar asignaciones activas por responsable
    List<ComplaintAssignment> findByResponsibleIdAndStatus(String responsibleId, AssignmentStatus status);

    // Encontrar asignaciones por denuncia
    List<ComplaintAssignment> findByComplaintId(String complaintId);

    // Encontrar la asignación activa actual de una denuncia
    @Query("SELECT ca FROM ComplaintAssignment ca WHERE ca.complaintId = :complaintId AND ca.status = 'ACTIVE'")
    Optional<ComplaintAssignment> findActiveAssignmentByComplaintId(@Param("complaintId") String complaintId);

    // Contar denuncias activas por responsable
    long countByResponsibleIdAndStatus(String responsibleId, AssignmentStatus status);

    // Encontrar todas las asignaciones de un responsable
    List<ComplaintAssignment> findByResponsibleId(String responsibleId);
}