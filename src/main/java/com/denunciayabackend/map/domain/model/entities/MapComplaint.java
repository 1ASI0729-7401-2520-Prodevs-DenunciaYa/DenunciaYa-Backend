package com.denunciayabackend.map.domain.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

@Getter
@NoArgsConstructor
@Entity(name = "MapComplaint")
@Immutable

@Subselect(
        "SELECT " +
                "  c.complaint_id as id, " +
                "  c.description as title, " +
                "  c.category as category, " +
                "  c.district as district, " +
                "  c.status as status, " +
                "  c.location as location " +
                "FROM complaints c"
)
public class MapComplaint {

    @Id
    private String id;

    private String title;

    private String category;

    private String district;

    private String status;

    private String location;
}