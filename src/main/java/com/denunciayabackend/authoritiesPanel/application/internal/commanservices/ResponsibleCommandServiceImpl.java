package com.denunciayabackend.authoritiesPanel.application.internal.commanservices;

import com.denunciayabackend.authoritiesPanel.domain.model.aggregates.Responsible;
import com.denunciayabackend.authoritiesPanel.domain.model.commands.*;
import com.denunciayabackend.authoritiesPanel.domain.model.entities.AssignedComplaints;
import com.denunciayabackend.authoritiesPanel.domain.model.valueobjects.*;
import com.denunciayabackend.authoritiesPanel.domain.services.ResponsibleCommandService;
import com.denunciayabackend.authoritiesPanel.infrastructure.persistence.jpa.repositories.ResponsibleRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Command Service Implementation for Responsible aggregate.
 */
@Service
public class ResponsibleCommandServiceImpl implements ResponsibleCommandService {

    private final ResponsibleRepository responsibleRepository;

    // Para generar IDs simples; puedes reemplazar por un generador UUID o secuencia de DB
    private final AtomicLong idGenerator = new AtomicLong(100L);

    public ResponsibleCommandServiceImpl(ResponsibleRepository responsibleRepository) {
        this.responsibleRepository = responsibleRepository;
    }

    private Long generateNewId() {
        return idGenerator.incrementAndGet();
    }


    @Override
    public Long handle(CreateResponsibleCommand command) {

        var fullName = new FullName(command.firstName(), command.lastName());
        var email = new Email(command.email());
        var phoneNumber = new PhoneNumber(command.phoneNumber());
        var role = new Role(command.role());
        var description = new Description(command.description());
        var accessLevel = AccessLevel.valueOf(command.accessLevel().toUpperCase());

        var responsible = new Responsible(
                new ResponsibleId(generateNewId()),

                fullName,
                email,
                phoneNumber,
                new Position(command.role()),
                new Department("Default Department"),
                role,
                description,
                AccessLevel.TECNICO,
                Status.ACTIVO,
                Collections.emptyList()
        );


        responsibleRepository.save(responsible);
        return responsible.getId();
    }

    // Delete Responsible
    @Override
    public void handle(DeleteResponsibleCommand command) {
        Long id = command.responsibleId();
        if (!responsibleRepository.existsById(id)) {
            throw new IllegalArgumentException("Responsible with id %d does not exist".formatted(id));
        }
        responsibleRepository.deleteById(id);
    }


    // Update Responsible Profile
    @Override
    public void handle(UpdateResponsibleProfileCommand command) {
        var responsible = responsibleRepository.findById(command.responsibleId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Responsible with id %d does not exist".formatted(command.responsibleId())
                ));

        responsible.updateProfile(
                new FullName(command.firstName(), command.lastName()),
                new Email(command.email()),
                new PhoneNumber(command.phoneNumber())
        );

        responsibleRepository.save(responsible);
    }



    // Assign Complaint
    @Override
    public void handle(AssignComplaintCommand command) {
        var responsible = responsibleRepository.findById(command.responsibleId())
                .orElseThrow(() -> new IllegalArgumentException("Responsible not found"));
        responsible.assignComplaint(new ComplaintId(command.complaintId()));
        responsibleRepository.save(responsible);
    }

    // Unassign Complaint
    @Override
    public void handle(UnassignComplaintCommand command) {
        var responsible = responsibleRepository.findById(command.responsibleId())
                .orElseThrow(() -> new IllegalArgumentException("Responsible not found"));
        responsible.unassignComplaint(new ComplaintId(command.complaintId()));
        responsibleRepository.save(responsible);
    }
}
