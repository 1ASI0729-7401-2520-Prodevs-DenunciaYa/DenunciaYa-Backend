package com.denunciayabackend.complaintCreation.domain.services;

import com.denunciayabackend.complaintCreation.domain.model.entities.Evidence;
import com.denunciayabackend.complaintCreation.infrastructure.persistence.jpa.repositories.EvidenceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EvidenceService {

    private final EvidenceRepository evidenceRepository;

    public EvidenceService(EvidenceRepository evidenceRepository) {
        this.evidenceRepository = evidenceRepository;
    }

    public List<Evidence> saveEvidencesForComplaint(String complaintId, List<String> evidenceUrls) {
        if (evidenceUrls == null || evidenceUrls.isEmpty()) {
            return List.of();
        }

        return evidenceUrls.stream()
                .map(url -> {
                    Evidence evidence = new Evidence(complaintId, url);
                    return evidenceRepository.save(evidence);
                })
                .collect(Collectors.toList());
    }

    public List<Evidence> getEvidencesByComplaintId(String complaintId) {
        return evidenceRepository.findByComplaintId(complaintId);
    }

    public void deleteEvidencesByComplaintId(String complaintId) {
        evidenceRepository.deleteByComplaintId(complaintId);
    }

    public boolean existsByComplaintIdAndUrl(String complaintId, String url) {
        return evidenceRepository.existsByComplaintIdAndUrl(complaintId, url);
    }
}