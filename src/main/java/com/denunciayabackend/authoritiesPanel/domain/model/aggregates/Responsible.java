package com.denunciayabackend.authoritiesPanel.domain.model.aggregates;

import com.denunciayabackend.authoritiesPanel.domain.model.valueobjects.*;
import com.denunciayabackend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Responsible{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID interno de JPA

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "responsible_id", unique = true))
    private ResponsibleId responsibleId; // ID de negocio (String)

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "first_name"))
    })
    private FirstName firstName;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "last_name"))
    })
    private LastName lastName;

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
    private StatusResponsible statusResponsible;

    protected Responsible() {}

    public Responsible(
            ResponsibleId responsibleId,
            FirstName firstName,
            LastName lastName,
            Email email,
            PhoneNumber phoneNumber,
            Position position,
            Department department,
            Role role,
            Description description,
            AccessLevel accessLevel,
            StatusResponsible statusResponsible
    ) {
        this.responsibleId = requireNonNull(responsibleId, "ResponsibleId");
        this.firstName = requireNonNull(firstName, "FirstName");
        this.lastName = requireNonNull(lastName, "LastName");
        this.email = requireNonNull(email, "Email");
        this.phoneNumber = requireNonNull(phoneNumber, "PhoneNumber");
        this.position = requireNonNull(position, "Position");
        this.department = requireNonNull(department, "Department");
        this.role = requireNonNull(role, "Role");
        this.description = requireNonNull(description, "Description");
        this.accessLevel = requireNonNull(accessLevel, "AccessLevel");
        this.statusResponsible = requireNonNull(statusResponsible, "Status");
    }

    private static <T> T requireNonNull(T value, String fieldName) {
        if (value == null)
            throw new IllegalArgumentException(fieldName + " cannot be null.");
        return value;
    }

    public void updateProfile(FirstName firstName, LastName lastName, Email email, PhoneNumber phoneNumber,
                              Role role, Description description, AccessLevel accessLevel,
                              StatusResponsible statusResponsible, Position position, Department department) {
        this.firstName = requireNonNull(firstName, "FirstName");
        this.lastName = requireNonNull(lastName, "LastName");
        this.email = requireNonNull(email, "Email");
        this.phoneNumber = requireNonNull(phoneNumber, "PhoneNumber");
        this.role = requireNonNull(role, "Role");
        this.description = requireNonNull(description, "Description");
        this.accessLevel = requireNonNull(accessLevel, "AccessLevel");
        this.statusResponsible = requireNonNull(statusResponsible, "Status");
        this.position = requireNonNull(position, "Position");
        this.department = requireNonNull(department, "Department");
    }

    public String getFullName() {
        return firstName.getValue() + " " + lastName.getValue();
    }

    public String getResponsibleId() {
        return responsibleId.getValue();
    }
}