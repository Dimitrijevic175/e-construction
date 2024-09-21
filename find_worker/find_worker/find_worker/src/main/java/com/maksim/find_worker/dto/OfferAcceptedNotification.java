package com.maksim.find_worker.dto;

public class OfferAcceptedNotification {

    private String email;
    private String name;
    private String lastName;
    private Long clientId;
    private String clientName;
    private String clientSurname;
    private String title;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Postovani " + name + " " + lastName + "\n"
                + "Vasa ponuda za posao je PRIHVACENA !!!" + "\n"
                +"Podaci o poslu:" + "\n"
                +"Poslodavac: " + clientName + " " + clientSurname + "\n"
                +"Naslov posla: " + title + "\n"
                +"Opis posla: " + description + "\n\n"
                +"Vas FindWorker.com";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
