package com.denunciayabackend.authoritiesPanel.domain.model.entities;

import com.denunciayabackend.authoritiesPanel.domain.model.valueobjects.AssignmentStatus;
import com.denunciayabackend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "complaint_assignments")
@Getter
public class ComplaintAssignment extends AuditableAbstractAggregateRoot<ComplaintAssignment> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "complaint_id", nullable = false)
    private String complaintId; // ID de la denuncia

    @Column(name = "responsible_id", nullable = false)
    private String responsibleId; // ID del responsable

    @Column(name = "assigned_date", nullable = false)
    private LocalDateTime assignedDate;

    @Column(name = "assigned_by", nullable = false)
    private String assignedBy; // Quién asignó

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AssignmentStatus status;

    protected ComplaintAssignment() {}

    public ComplaintAssignment(String complaintId, String responsibleId,
                               String assignedBy, AssignmentStatus status) {
        this.complaintId = complaintId;
        this.responsibleId = responsibleId;
        this.assignedBy = assignedBy;
        this.status = status;
        this.assignedDate = LocalDateTime.now();
    }

    public void updateStatus(AssignmentStatus newStatus) {
        this.status = newStatus;
    }

    public void reassign(String newResponsibleId, String assignedBy) {
        this.responsibleId = newResponsibleId;
        this.assignedBy = assignedBy;
        this.status = AssignmentStatus.REASSIGNED;
        this.assignedDate = LocalDateTime.now();
    }


}