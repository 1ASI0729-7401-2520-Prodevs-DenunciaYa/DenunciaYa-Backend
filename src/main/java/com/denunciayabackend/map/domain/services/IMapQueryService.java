package com.denunciayabackend.map.domain.services;

import com.denunciayabackend.map.domain.model.queries.GetComplaintsForMapQuery;
import com.denunciayabackend.map.interfaces.resources.ComplaintMapResource;

import java.util.List;

public interface IMapQueryService {

    List<ComplaintMapResource> handle(GetComplaintsForMapQuery query);
}