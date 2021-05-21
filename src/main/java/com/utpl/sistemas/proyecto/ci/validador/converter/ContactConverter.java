package com.utpl.sistemas.proyecto.ci.validador.converter;

import java.math.BigInteger;

import org.springframework.stereotype.Component;

import com.utpl.sistemas.proyecto.ci.validador.entity.Contact;
import com.utpl.sistemas.proyecto.ci.validador.model.ContactModel;


@Component("contactConverter")
public class ContactConverter {

    public ContactModel convertContact2ContactModel(Contact contact) {
        return ContactModel.builder()
                .id(contact.getId())
                .ci(contact.getCi().toString())
                .firstName(contact.getFirstName())
                .lastName(contact.getLastName())
                .age(contact.getAge())
                .telephone(contact.getTelephone())
                .cellphone(contact.getCellphone())
                .city(contact.getCity())
                .address(contact.getAddress())
                .build();
    }

    public Contact convertContactModel2Contact(ContactModel contactModel) {
        return Contact.builder()
                .id(contactModel.getId())
                .ci(new BigInteger(contactModel.getCi()))
                .firstName(contactModel.getFirstName())
                .lastName(contactModel.getLastName())
                .age(contactModel.getAge())
                .telephone(contactModel.getTelephone())
                .cellphone(contactModel.getCellphone())
                .city(contactModel.getCity())
                .address(contactModel.getAddress())
                .build();
    }

}
