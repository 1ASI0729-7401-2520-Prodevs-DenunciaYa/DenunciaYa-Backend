package com.denunciayabackend.complaintCreation.application.internal.queryservices;

import com.denunciayabackend.complaintCreation.domain.model.aggregates.Complaint;
import com.denunciayabackend.complaintCreation.domain.model.queries.*;
import com.denunciayabackend.complaintCreation.domain.model.valueobjects.ComplaintStatus;
import com.denunciayabackend.complaintCreation.domain.services.ComplaintQueryService;
import com.denunciayabackend.complaintCreation.infrastructure.persistence.jpa.repositories.ComplaintRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ComplaintQueryServiceImpl implements ComplaintQueryService {

    private final ComplaintRepository complaintRepository;

    public ComplaintQueryServiceImpl(ComplaintRepository complaintRepository) {
        this.complaintRepository = complaintRepository;
    }

    @Override
    public List<Complaint> handle(GetAllComplaintsQuery query) {
        return complaintRepository.findAll();
    }

    @Override
    public Optional<Complaint> handle(GetComplaintByIdQuery query) {
        return complaintRepository.findById(query.id());
    }

    @Override
    public Optional<Complaint> handle(GetComplaintByComplaintIdQuery query) {
        return complaintRepository.findByComplaintIdValue(query.complaintId());
    }

    @Override
    public List<Complaint> handle(GetComplaintsByStatusQuery query) {
        return complaintRepository.findByStatus(query.status());
    }


    @Override
    public List<Complaint> handle(GetComplaintsByDepartmentAndCityQuery query) {
        return complaintRepository.findByDepartmentAndCity(query.department(), query.city());
    }

    @Override
    public List<Complaint> findAllComplaints() {
        return complaintRepository.findAll();
    }

    @Override
    public Optional<Complaint> findComplaintById(Long id) {
        return complaintRepository.findById(id);
    }

    @Override
    public Optional<Complaint> findComplaintByComplaintId(String complaintId) {
        return complaintRepository.findByComplaintIdValue(complaintId);
    }

    @Override
    public List<Complaint> findComplaintsByStatus(ComplaintStatus status) {
        return complaintRepository.findByStatus(status);
    }

    @Override
    public List<Complaint> findComplaintsByUserId(String userId) {
        throw new UnsupportedOperationException("Finding complaints by userId is no longer supported");
    }

    @Override
    public List<Complaint> findComplaintsByDepartmentAndCity(String department, String city) {
        return complaintRepository.findByDepartmentAndCity(department, city);
    }

    @Override
    public boolean existsByComplaintId(String complaintId) {
        return complaintRepository.existsByComplaintIdValue(complaintId);
    }
}