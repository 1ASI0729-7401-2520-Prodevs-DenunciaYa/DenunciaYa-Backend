package com.denunciayabackend.map.interfaces.controllers;

import com.denunciayabackend.map.domain.model.queries.GetComplaintsForMapQuery;
import com.denunciayabackend.map.domain.services.IMapQueryService;
import com.denunciayabackend.map.interfaces.resources.ComplaintMapResource;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/map/complaints")
@Tag(name = "Map Complaints", description = "API para visualización de denuncias en el mapa")
public class MapComplaintsController {

    private final IMapQueryService mapQueryService;


    public MapComplaintsController(IMapQueryService mapQueryService) {
        this.mapQueryService = mapQueryService;
    }


    @GetMapping
    public ResponseEntity<List<ComplaintMapResource>> getFilteredComplaintsForMap(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String district,
            @RequestParam(required = false) String status
    ) {
        GetComplaintsForMapQuery query = new GetComplaintsForMapQuery(category, district, status);

        List<ComplaintMapResource> resources = mapQueryService.handle(query);

        return ResponseEntity.ok(resources);
    }
}