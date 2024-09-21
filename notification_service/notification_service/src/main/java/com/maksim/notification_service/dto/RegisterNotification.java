package com.maksim.notification_service.dto;

import java.io.Serializable;


public class RegisterNotification implements Serializable {
    private String email;
    private String name;
    private String surname;
    private String link;
    private Long receiverId;

    @Override
    public String toString(){
        return "Postovani "+name+" "+surname+",\n\n"+
                "Uspesno ste se registrovali na nas sajt. Molimo Vas da aktivirate Vas nalog klikom na sledeci link:\n"+
                link+"\n\n"+
                "Ukoliko niste Vi pokusali da se registrujete, molimo Vas da zanemarite ovaj mail.\n\n"+
                "Srdacan pozdrav,\n"+
                "Vas FindWorker.com";
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }
}
