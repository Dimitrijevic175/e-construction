package com.maksim.find_worker.dto;

import java.time.LocalDate;

public class JobOfferIn {
    private Long job_post_id; // Reference to JobPosting ID
    private Long worker_id; // Reference to Worker ID (majstor)
    private LocalDate date_offered;
    private String offer_details;
    private String name;
    private String last_name;
    private String profession;
    private String email;
    private double starting_price;

    public Long getJob_post_id() {
        return job_post_id;
    }

    public void setJob_post_id(Long job_post_id) {
        this.job_post_id = job_post_id;
    }

    public Long getWorker_id() {
        return worker_id;
    }

    public void setWorker_id(Long worker_id) {
        this.worker_id = worker_id;
    }

    public LocalDate getDate_offered() {
        return date_offered;
    }

    public void setDate_offered(LocalDate date_offered) {
        this.date_offered = date_offered;
    }

    public String getOffer_details() {
        return offer_details;
    }

    public void setOffer_details(String offer_details) {
        this.offer_details = offer_details;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getStarting_price() {
        return starting_price;
    }

    public void setStarting_price(double starting_price) {
        this.starting_price = starting_price;
    }
}
