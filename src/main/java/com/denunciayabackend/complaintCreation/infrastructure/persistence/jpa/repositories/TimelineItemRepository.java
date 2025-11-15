package com.denunciayabackend.complaintCreation.infrastructure.persistence.jpa.repositories;

import com.denunciayabackend.complaintCreation.domain.model.entities.TimelineItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TimelineItemRepository extends JpaRepository<TimelineItem, Long> {

    @Query("SELECT t FROM TimelineItem t WHERE t.complaint.id = :complaintId ORDER BY t.date ASC")
    List<TimelineItem> findByComplaintId(@Param("complaintId") Long complaintId);

    @Query("SELECT t FROM TimelineItem t WHERE t.complaint.complaintId.value = :complaintId ORDER BY t.date ASC")
    List<TimelineItem> findByComplaintComplaintId(@Param("complaintId") String complaintId);

    @Modifying
    @Query("DELETE FROM TimelineItem t WHERE t.complaint.id = :complaintId")
    void deleteByComplaintId(@Param("complaintId") Long complaintId);

    @Query("SELECT t FROM TimelineItem t WHERE t.current = true AND t.complaint.id = :complaintId")
    Optional<TimelineItem> findCurrentByComplaintId(@Param("complaintId") Long complaintId);

    @Query("SELECT COUNT(t) FROM TimelineItem t WHERE t.complaint.id = :complaintId AND t.completed = true")
    long countCompletedByComplaintId(@Param("complaintId") Long complaintId);
}