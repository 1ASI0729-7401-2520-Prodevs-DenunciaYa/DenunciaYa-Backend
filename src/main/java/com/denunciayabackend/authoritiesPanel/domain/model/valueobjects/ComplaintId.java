package com.denunciayabackend.authoritiesPanel.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ComplaintId {

    private Long value;

    protected ComplaintId() {}

    public ComplaintId(Long value) {
        if (value == null || value < 1) {
            throw new IllegalArgumentException("ComplaintId must be a positive number.");
        }
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ComplaintId)) return false;
        ComplaintId that = (ComplaintId) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
