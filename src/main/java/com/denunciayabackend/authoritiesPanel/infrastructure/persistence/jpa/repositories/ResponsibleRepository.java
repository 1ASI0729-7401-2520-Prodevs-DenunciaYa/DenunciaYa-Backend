package com.denunciayabackend.authoritiesPanel.infrastructure.persistence.jpa.repositories;

import com.denunciayabackend.authoritiesPanel.domain.model.aggregates.Responsible;
import com.denunciayabackend.authoritiesPanel.domain.model.valueobjects.ResponsibleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResponsibleRepository extends JpaRepository<Responsible, Long> {

    Optional<Responsible> findByResponsibleId(ResponsibleId responsibleId);

    boolean existsByResponsibleId(ResponsibleId responsibleId);

    void deleteByResponsibleId(ResponsibleId responsibleId);
}