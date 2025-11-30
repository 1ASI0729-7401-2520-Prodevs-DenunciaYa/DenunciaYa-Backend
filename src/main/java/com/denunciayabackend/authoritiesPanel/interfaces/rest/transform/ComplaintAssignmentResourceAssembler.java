package com.denunciayabackend.authoritiesPanel.interfaces.rest.transform;

import com.denunciayabackend.authoritiesPanel.domain.model.entities.ComplaintAssignment;
import com.denunciayabackend.authoritiesPanel.interfaces.rest.resources.ComplaintAssignmentResource;

import java.util.List;
import java.util.stream.Collectors;

public class ComplaintAssignmentResourceAssembler {

    public static ComplaintAssignmentResource toResourceFromEntity(ComplaintAssignment assignment) {
        return ComplaintAssignmentResource.fromEntity(assignment);
    }

    public static List<ComplaintAssignmentResource> toResourceListFromEntities(List<ComplaintAssignment> assignments) {
        return assignments.stream()
                .map(ComplaintAssignmentResource::fromEntity)
                .collect(Collectors.toList());
    }
}