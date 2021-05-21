package com.utpl.sistemas.proyecto.ci.validador.entity;


import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name="contact")
public class Contact {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    @Column(name = "ci", unique = true, nullable = false)
    private BigInteger ci;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "age")
    private Integer age;
    @Column(name = "telephone")
    private String telephone;
    @Column(name = "cellphone")
    private String cellphone;
    @Column(name = "city")
    private String city;
    @Column(name = "address")
    private String address;
    
}
