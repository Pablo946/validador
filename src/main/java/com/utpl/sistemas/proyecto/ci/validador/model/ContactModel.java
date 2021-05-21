package com.utpl.sistemas.proyecto.ci.validador.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ContactModel {

    private int id;
    
    @Size(min = 10, max = 10)
    private String ci;
    
    private String firstName;
    
    private String lastName;
    
    @Min(18)
    @Max(150)
    private Integer age;
    
    @Size(min = 0, max = 9)
    private String telephone;
    @Size(min = 0, max = 10)
    private String cellphone;

    private String city;
    private String address;
}
