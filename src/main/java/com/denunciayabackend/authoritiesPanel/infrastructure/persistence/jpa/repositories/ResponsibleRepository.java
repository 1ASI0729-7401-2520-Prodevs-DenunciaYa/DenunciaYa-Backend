package com.denunciayabackend.authoritiesPanel.infrastructure.persistence.jpa.repositories;

import com.denunciayabackend.authoritiesPanel.domain.model.aggregates.Responsible;
import com.denunciayabackend.authoritiesPanel.domain.model.valueobjects.ResponsibleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface to interact with the database for Responsible aggregates.
 */
@Repository
public interface ResponsibleRepository extends JpaRepository<Responsible, Long> {

    // Buscar por correo electrónico
    Optional<Responsible> findByEmail(String email);

    // Verificar existencia por correo electrónico
    boolean existsByEmail(String email);

    // Verificar existencia por correo distinto al id dado
    boolean existsByEmailAndIdIsNot(String email, ResponsibleId id);
}