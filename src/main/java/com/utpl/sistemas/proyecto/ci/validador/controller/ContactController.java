package com.utpl.sistemas.proyecto.ci.validador.controller;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.utpl.sistemas.proyecto.ci.validador.ViewConstant;
import com.utpl.sistemas.proyecto.ci.validador.model.ContactModel;
import com.utpl.sistemas.proyecto.ci.validador.service.ContactService;


@Controller
@RequestMapping("/contacts")
public class ContactController {

    private final static Log LOG = LogFactory.getLog(ContactController.class);

    @Autowired
    @Qualifier("contactServiceImpl")
    private ContactService contactService;

    @RequestMapping("/cancel")
    public String Cancel() {
        return "redirect:/contacts/table?result=0";
    }

    @RequestMapping("/addContact")
    public String addContactTable(@Valid @ModelAttribute(name = "contactModel") ContactModel contactModel, BindingResult bindingResult) {
        LOG.info("METHOD: addContact() -- PARAMS: " + contactModel.toString());
        int result = 0;
        if (bindingResult.hasErrors()) {
            return ViewConstant.CONTACT_FORM;
        } else {
            
            if (!validateCI(contactModel.getCi())) {
                result = 6;
            } else if (null != contactService.addContact(contactModel)) {
             // Persona agregada correctamente
                result = 1;
            } else {
                // No se pudo agregar a la persona
                result = 5;
            }
        }

        return "redirect:/contacts/table?result=".concat(result + "");
    }

    @RequestMapping("/contactForm")
    public String redirectContactForm(@RequestParam(name = "id", required = false) int id, Model model) {
        ContactModel contactModel = new ContactModel();
        if (id != 0) {
            contactModel = contactService.findContactByIdModel(id);
        }
        model.addAttribute("contactModel", contactModel);
        LOG.info("METHOD: redirectContactForm() -- PARAMS: " + contactModel.toString());
        return ViewConstant.CONTACT_FORM;
    }

    @GetMapping("/table")
    public ModelAndView table(@RequestParam(name = "result", required = false) Integer result) {
        if (result == null) {
            result = 0;
        }
        return getMavContacts(result, ViewConstant.TABLE);
    }

    @GetMapping("/removeContact")
    public ModelAndView removeContact(@RequestParam(name = "id", required = true) int id) {
        contactService.removeContact(id);
        return table(2);
    }
    
    private ModelAndView getMavContacts(int result, String viewConstant) {
        ModelAndView mav = new ModelAndView(viewConstant);
        mav.addObject("userName", "Administrador");
        mav.addObject("contacts", contactService.listAllContacts());
        mav.addObject("result", result);

        return mav;
    }
    
    
    /**
     * Valida el nuemro de cedula
     * 
     * @param cedula Numero de cedula
     * @return
     */
    public boolean validateCI(String cedula) {
        boolean result = false;
        // verifica que tenga 10 digitos y que contenga solo valores numericos
        if (!((cedula.length() == 10) && cedula.matches("^[0-9]{10}$"))) {
            return false;
        }
        // Verifica que el tercer digito sea �nicamente los que sean menores o iguales a
        // 6
        if ("789".indexOf(cedula.substring(2, 3)) >= 0) {
            return false;
        }
        try {
            int codigoProvincia = Integer.parseInt(cedula.substring(0, 2));
            // se valida que el codigo de provincia este entre 1 a 24
            if ((codigoProvincia >= 1 && codigoProvincia <= ViewConstant.NUMERO_PROVINCIAS)) {

                result = validator(cedula);
            }
            return result;
        } catch (Exception e) {
            LOG.error("Error al validar la cedula.", e);
        }
        return result;
    }

    /**
     * Metodo para validar el numero de cedula
     * 
     * @param cedula Numero de cedula
     * @return
     */
    private boolean validator(String cedula) {
        int suma = 0;
        boolean result;
        int ayuda;
        try {
            int i = 0;
            while (i < cedula.length() - 1) {

                if (i % 2 == 0) {
                    ayuda = Integer.parseInt(cedula.substring(i, i + 1)) * 2;
                    if (ayuda >= 10)
                        ayuda -= 9;
                    suma = suma + ayuda;
                } else {
                    suma = suma + Integer.parseInt(cedula.substring(i, i + 1));
                }
                i++;
            }

            int val = 0;
            for (int j = 0; j < 100; j += 10) {
                if (suma >= j && suma < (j + 10)) {
                    val = j += 10;
                    break;
                }
            }
            if (Integer.parseInt(cedula.substring(9)) != (val - suma) && Integer.parseInt(cedula.substring(9)) != (val - (suma + 10)))
                result = false;
            else
                result = true;
        } catch (NumberFormatException e) {
            result = false;
        }
        return result;
    }
    
}

