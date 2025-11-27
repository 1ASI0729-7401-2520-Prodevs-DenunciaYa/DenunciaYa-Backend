package com.denunciayabackend.complaintCreation.interfaces;

import com.denunciayabackend.complaintCreation.domain.model.commands.UpdateComplaintCommand;
import com.denunciayabackend.complaintCreation.domain.model.valueobjects.ComplaintPriority;
import org.springframework.stereotype.Component;

@Component
public class UpdateComplaintCommandFromResourceAssembler {

    public UpdateComplaintCommand toCommandFromResource(String complaintId, UpdateComplaintResource resource) {
        return new UpdateComplaintCommand(
                complaintId,
                resource.category(),
                resource.department(),
                resource.city(),
                resource.district(),
                resource.location(),
                resource.referenceInfo(),
                resource.description(),
                ComplaintPriority.fromJsonValue(resource.priority())
        );
    }
}