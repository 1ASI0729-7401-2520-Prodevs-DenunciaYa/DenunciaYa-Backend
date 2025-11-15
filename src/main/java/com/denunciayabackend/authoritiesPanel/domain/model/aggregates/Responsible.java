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

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "responsibleId", column = @Column(name = "responsible_id"))
    })
    private ResponsibleId id;

    @Embedded
    private FullName fullName;

    @Embedded
    private Email email;

    @Embedded
    private PhoneNumber phoneNumber;

    @Embedded
    private Position position;

    @Embedded
    private Department department;

    @Embedded
    private Role role;

    @Embedded
    private AccessLevel accessLevel;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "responsible_assigned_complaints",
            joinColumns = @JoinColumn(name = "responsible_id"))
    private Set<ComplaintId> assignedComplaints = new HashSet<>();


    protected Responsible() { } // JPA requirement

    public Responsible(
            ResponsibleId id,
            FullName fullName,
            Email email,
            PhoneNumber phoneNumber,
            Position position,
            Department department,
            Role role,
            AccessLevel accessLevel
    ) {
        this.id = requireNonNull(id, "ResponsibleId");
        this.fullName = requireNonNull(fullName, "FullName");
        this.email = requireNonNull(email, "Email");
        this.phoneNumber = requireNonNull(phoneNumber, "PhoneNumber");
        this.position = requireNonNull(position, "Position");
        this.department = requireNonNull(department, "Department");
        this.role = requireNonNull(role, "Role");
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
}
