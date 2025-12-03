package com.denunciayabackend.complaintCreation.domain.services;

import com.denunciayabackend.complaintCreation.domain.model.aggregates.Complaint;
import com.denunciayabackend.complaintCreation.domain.model.queries.*;
import com.denunciayabackend.complaintCreation.domain.model.valueobjects.ComplaintStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface ComplaintQueryService {
    List<Complaint> handle(GetAllComplaintsQuery query);
    Optional<Complaint> handle(GetComplaintByIdQuery query);
    Optional<Complaint> handle(GetComplaintByComplaintIdQuery query);
    List<Complaint> handle(GetComplaintsByStatusQuery query);
    List<Complaint> handle(GetComplaintsByDepartmentAndCityQuery query);

    List<Complaint> findAllComplaints();
    Optional<Complaint> findComplaintById(Long id);
    Optional<Complaint> findComplaintByComplaintId(String complaintId);
    List<Complaint> findComplaintsByStatus(ComplaintStatus status);
    List<Complaint> findComplaintsByUserId(String userId);
    List<Complaint> findComplaintsByDepartmentAndCity(String department, String city);
    boolean existsByComplaintId(String complaintId);
}