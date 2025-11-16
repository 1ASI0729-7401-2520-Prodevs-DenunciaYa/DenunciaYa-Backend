package com.denunciayabackend.complaintCreation.interfaces;
import com.denunciayabackend.complaintCreation.domain.model.commands.CreateComplaintCommand;
import com.denunciayabackend.complaintCreation.domain.model.valueobjects.ComplaintPriority;
import org.springframework.stereotype.Component;

@Component
public class CreateComplaintCommandFromResourceAssembler {

    public CreateComplaintCommand toCommandFromResource(CreateComplaintResource resource) {
        return new CreateComplaintCommand(
                resource.category(),
                resource.departmentName(),
                resource.provinceName(),
                resource.districtName(),
                resource.location(),
                resource.referenceInfo(),
                resource.description(),
                ComplaintPriority.fromJsonValue(resource.priority())

        );
    }
}