package com.denunciayabackend.authoritiesPanel.domain.model.entities;

import com.denunciayabackend.authoritiesPanel.domain.model.valueobjects.AssignmentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "complaint_assignments")
@Getter
@Setter
public class ComplaintAssignment {

    @Id
    @Column(name = "id", nullable = false, length = 36)
    private String id;

    @Column(name = "complaint_id", nullable = false, length = 36)
    private String complaintId;

    @Column(name = "responsible_id", nullable = false, length = 36)
    private String responsibleId;

    @Column(name = "assigned_date", nullable = false)
    private LocalDateTime assignedDate;

    @Column(name = "assigned_by", nullable = false, length = 100)
    private String assignedBy;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AssignmentStatus status;

    @Column(name = "notes", length = 500)
    private String notes;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Constructor protegido para JPA
    protected ComplaintAssignment() {
        // JPA requiere un constructor protegido o público sin argumentos
    }

    // Constructor principal
    private ComplaintAssignment(String id, String complaintId, String responsibleId,
                                String assignedBy, String notes) {
        this.id = id;
        this.complaintId = complaintId;
        this.responsibleId = responsibleId;
        this.assignedBy = assignedBy;
        this.notes = notes;
        this.status = AssignmentStatus.ACTIVE;
        this.assignedDate = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Factory Method
    public static ComplaintAssignment create(String complaintId, String responsibleId,
                                             String assignedBy, String notes) {
        // Validaciones
        if (complaintId == null || complaintId.isBlank()) {
            throw new IllegalArgumentException("Complaint ID cannot be null or empty");
        }
        if (responsibleId == null || responsibleId.isBlank()) {
            throw new IllegalArgumentException("Responsible ID cannot be null or empty");
        }
        if (assignedBy == null || assignedBy.isBlank()) {
            throw new IllegalArgumentException("Assigned by cannot be null or empty");
        }

        // Generar ID único (puedes usar UUID)
        String id = "ASSIGN-" + java.util.UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        return new ComplaintAssignment(id, complaintId, responsibleId, assignedBy, notes);
    }

    // Métodos de dominio
    public void updateStatus(AssignmentStatus newStatus, String notes) {
        if (newStatus == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        this.status = newStatus;
        if (notes != null && !notes.isBlank()) {
            this.notes = notes;
        }
        this.updatedAt = LocalDateTime.now();
    }

    public void reassign(String newResponsibleId, String reassignedBy, String notes) {
        if (newResponsibleId == null || newResponsibleId.isBlank()) {
            throw new IllegalArgumentException("New responsible ID cannot be null or empty");
        }
        if (reassignedBy == null || reassignedBy.isBlank()) {
            throw new IllegalArgumentException("Reassigned by cannot be null or empty");
        }

        this.responsibleId = newResponsibleId;
        this.assignedBy = reassignedBy;
        this.notes = notes != null ? notes : this.notes;
        this.status = AssignmentStatus.REASSIGNED;
        this.assignedDate = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void completeAssignment(String notes) {
        this.status = AssignmentStatus.COMPLETED;
        if (notes != null && !notes.isBlank()) {
            this.notes = notes;
        }
        this.updatedAt = LocalDateTime.now();
    }

    public void cancelAssignment(String notes) {
        this.status = AssignmentStatus.CANCELLED;
        if (notes != null && !notes.isBlank()) {
            this.notes = notes;
        }
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isActive() {
        return this.status == AssignmentStatus.ACTIVE;
    }

    // Método para reabrir una asignación completada/cancelada
    public void reopenAssignment(String notes) {
        if (this.status == AssignmentStatus.COMPLETED || this.status == AssignmentStatus.CANCELLED) {
            this.status = AssignmentStatus.ACTIVE;
            if (notes != null && !notes.isBlank()) {
                this.notes = notes;
            }
            this.updatedAt = LocalDateTime.now();
        }
    }
}