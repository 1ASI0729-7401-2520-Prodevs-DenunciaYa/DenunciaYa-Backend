package com.denunciayabackend.map.infrastructure.persistence.jpa.repositories;

import com.denunciayabackend.map.domain.model.entities.MapComplaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IReadOnlyComplaintRepository extends JpaRepository<MapComplaint, String> {

    @Query("SELECT c FROM MapComplaint c WHERE " +
            "(:category IS NULL OR c.category = :category) AND " +
            "(:district IS NULL OR c.district = :district) AND " +
            "(:status IS NULL OR c.status = :status)")
    List<MapComplaint> findFilteredComplaints(
            @Param("category") String category,
            @Param("district") String district,
            @Param("status") String status);

}
