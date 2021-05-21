package com.utpl.sistemas.proyecto.ci.validador.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.utpl.sistemas.proyecto.ci.validador.entity.Contact;

@Repository("contactRepository")
public interface ContactReposiroty extends JpaRepository<Contact, Serializable> {

}
