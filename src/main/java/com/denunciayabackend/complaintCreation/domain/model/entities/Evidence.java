package com.denunciayabackend.complaintCreation.domain.model.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "evidences")
public class Evidence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "complaint_id", nullable = false)
    private String complaintId;

    @Column(nullable = false)
    private String url;

    @Column(name = "upload_date")
    private LocalDateTime uploadDate;

    private String description;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "file_size")
    private Long fileSize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "complaint_id", referencedColumnName = "complaint_id", insertable = false, updatable = false)
    private com.denunciayabackend.complaintCreation.domain.model.aggregates.Complaint complaint;

    public Evidence() {
        this.uploadDate = LocalDateTime.now();
    }

    public Evidence(String complaintId, String url) {
        this.complaintId = complaintId;
        this.url = url;
        this.uploadDate = LocalDateTime.now();
    }

    public Evidence(String complaintId, String url, String description) {
        this(complaintId, url);
        this.description = description;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(String complaintId) {
        this.complaintId = complaintId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public com.denunciayabackend.complaintCreation.domain.model.aggregates.Complaint getComplaint() {
        return complaint;
    }

    public void setComplaint(com.denunciayabackend.complaintCreation.domain.model.aggregates.Complaint complaint) {
        this.complaint = complaint;
    }
}