package com.scm.controllers;

import com.scm.entities.ContactForNonLogin;
import com.scm.forms.ContactFormforNonLogin;
import com.scm.services.ContactForNonLoginn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.scm.entities.User;
import com.scm.forms.UserForm;
import com.scm.helpers.Message;
import com.scm.helpers.MessageType;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class PageController {

    @Autowired
    private UserService userService;

    @Autowired
    private ContactForNonLoginn contactForNonLoginn;
    @GetMapping("/")
    public String index() {

        return "redirect:/home";
    }

    // when we fire this mapping we will redirect to home page
    @RequestMapping("/home")
    public String home(Model model) {
        System.out.println("Home page handler");
        // sending data to view -> what ever we send the data here it will go to the template itself
        model.addAttribute("name", "Substring Technologies");
        model.addAttribute("my portfolio", "Govind Kumar");
        model.addAttribute("githubRepo", "https://github.com/Gk355252/SCM");
        return "home";
    }

    // about route

    @RequestMapping("/about")
    public String aboutPage(Model model) {
        model.addAttribute("isLogin", true);
        System.out.println("About page loading");
        return "about";
    }

    // services

    @RequestMapping("/services")
    public String servicesPage() {
        System.out.println("services page loading");
        return "services";
    }




    // contact page

    @GetMapping("/contact")
    public String contact(Model model, HttpSession session) {
        model.addAttribute("contactForm", new ContactForNonLogin());

        // Add any existing success message to the model
        Message message = (Message) session.getAttribute("message");
        if (message != null) {
            model.addAttribute("message", message);
            session.removeAttribute("message"); // Remove the message after displaying it
        }

        return "contact"; // contact.html template
    }

    // Handle contact form submission
    @PostMapping("/submit-contact")
    public String submitContact(@Valid @ModelAttribute("contactForm") ContactForNonLogin contactForm, BindingResult result, Model model, HttpSession session) {
        if (result.hasErrors()) {
            // If validation fails, return to the contact form
            return "contact";
        }

        // Save the contact information to the database
        contactForNonLoginn.saveContact(contactForm);

        // Add a success message to the session
        Message message = Message.builder()
                .content("Thank you for contacting us! We will get back to you soon.")
                .type(MessageType.green)  // Green for success
                .build();
        session.setAttribute("message", message);

        // Redirect back to the contact page to clear the form and show the message
        return "redirect:/contact";
    }

    // this is showing login page
    @GetMapping("/login")
    public String login() {

        return new String("login");
    }

    // registration page
    // Registration page
    @GetMapping("/register")
    public String register(Model model) {
        // Create a fresh userForm object to send to the view
        model.addAttribute("userForm", new UserForm());
        return "register";  // Thymeleaf template for registration
    }

    // Processing registration
    @PostMapping("/do-register")
    public String processRegister(@Valid @ModelAttribute("userForm") UserForm userForm, BindingResult result, Model model, HttpSession session) {
        System.out.println("Processing registration");

        // If validation errors exist, return to the form with errors
        if (result.hasErrors()) {
            return "register";  // Re-render the form with errors
        }

        // Proceed with saving the user if validation passed
        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setAbout(userForm.getAbout());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setEnabled(false);
        user.setProfilePic("https://path-to-default-profile-pic");

        User savedUser = userService.saveUser(user);
        System.out.println("User saved: " + savedUser.getName());

        // Add a success message to the session
        Message message = Message.builder().content("Registration Successful").type(MessageType.green).build();
        session.setAttribute("message", message);

        // Redirect to the login page upon successful registration
        return "redirect:/login";
    }

    // Controller method to handle form reset
    @GetMapping("/do-reset")
    public String resetForm(HttpSession session) {
        // Remove any existing userForm data from the session
        session.removeAttribute("userForm");

        // Redirect to the fresh registration form with empty fields
        return "redirect:/register";
    }

}
