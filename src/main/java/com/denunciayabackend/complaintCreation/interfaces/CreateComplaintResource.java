package com.denunciayabackend.complaintCreation.interfaces;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record CreateComplaintResource(
        @NotBlank @Schema(description = "Category of the complaint", example = "Public Safety")
        String category,

        @NotBlank @Schema(description = "Department where the complaint is located", example = "Lima")
        String department,

        @NotBlank @Schema(description = "City where the complaint is located", example = "Lima")
        String city,

        @NotBlank @Schema(description = "District where the complaint is located", example = "Miraflores")
        String district,

        @NotBlank @Schema(description = "Specific location of the complaint", example = "Av. Larco 1550, Parque Central")
        String location,

        @Schema(description = "Additional reference information", example = "Frente al centro comercial Larcomar")
        String referenceInfo,

        @NotBlank @Schema(description = "Detailed description of the complaint", example = "Se observan grupos causando disturbios y daños a la propiedad pública.")
        String description,

        @Schema(description = "Priority of the complaint", example = "URGENT")
        String priority,

        @Schema(description = "List of evidence URLs", example = "[\"https://picsum.photos/200/200?random=51\"]")
        List<String> evidence
) { }
