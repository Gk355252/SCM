package com.scm.services;

import com.scm.entities.ContactForNonLogin;
import com.scm.forms.ContactFormforNonLogin;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.scm.repsitories.ContactfornonLogin;
@Service
public class ContactForNonLoginn {

    @Autowired
   private ContactfornonLogin contactfornonLogin;
    public void saveContact(@Valid ContactForNonLogin contact) {
        contactfornonLogin.save(contact);
    }
}
