package com.denunciayabackend.complaintCreation.domain.services;

import com.denunciayabackend.complaintCreation.domain.model.aggregates.Complaint;
import com.denunciayabackend.complaintCreation.domain.model.commands.*;

public interface ComplaintCommandService {

    Complaint handle(CreateComplaintCommand command);
    Complaint handle(UpdateComplaintCommand command);
    Complaint handle(UpdateComplaintStatusCommand command);
    Complaint handle(AssignComplaintCommand command);
    void handle(DeleteComplaintCommand command);
    void addEvidence(String complaintId, String evidenceUrl);
    void updatePriority(String complaintId, String priority);
    void assignComplaint(String complaintId, String assignedTo, String responsibleId);
    Complaint handle(com.denunciayabackend.complaintCreation.domain.model.commands.UpdateTimelineItemCommand command);
    Complaint handle(com.denunciayabackend.complaintCreation.domain.model.commands.AdvanceTimelineCommand command);
    Complaint handle(com.denunciayabackend.complaintCreation.domain.model.commands.AcceptDecisionCommand command);
    Complaint handle(com.denunciayabackend.complaintCreation.domain.model.commands.RejectDecisionCommand command);
    Complaint handle(UpdateTimelineItemStatusCommand command);

}