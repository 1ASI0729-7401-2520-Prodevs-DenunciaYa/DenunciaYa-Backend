package com.denunciayabackend.complaintCreation.domain.model.queries;

public record GetComplaintsByDepartmentAndCityQuery(
        String department,
        String city
) { }