package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.security.Principal;





@Controller
public class HomeController {

    @Autowired
    private UserService userService;

//    @Autowired
//    private RoleRepository roleRepository;

//    @PostConstruct
//
//Note that both @PostConstruct and @PreDestroy annotations are part of Java EE. And since Java EE has been deprecated in Java 9
// and removed in Java 11 we have to add an additional dependency to use these annotations:
//
//<dependency>
//    <groupId>javax.annotation</groupId>
//    <artifactId>javax.annotation-api</artifactId>
//    <version>1.3.2</version>
//</dependency>

//    public void postConstruct(){
//        if (!roleRepository.findAll().iterator().hasNext()) {
//            roleRepository.save(new Role("USER"));
//            roleRepository.save(new Role("ADMIN"));
//        }
//    }

    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/register")
    public String processRegistrationPage(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        model.addAttribute("user", user);
        if (result.hasErrors())
        {
            return "registration";
        }
        else
        {
            userService.saveUser(user);
            model.addAttribute("message", "User Account Created");
        }

        return "index";
    }

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @Autowired
    UserRepository userRepository;

    @RequestMapping("/secure")
    public String secure(Principal principal, Model model) {
        String username = principal.getName();
        model.addAttribute("user", userRepository.findByUsername(username));
        return "secure";
    }

}


