package com.denunciayabackend.authoritiesPanel.application.internal.commanservices;

import com.denunciayabackend.authoritiesPanel.domain.model.aggregates.Responsible;
import com.denunciayabackend.authoritiesPanel.domain.model.commands.*;
import com.denunciayabackend.authoritiesPanel.domain.model.valueobjects.*;
import com.denunciayabackend.authoritiesPanel.domain.services.ResponsibleCommandService;
import com.denunciayabackend.authoritiesPanel.infrastructure.persistence.jpa.repositories.ResponsibleRepository;
import org.springframework.stereotype.Service;

/**
 * Command Service Implementation for Responsible aggregate.
 */
@Service
public class ResponsibleCommandServiceImpl implements ResponsibleCommandService {

    private final ResponsibleRepository responsibleRepository;

    public ResponsibleCommandServiceImpl(ResponsibleRepository responsibleRepository) {
        this.responsibleRepository = responsibleRepository;
    }

    private String generateBusinessId() {
        return "RESP-" + System.currentTimeMillis(); // Genera un ID de negocio único
    }

    @Override
    public Long handle(CreateResponsibleCommand command) {
        // Generar ID de negocio (String)
        var responsibleId = new ResponsibleId(generateBusinessId());

        var firstName = new FirstName(command.firstName());
        var lastName = new LastName(command.lastName());
        var email = new Email(command.email());
        var phoneNumber = new PhoneNumber(command.phoneNumber());
        var role = new Role(command.role());
        var description = new Description(command.description());
        var position = new Position(command.position() != null ? command.position() : command.role());
        var department = new Department(command.department() != null ? command.department() : "Default Department");
        var accessLevel = AccessLevel.valueOf(command.accessLevel().toUpperCase());

        var responsible = new Responsible(
                responsibleId, // ID de negocio (String)
                firstName,
                lastName,
                email,
                phoneNumber,
                position,
                department,
                role,
                description,
                accessLevel,
                StatusResponsible.ACTIVO
        );

        responsibleRepository.save(responsible);
        return responsible.getId(); // Devuelve el ID de JPA (Long)
    }

    // Delete Responsible
    @Override
    public void handle(DeleteResponsibleCommand command) {
        Long id = Long.valueOf(command.responsibleId()); // Ya es Long
        if (!responsibleRepository.existsById(id)) {
            throw new IllegalArgumentException("Responsible with id %s does not exist".formatted(id));
        }
        responsibleRepository.deleteById(id);
    }

    // Update Responsible Profile
    @Override
    public void handle(UpdateResponsibleProfileCommand command) {
        var responsible = responsibleRepository.findById(Long.valueOf(command.responsibleId())) // Buscar por ID de JPA
                .orElseThrow(() -> new IllegalArgumentException(
                        "Responsible with id %s does not exist".formatted(command.responsibleId())
                ));

        responsible.updateProfile(
                new FirstName(command.firstName()),
                new LastName(command.lastName()),
                new Email(command.email()),
                new PhoneNumber(command.phone()),
                new Role(command.role()),
                new Description(command.description()),
                AccessLevel.valueOf(command.accessLevel()),
                StatusResponsible.valueOf(command.status()),
                new Position(command.position()),
                new Department(command.department())
        );

        responsibleRepository.save(responsible);
    }

    // Assign Complaint
    @Override
    public void handle(AssignComplaintCommand command) {
        var responsible = responsibleRepository.findById(Long.valueOf(command.responsibleId())) // Buscar por ID de JPA
                .orElseThrow(() -> new IllegalArgumentException("Responsible not found"));
        // responsible.assignComplaint(new ComplaintId(command.complaintId()));
        responsibleRepository.save(responsible);
    }

    // Unassign Complaint
    @Override
    public void handle(UnassignComplaintCommand command) {
        var responsible = responsibleRepository.findById(Long.valueOf(command.responsibleId())) // Buscar por ID de JPA
                .orElseThrow(() -> new IllegalArgumentException("Responsible not found"));
        // responsible.unassignComplaint(new ComplaintId(command.complaintId()));
        responsibleRepository.save(responsible);
    }
}