package com.scm.repsitories;

import com.scm.entities.ContactForNonLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import com.scm.entities.Contact;

public interface ContactfornonLogin extends JpaRepository<ContactForNonLogin, Long> {
}
