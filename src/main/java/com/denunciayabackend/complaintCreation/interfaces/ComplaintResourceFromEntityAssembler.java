package com.denunciayabackend.complaintCreation.interfaces;

import com.denunciayabackend.complaintCreation.domain.model.aggregates.Complaint;
import com.denunciayabackend.complaintCreation.domain.model.entities.TimelineItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ComplaintResourceFromEntityAssembler {

    public ComplaintResource toResourceFromEntity(Complaint complaint) {
        if (complaint == null) {
            return null;
        }

        return new ComplaintResource(
                complaint.getComplaintId(),
                complaint.getCategory(),
                complaint.getDepartment(),
                complaint.getCity(),
                complaint.getDistrict(),
                complaint.getLocation(),
                complaint.getReferenceInfo(),
                complaint.getDescription(),
                complaint.getStatus(),
                complaint.getPriority(),
                complaint.getEvidence(),
                complaint.getAssignedTo(),
                complaint.getResponsibleId(),
                complaint.getUpdateMessage(),
                complaint.getUpdateDate(),
                mapTimelineToResources(complaint.getTimeline())
        );
    }

    private List<TimelineItemResource> mapTimelineToResources(List<TimelineItem> timeline) {
        if (timeline == null) {
            return List.of();
        }

        return timeline.stream()
                .map(this::mapTimelineItemToResource)
                .collect(Collectors.toList());
    }

    private TimelineItemResource mapTimelineItemToResource(TimelineItem timelineItem) {
        return new TimelineItemResource(
                timelineItem.getId(),
                timelineItem.getStatus(),
                timelineItem.getDate() != null ? timelineItem.getDate().toString() : null,
                timelineItem.isCompleted(),
                timelineItem.isCurrent(),
                timelineItem.isWaitingDecision(),
                timelineItem.getUpdateMessage()
        );
    }
}