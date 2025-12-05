package com.denunciayabackend.complaintCreation.domain.model.entities;

import com.denunciayabackend.complaintCreation.domain.model.aggregates.Complaint;
import com.denunciayabackend.shared.domain.model.entities.AuditableModel;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "complaint_timelines")
public class TimelineItem extends AuditableModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private boolean completed;

    @Column(nullable = false)
    private boolean current;

    private boolean waitingDecision;

    @Column(length = 1000)
    private String updateMessage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "complaint_id", nullable = false)
    private Complaint complaint;

    protected TimelineItem() { }

    public TimelineItem(Complaint complaint, String status, String updateMessage) {
        this.complaint = complaint;
        this.status = status;
        this.updateMessage = updateMessage;
        this.date = LocalDateTime.now();
        this.completed = true;
        this.current = true;
        this.waitingDecision = false;
    }

    public TimelineItem(Complaint complaint, String status, LocalDateTime date, boolean completed, boolean current, boolean waitingDecision) {
        this.complaint = complaint;
        this.status = status;
        this.date = date;
        this.completed = completed;
        this.current = current;
        this.waitingDecision = waitingDecision;
        this.updateMessage = "";
    }

    public void markAsCompleted() {
        this.completed = true;
        this.current = false;
        this.waitingDecision = false;
        this.updateMessage = updateMessage != null ? updateMessage : this.updateMessage;
        this.date = LocalDateTime.now();
    }

    public void markAsCurrent() {
        this.current = true;
        this.completed = false;
        this.waitingDecision = false;
        this.updateMessage = updateMessage != null ? updateMessage : this.updateMessage;
        this.date = LocalDateTime.now();
    }
    public void markAsWaitingDecision(String updateMessage) {
        this.waitingDecision = true;
        this.current = true;
        this.completed = false;
        this.updateMessage = updateMessage != null ? updateMessage : this.updateMessage;
        this.date = LocalDateTime.now();
    }
    public void clearStates() {
        this.completed = false;
        this.current = false;
        this.waitingDecision = false;
    }
    public String getStateDescription() {
        if (this.completed) return "Completed";
        if (this.current) return "Current";
        if (this.waitingDecision) return "Waiting Decision";
        return "Pending";
    }

    public boolean isCompleted() {
        return this.completed;
    }

    public boolean isCurrent() {
        return this.current;
    }

    public LocalDateTime getDate() {
        return this.date;
    }

    public String getStatus() {
        return this.status;
    }

    public boolean isWaitingDecision() {
        return this.waitingDecision;
    }
}