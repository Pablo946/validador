package com.utpl.sistemas.proyecto.ci.validador.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.utpl.sistemas.proyecto.ci.validador.converter.ContactConverter;
import com.utpl.sistemas.proyecto.ci.validador.entity.Contact;
import com.utpl.sistemas.proyecto.ci.validador.model.ContactModel;
import com.utpl.sistemas.proyecto.ci.validador.repository.ContactReposiroty;
import com.utpl.sistemas.proyecto.ci.validador.service.ContactService;

@Service("contactServiceImpl")
public class ContactServiceImpl implements ContactService {

    @Autowired
    @Qualifier("contactRepository")
    private ContactReposiroty contactRepository;

    @Autowired
    @Qualifier("contactConverter")
    private ContactConverter contactConverter;

    @Override
    public ContactModel addContact(ContactModel contactModel) {
        Contact contact = contactRepository.save(contactConverter.convertContactModel2Contact(contactModel));
        return contactConverter.convertContact2ContactModel(contact);
    }

    @Override
    public List<ContactModel> listAllContacts() {
        List<Contact> contacts = contactRepository.findAll();
        List<ContactModel> contactsModel = new ArrayList<>();

        contacts.stream().forEach(x -> contactsModel.add(contactConverter.convertContact2ContactModel(x)));
        return contactsModel;
    }

    @Override
    public Contact findContactById(int id) {
        Optional<Contact> contact = contactRepository.findById(id);
        if (contact.isPresent()) {
            return contact.get();
        }
        return null;
    }

    public ContactModel findContactByIdModel(int id) {
        return contactConverter.convertContact2ContactModel(findContactById(id));
    }

    @Override
    public void removeContact(int id) {
        Contact contact = findContactById(id);
        if (null != contact) {
            contactRepository.delete(contact);
        }
    }
}
