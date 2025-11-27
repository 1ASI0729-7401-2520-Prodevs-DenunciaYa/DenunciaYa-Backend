package com.denunciayabackend.complaintCreation.domain.model.aggregates;

import com.denunciayabackend.complaintCreation.domain.model.commands.CreateComplaintCommand;
import com.denunciayabackend.complaintCreation.domain.model.commands.UpdateComplaintCommand;
import com.denunciayabackend.complaintCreation.domain.model.entities.TimelineItem;
import com.denunciayabackend.complaintCreation.domain.model.valueobjects.ComplaintId;
import com.denunciayabackend.complaintCreation.domain.model.valueobjects.ComplaintPriority;
import com.denunciayabackend.complaintCreation.domain.model.valueobjects.ComplaintStatus;
import com.denunciayabackend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "ComplaintCreation")
@Table(name = "complaint_creation")
public class Complaint extends AuditableAbstractAggregateRoot<Complaint> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "complaint_id", unique = true))
    })
    private ComplaintId complaintId;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String department;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String district;

    @Column(nullable = false)
    private String location;

    @Lob
    private String referenceInfo;

    @Lob
    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ComplaintStatus status = ComplaintStatus.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ComplaintPriority priority = ComplaintPriority.STANDARD;

    @ElementCollection
    @CollectionTable(name = "complaint_evidence", joinColumns = @JoinColumn(name = "complaint_id"))
    @Column(name = "evidence_url")
    private List<String> evidence = new ArrayList<>();

    private String assignedTo;

    private String responsibleId;

    @Lob
    private String updateMessage;

    @OneToMany(mappedBy = "complaint", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<TimelineItem> timeline = new ArrayList<>();

    protected Complaint() { }

    public Complaint(CreateComplaintCommand command) {
        this.complaintId = new ComplaintId(generateComplaintId());
        this.category = command.category();
        this.department = command.department();
        this.city = command.city();
        this.district = command.district();
        this.location = command.location();
        this.referenceInfo = command.referenceInfo();
        this.description = command.description();
        this.priority = command.priority() != null ? command.priority() : ComplaintPriority.STANDARD;
        this.status = ComplaintStatus.PENDING;

        addTimelineItem("Complaint registered", "Complaint successfully registered in the system");
    }

    public void updateComplaint(UpdateComplaintCommand command) {
        this.category = command.category();
        this.department = command.department();
        this.city = command.city();
        this.district = command.district();
        this.location = command.location();
        this.referenceInfo = command.referenceInfo();
        this.description = command.description();
        this.priority = command.priority();
        this.updateMessage = "Denuncia actualizada por el ciudadano";

        addTimelineItem("Complaint updated", "Complaint information updated by citizen");
    }

    public void updateStatus(ComplaintStatus newStatus, String updateMessage) {
        this.status = newStatus;
        this.updateMessage = updateMessage;
        addTimelineItem("Status updated to " + newStatus, updateMessage);
    }

    public void assignTo(String assignedTo, String responsibleId) {
        this.assignedTo = assignedTo;
        this.responsibleId = responsibleId;
        addTimelineItem("Assigned to " + assignedTo, "Complaint assigned to responsible authority");
    }

    public void addEvidence(List<String> evidenceUrls) {
        if (evidenceUrls != null) {
            this.evidence.addAll(evidenceUrls);
        }
    }

    public void updatePriority(ComplaintPriority priority) {
        this.priority = priority;
        addTimelineItem("Priority updated to " + priority, "Complaint priority changed");
    }

    public void deleteComplaint() {
        this.status = ComplaintStatus.REJECTED;
        addTimelineItem("Complaint deleted", "Complaint was deleted from the system");
    }

    private void addTimelineItem(String status, String message) {
        TimelineItem timelineItem = new TimelineItem(this, status, message);
        this.timeline.add(timelineItem);

        // Mark previous items as not current
        this.timeline.forEach(item -> item.setCurrent(false));
        timelineItem.setCurrent(true);
    }

    private String generateComplaintId() {
        return String.valueOf((int) (Math.random() * 900000) + 100000);
    }

    public String getComplaintId() {
        return this.complaintId != null ? this.complaintId.value() : null;
    }

    // Mantener el método para el ID interno
    public Long getId() {
        return this.id;
    }

    public String getAssignedTo() {
        return this.assignedTo;
    }

    public String getCategory() {
        return this.category;
    }

    public String getCity() {
        return this.city;
    }

    public String getDepartment() {
        return this.department;
    }

    public String getDescription() {
        return this.description;
    }

    public String getDistrict() {
        return this.district;
    }

    public List<String> getEvidence() {
        return this.evidence;
    }

    public String getLocation() {
        return this.location;
    }

    public String getPriority() {
        return this.priority != null ? this.priority.toJsonValue() : null;
    }

    public String getReferenceInfo() {
        return this.referenceInfo;
    }

    public String getResponsibleId() {
        return this.responsibleId;
    }
    public String getStatusForJson() {
        return this.status != null ? this.status.toJsonValue() : null;
    }
    public ComplaintStatus getStatus() {
        return this.status;
    }

    public List<TimelineItem> getTimeline() {
        return this.timeline;
    }

    public String getUpdateDate() {
        return this.getUpdatedAt() != null ? this.getUpdatedAt().toString() : this.getCreatedAt().toString();
    }

    public String getUpdateMessage() {
        return this.updateMessage;
    }

    public boolean isCitizenDeletable() {
        return this.status == ComplaintStatus.PENDING || this.status == ComplaintStatus.AWAITING_RESPONSE;
    }

    // Getters para uso interno (no para JSON)
    public ComplaintStatus getStatusEnum() {
        return this.status;
    }

    public ComplaintPriority getPriorityEnum() {
        return this.priority;
    }

    public Long getInternalId() {
        return this.id;
    }

    public ComplaintId getComplaintIdObject() {
        return this.complaintId;
    }

}