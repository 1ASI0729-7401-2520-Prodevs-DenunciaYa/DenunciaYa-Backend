package com.denunciayabackend.community.domain.model.valueobjects;

import com.denunciayabackend.community.domain.model.aggregates.Post;

public record PostContent (String content){
public PostContent{
    if(content==null){
        throw new IllegalArgumentException("PostContent must be a positive non-null value");
    }
}
}
