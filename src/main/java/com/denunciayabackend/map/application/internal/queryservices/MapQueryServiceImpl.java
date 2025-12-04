package com.denunciayabackend.map.application.internal.queryservices;

import com.denunciayabackend.map.domain.model.entities.MapComplaint;
import com.denunciayabackend.map.domain.model.queries.GetComplaintsForMapQuery;
import com.denunciayabackend.map.domain.services.IMapQueryService;
import com.denunciayabackend.map.infrastructure.persistence.jpa.repositories.IReadOnlyComplaintRepository;
import com.denunciayabackend.map.interfaces.resources.ComplaintMapResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class MapQueryServiceImpl implements IMapQueryService {

    private final IReadOnlyComplaintRepository complaintRepository;
    private final Random random = new Random();

    private static final double PERU_MIN_LAT = -18.35;
    private static final double PERU_MAX_LAT = -0.03;
    private static final double PERU_MIN_LNG = -81.33;
    private static final double PERU_MAX_LNG = -68.65;

    public MapQueryServiceImpl(IReadOnlyComplaintRepository complaintRepository) {
        this.complaintRepository = complaintRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComplaintMapResource> handle(GetComplaintsForMapQuery query) {
        List<MapComplaint> complaints = complaintRepository.findFilteredComplaints(
                query.category(),
                query.district(),
                query.status()
        );

        return complaints.stream()
                .map(this::toResource)
                .collect(Collectors.toList());
    }

    private ComplaintMapResource toResource(MapComplaint complaint) {
        double lat;
        double lng;

        try {
            if (complaint.getLocation() != null && complaint.getLocation().contains(",")) {
                String[] parts = complaint.getLocation().split(",");
                lat = Double.parseDouble(parts[0].trim());
                lng = Double.parseDouble(parts[1].trim());
            }
            else {
                double[] randomCoords = generateRandomPeruCoordinates();
                lat = randomCoords[0];
                lng = randomCoords[1];
            }
        } catch (Exception e) {
            double[] randomCoords = generateRandomPeruCoordinates();
            lat = randomCoords[0];
            lng = randomCoords[1];
        }

        return new ComplaintMapResource(
                complaint.getId(),
                complaint.getTitle(),
                complaint.getCategory(),
                complaint.getDistrict(),
                complaint.getStatus(),
                lat,
                lng
        );
    }

    private double[] generateRandomPeruCoordinates() {
        double randomLat = PERU_MIN_LAT + (PERU_MAX_LAT - PERU_MIN_LAT) * random.nextDouble();
        double randomLng = PERU_MIN_LNG + (PERU_MAX_LNG - PERU_MIN_LNG) * random.nextDouble();
        return new double[]{randomLat, randomLng};
    }
}