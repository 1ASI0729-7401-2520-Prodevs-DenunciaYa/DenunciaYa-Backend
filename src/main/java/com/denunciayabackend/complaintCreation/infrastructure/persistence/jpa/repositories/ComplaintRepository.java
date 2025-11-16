package com.denunciayabackend.complaintCreation.infrastructure.persistence.jpa.repositories;

import com.denunciayabackend.complaintCreation.domain.model.aggregates.Complaint;
import com.denunciayabackend.complaintCreation.domain.model.valueobjects.ComplaintStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {

    Optional<Complaint> findByComplaintIdValue(String complaintId);

    List<Complaint> findByStatus(ComplaintStatus status);

    // ELIMINADO: findByUserIdValue ya que Complaint no tiene userId

    @Query("SELECT c FROM Complaint c WHERE c.department = :department AND c.city = :city")
    List<Complaint> findByDepartmentAndCity(@Param("department") String department, @Param("city") String city);

    @Query("SELECT c FROM Complaint c WHERE c.assignedTo LIKE %:assignedTo%")
    List<Complaint> findByAssignedToContaining(@Param("assignedTo") String assignedTo);

    boolean existsByComplaintIdValue(String complaintId);

    @Query("SELECT COUNT(c) FROM Complaint c WHERE c.status = :status")
    long countByStatus(@Param("status") ComplaintStatus status);

    @Query("SELECT c FROM Complaint c WHERE c.department = :department")
    List<Complaint> findByDepartment(@Param("department") String department);

    @Query("SELECT c FROM Complaint c WHERE c.city = :city")
    List<Complaint> findByCity(@Param("city") String city);
}