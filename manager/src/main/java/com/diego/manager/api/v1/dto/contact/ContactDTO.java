package com.diego.manager.api.v1.dto.contact;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ContactDTO implements Serializable {

    @ApiModelProperty(value = "User name", required = true)
    private String contact;
    @ApiModelProperty(value = "User name", required = true)
    private String contactType;

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    public static class Builder {

        private final ContactDTO contactDTO;

        private Builder() {
            contactDTO = new ContactDTO();
        }

        public static Builder of () {
            return new Builder();
        }

        public Builder contact (String contact) {
            contactDTO.contact = contact;
            return this;
        }

        public Builder address (String contactType) {
            contactDTO.contactType = contactType;
            return this;
        }

        public ContactDTO build () {
            return contactDTO;
        }
    }
}
