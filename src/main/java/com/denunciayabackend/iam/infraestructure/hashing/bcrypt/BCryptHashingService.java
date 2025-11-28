package com.denunciayabackend.iam.infraestructure.hashing.bcrypt;
import com.denunciayabackend.iam.application.internal.outboundservices.hashing.HashingService;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface BCryptHashingService extends HashingService, PasswordEncoder {
}
