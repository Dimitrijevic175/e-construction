package com.maksim.find_worker.dto;

import java.time.LocalDate;

public class JobPostDto {

    private Long id;
    private String title;
    private String description;
    private LocalDate date_posted;
    private Long client_id; // Reference to User who posted the job

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate_posted() {
        return date_posted;
    }

    public void setDate_posted(LocalDate date_posted) {
        this.date_posted = date_posted;
    }

    public Long getClient_id() {
        return client_id;
    }

    public void setClient_id(Long client_id) {
        this.client_id = client_id;
    }
}
