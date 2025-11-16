package com.denunciayabackend.community.application.internal.outboundservices.acl;

import org.springframework.stereotype.Service;

@Service
public class ExternalUserService {



    public boolean userExists(String userId) {

        return userId != null && !userId.isBlank();
    }

    public String getUserNameById(String userId) {
        return "Usuario_" + userId;
    }
}
