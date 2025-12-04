package com.denunciayabackend.complaintCreation.infrastructure.persistence.jpa.repositories;

import com.denunciayabackend.complaintCreation.domain.model.entities.Evidence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvidenceRepository extends JpaRepository<Evidence, Long> {

    List<Evidence> findByComplaintId(String complaintId);

    void deleteByComplaintId(String complaintId);

    boolean existsByComplaintIdAndUrl(String complaintId, String url);

    List<Evidence> findByComplaintIdIn(List<String> complaintIds);
}