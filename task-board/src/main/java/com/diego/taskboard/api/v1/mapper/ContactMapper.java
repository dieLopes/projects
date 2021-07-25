package com.diego.taskboard.api.v1.mapper;

import com.diego.taskboard.api.v1.dto.contact.ContactDTO;
import com.diego.taskboard.domain.Contact;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ContactMapper {

    private static final ModelMapper mapper = new ModelMapper();

    public static List<ContactDTO> entitiesToDTOs (Set<Contact> contacts) {
        return contacts
                .stream()
                .map(contact -> mapper.map(contact, ContactDTO.class))
                .collect(Collectors.toList());
    }
}
