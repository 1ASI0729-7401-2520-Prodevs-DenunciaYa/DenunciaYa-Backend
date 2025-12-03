package com.denunciayabackend.map.domain.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

@Getter
@NoArgsConstructor
@Entity(name = "MapComplaint")
@Table(name = "complaints")
@Immutable
public class MapComplaint {

    @Id
    @Column(name = "complaint_id")
    private String id;

    @Column(name = "description")
    private String title;

    @Column(name = "category")
    private String category;

    @Column(name = "district")
    private String district;

    @Column(name = "status")
    private String status;

    @Column(name = "location")
    private String location;
}