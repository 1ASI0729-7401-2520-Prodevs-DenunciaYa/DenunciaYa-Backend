package com.denunciayabackend.complaintCreation.interfaces;

import com.denunciayabackend.complaintCreation.domain.model.commands.UpdateComplaintCommand;
import com.denunciayabackend.complaintCreation.domain.model.valueobjects.ComplaintPriority;
import org.springframework.stereotype.Component;

@Component
public class UpdateComplaintCommandFromResourceAssembler {

    public UpdateComplaintCommand toCommandFromResource(String complaintId, UpdateComplaintResource resource) {
        return new UpdateComplaintCommand(
                complaintId,
                resource.category() != null ? resource.category() : "",
                resource.department() != null ? resource.department() : "",
                resource.city() != null ? resource.city() : "",
                resource.district() != null ? resource.district() : "",
                resource.location() != null ? resource.location() : "",
                resource.referenceInfo() != null ? resource.referenceInfo() : "",
                resource.description() != null ? resource.description() : "",
                resource.priority() != null ? ComplaintPriority.fromJsonValue(resource.priority()) : null,
                resource.status() != null ? com.denunciayabackend.complaintCreation.domain.model.valueobjects.ComplaintStatus.fromJsonValue(resource.status()) : null,
                resource.updateMessage() != null ? resource.updateMessage() : "",
                resource.assignedTo() != null ? resource.assignedTo() : "",
                resource.responsibleId() != null ? resource.responsibleId() : null
        );
    }
}