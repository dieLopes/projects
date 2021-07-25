package com.diego.taskboard.domain;

import java.io.Serializable;

public class Contact implements Serializable {

    private ContactType contactType;
    private String contact;

    public ContactType getContactType() {
        return contactType;
    }

    public void setContactType(ContactType contactType) {
        this.contactType = contactType;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
