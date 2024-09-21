package com.maksim.find_worker.dto;

public class JobOfferedNotification {

    private String name;
    private String surname;
    private String email;
    private String workerName;
    private String workerSurname;
    private String offerDetails;
    private double startingPrice;
    private Long clientId;

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return "Postovani " + name + " " + surname + "\n"
                + "dobili ste ponudu za posao! U nastavku su podaci ponude: \n"
                + "Izvodjac radova: " + workerName + " " + workerSurname + "\n"
                + "Detalji ponude: " + offerDetails + "\n"
                + "Pocetna cena: " + startingPrice + "\n\n"
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

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public String getWorkerSurname() {
        return workerSurname;
    }

    public void setWorkerSurname(String workerSurname) {
        this.workerSurname = workerSurname;
    }

    public String getOfferDetails() {
        return offerDetails;
    }

    public void setOfferDetails(String offerDetails) {
        this.offerDetails = offerDetails;
    }

    public double getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(double startingPrice) {
        this.startingPrice = startingPrice;
    }
}
