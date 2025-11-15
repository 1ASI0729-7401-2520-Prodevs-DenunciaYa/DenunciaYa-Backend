package com.denunciayabackend.authoritiesPanel.domain.model.aggregates;

import com.denunciayabackend.authoritiesPanel.domain.model.valueobjects.*;
import com.denunciayabackend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
public class Responsible extends AuditableAbstractAggregateRoot<Responsible> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "responsible_id"))
    })
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

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "access_level"))
    })
    private AccessLevel accessLevel;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "responsible_assigned_complaints",
            joinColumns = @JoinColumn(name = "responsible_id"))
    private Set<ComplaintId> assignedComplaints = new HashSet<>();

    protected Responsible() { }

    public Responsible(
            ResponsibleId id,
            FullName fullName,
            Email email,
            PhoneNumber phoneNumber,
            Position position,
            Department department,
            Role role,
            Description description,
            AccessLevel accessLevel
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
    }

    private static <T> T requireNonNull(T value, String fieldName) {
        if (value == null)
            throw new IllegalArgumentException(fieldName + " cannot be null.");
        return value;
    }

    public void assignComplaint(ComplaintId complaintId) {
        if (complaintId == null)
            throw new IllegalArgumentException("ComplaintId cannot be null.");

        assignedComplaints.add(complaintId);
    }

    public void unassignComplaint(ComplaintId complaintId) {
        assignedComplaints.remove(complaintId);
    }

    public Set<ComplaintId> getAssignedComplaints() {
        return Collections.unmodifiableSet(assignedComplaints);
    }

    public void updateProfile(FullName fullName, Email email, PhoneNumber phoneNumber) {
        this.fullName = requireNonNull(fullName, "FullName");
        this.email = requireNonNull(email, "Email");
        this.phoneNumber = requireNonNull(phoneNumber, "PhoneNumber");
    }
}
