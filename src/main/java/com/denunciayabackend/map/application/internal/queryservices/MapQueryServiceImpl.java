package com.denunciayabackend.map.application.internal.queryservices;

import com.denunciayabackend.map.domain.model.entities.Complaint;
import com.denunciayabackend.map.domain.model.queries.GetComplaintsForMapQuery;
import com.denunciayabackend.map.domain.services.IMapQueryService;
import com.denunciayabackend.map.infrastructure.persistence.jpa.repositories.IReadOnlyComplaintRepository;
import com.denunciayabackend.map.interfaces.resources.ComplaintMapResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MapQueryServiceImpl implements IMapQueryService {

    private final IReadOnlyComplaintRepository complaintRepository;

    public MapQueryServiceImpl(IReadOnlyComplaintRepository complaintRepository) {
        this.complaintRepository = complaintRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComplaintMapResource> handle(GetComplaintsForMapQuery query) {

        List<Complaint> complaints = complaintRepository.findFilteredComplaints(
                query.category(),
                query.district(),
                query.status()
        );

        return complaints.stream()
                .map(this::toResource)
                .collect(Collectors.toList());
    }

    private ComplaintMapResource toResource(Complaint complaint) {
        return new ComplaintMapResource(
                complaint.getId(),
                complaint.getTitle(),
                complaint.getCategory(),
                complaint.getStatus(),
                complaint.getLatitude(),
                complaint.getLongitude()
        );
    }
}