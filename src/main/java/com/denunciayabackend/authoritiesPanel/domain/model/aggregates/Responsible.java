package com.denunciayabackend.authoritiesPanel.domain.model.aggregates;

import com.denunciayabackend.authoritiesPanel.domain.model.entities.AssignedComplaints;
import com.denunciayabackend.authoritiesPanel.domain.model.valueobjects.*;
import com.denunciayabackend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Getter
public class Responsible extends AuditableAbstractAggregateRoot<Responsible> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "responsible_id"))
    private ResponsibleId responsibleId;


    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "full_name"))
    })
    private FullName fullName;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "email"))
    })
    private Email email;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "phone_number"))
    })
    private PhoneNumber phoneNumber;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "position"))
    })
    private Position position;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "department"))
    })
    private Department department;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "role"))
    })
    private Role role;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "description"))
    })
    private Description description;


    @Enumerated(EnumType.STRING)
    private AccessLevel accessLevel;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "responsible", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AssignedComplaints> assignedComplaints = new ArrayList<>();

    protected Responsible() {}

    public Responsible(
            ResponsibleId id,
            FullName fullName,
            Email email,
            PhoneNumber phoneNumber,
            Position position,
            Department department,
            Role role,
            Description description,
            AccessLevel accessLevel,
            Status status,
            List<AssignedComplaints> assignedComplaints
    ) {
        this.responsibleId = requireNonNull(id, "ResponsibleId");
        this.fullName = requireNonNull(fullName, "FullName");
        this.email = requireNonNull(email, "Email");
        this.phoneNumber = requireNonNull(phoneNumber, "PhoneNumber");
        this.position = requireNonNull(position, "Position");
        this.department = requireNonNull(department, "Department");
        this.role = requireNonNull(role, "Role");
        this.description = requireNonNull(description, "Description");
        this.accessLevel = requireNonNull(accessLevel, "AccessLevel");
        this.status = requireNonNull(status, "Status");
        this.assignedComplaints = new ArrayList<>(assignedComplaints);
    }

    private static <T> T requireNonNull(T value, String fieldName) {
        if (value == null)
            throw new IllegalArgumentException(fieldName + " cannot be null.");
        return value;
    }

    // ──────────────────────────────────────
    // Métodos para manejar AssignedComplaint entity
    // ──────────────────────────────────────

    public void assignComplaint(ComplaintId complaintId) {
        if (complaintId == null)
            throw new IllegalArgumentException("ComplaintId cannot be null.");

        AssignedComplaints assigned = new AssignedComplaints(complaintId, this);
        assignedComplaints.add(assigned);
    }

    public void unassignComplaint(ComplaintId complaintId) {
        assignedComplaints.removeIf(ac -> ac.getComplaintId().equals(complaintId));
    }

    public List<AssignedComplaints> getAssignedComplaints() {
        return Collections.unmodifiableList(assignedComplaints);
    }

    public void updateProfile(FullName fullName, Email email, PhoneNumber phoneNumber) {
        this.fullName = requireNonNull(fullName, "FullName");
        this.email = requireNonNull(email, "Email");
        this.phoneNumber = requireNonNull(phoneNumber, "PhoneNumber");
    }
}
