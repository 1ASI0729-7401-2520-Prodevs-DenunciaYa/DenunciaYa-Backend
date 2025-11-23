package com.denunciayabackend.complaintCreation.domain.model.entities;

import com.denunciayabackend.complaintCreation.domain.model.aggregates.Complaint;
import jakarta.persistence.*;

@Entity
@Table(name = "complaint_evidence")
public class ComplaintEvidenceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "evidence_url", nullable = false)
    private String evidenceUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "complaint_id", nullable = false)
    private Complaint complaint;

    public ComplaintEvidenceEntity() {}

    public ComplaintEvidenceEntity(String evidenceUrl, Complaint complaint) {
        this.evidenceUrl = evidenceUrl;
        this.complaint = complaint;
    }

    public Long getId() { return id; }
    public String getEvidenceUrl() { return evidenceUrl; }
}
