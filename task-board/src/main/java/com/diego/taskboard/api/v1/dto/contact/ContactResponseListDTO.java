package com.diego.taskboard.api.v1.dto.contact;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "ContactResponseList")
public class ContactResponseListDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("List of users")
    private List<ContactDTO> contacts = new ArrayList<>();

    public ContactResponseListDTO() { }

    public ContactResponseListDTO(List<ContactDTO> contacts) {
        this.contacts = contacts;
    }

    public List<ContactDTO> getContacts() {
        return contacts;
    }

    public void setContacts(List<ContactDTO> contacts) {
        this.contacts = contacts;
    }
}
