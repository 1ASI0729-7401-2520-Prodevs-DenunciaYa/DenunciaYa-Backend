package com.denunciayabackend.profiles.domain.model.aggregates;


import com.denunciayabackend.profiles.domain.model.commands.CreateProfileCommand;
import com.denunciayabackend.profiles.domain.model.valueobjects.EmailAddress;
import com.denunciayabackend.profiles.domain.model.valueobjects.PersonName;
import com.denunciayabackend.profiles.domain.model.valueobjects.PhoneNumber;
import com.denunciayabackend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * Profile Aggregate Root
 */
@Entity
public class Profile extends AuditableAbstractAggregateRoot<Profile> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private PersonName name;

    @Embedded
    @AttributeOverride(name = "address", column = @Column(name = "email_address", nullable = false, unique = true))
    private EmailAddress emailAddress;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "phone_numberr"))
    private PhoneNumber phoneNumber;

    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    /**
     * Default constructor
     */
    public Profile() {}

    /**
     * Constructor with fields
     */
    public Profile(String firstName, String lastName, String email,
                   LocalDate birthDate, String phoneNumber, Long userId) {
        this.name = new PersonName(firstName, lastName);
        this.emailAddress = new EmailAddress(email);
        this.birthDate = birthDate;
        this.phoneNumber = new PhoneNumber(phoneNumber);
        this.userId = userId;
    }

    /**
     * Constructor with CreateProfileCommand
     */
    public Profile(CreateProfileCommand command) {
        this.name = new PersonName(command.firstName(), command.lastName());
        this.emailAddress = new EmailAddress(command.email());
        this.birthDate = command.birthDate();
        this.phoneNumber = new PhoneNumber(command.phoneNumber());
        this.userId = command.userId();
    }

    public String getFullName() {
        return name.getFullName();
    }

    public String getEmailAddress() {
        return emailAddress.address();
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber.value();
    }

    public Long getUserId() {
        return userId;
    }

    public Long getId() {
        return id;
    }

    public void updateName(String firstName, String lastName) {
        this.name = new PersonName(firstName, lastName);
    }

    public void updateEmailAddress(String email) {
        this.emailAddress = new EmailAddress(email);
    }

    public void updatePhoneNumber(String number) {
        this.phoneNumber = new PhoneNumber(number);
    }

    public void updateBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
