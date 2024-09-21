package com.maksim.find_worker.dto;

public class ReviewNotification {

    private String name;
    private String surname;
    private String email;
    private String comment;
    private double rating;
    private String clientName;
    private String clientSurname;
    private Long clientId;
    private Long workerId;

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Long workerId) {
        this.workerId = workerId;
    }

    @Override
    public String toString() {
        return "Postovani " + name + " " + surname + "\n"
                +"Dobili ste receniziju od: " + clientName + " " + clientSurname + "\n"
                +"Komentar: " + comment + "\n"
                + "Ocena: " + rating + "\n\n"
                +"Vas FindWorker.com";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientSurname() {
        return clientSurname;
    }

    public void setClientSurname(String clientSurname) {
        this.clientSurname = clientSurname;
    }
}
