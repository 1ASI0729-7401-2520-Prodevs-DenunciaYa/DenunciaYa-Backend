package com.denunciayabackend.authoritiesPanel.infrastructure.persistence.jpa.repositories;

import com.denunciayabackend.authoritiesPanel.domain.model.entities.ComplaintAssignment;
import com.denunciayabackend.authoritiesPanel.domain.model.valueobjects.AssignmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for accessing and querying complaint assignment data.
 * Supports retrieval of active assignments, history records, responsible-based queries,
 * and validation of assignment state.
 */
@Repository
public interface ComplaintAssignmentRepository extends JpaRepository<ComplaintAssignment, String> {

    /**
     * Finds the active assignment of a complaint.
     */
    Optional<ComplaintAssignment> findByComplaintIdAndStatus(String complaintId, AssignmentStatus status);

    /**
     * Retrieves the full assignment history of a complaint, ordered by date.
     */
    List<ComplaintAssignment> findByComplaintIdOrderByAssignedDateDesc(String complaintId);

    /**
     * Retrieves active assignments for a responsible user.
     */
    List<ComplaintAssignment> findByResponsibleIdAndStatus(String responsibleId, AssignmentStatus status);

    /**
     * Retrieves all assignments linked to a responsible user.
     */
    List<ComplaintAssignment> findByResponsibleIdOrderByAssignedDateDesc(String responsibleId);

    /**
     * Counts active assignments of a responsible user.
     */
    long countByResponsibleIdAndStatus(String responsibleId, AssignmentStatus status);

    /**
     * Checks if a complaint already has an active assignment.
     */
    @Query("SELECT CASE WHEN COUNT(ca) > 0 THEN true ELSE false END " +
            "FROM ComplaintAssignment ca " +
            "WHERE ca.complaintId = :complaintId AND ca.status = 'ACTIVE'")
    boolean existsActiveAssignmentForComplaint(@Param("complaintId") String complaintId);

    /**
     * Retrieves assignments by status.
     */
    List<ComplaintAssignment> findByStatusOrderByAssignedDateDesc(AssignmentStatus status);

    /**
     * Retrieves assignments assigned by a specific authority or user.
     */
    List<ComplaintAssignment> findByAssignedByOrderByAssignedDateDesc(String assignedBy);
}