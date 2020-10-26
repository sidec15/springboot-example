package com.sdc.springboot_example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.sdc.springboot_example.Application;
import com.sdc.springboot_example.jms.artemis.embedded.EmbeddedArtemisProducer;
import com.sdc.springboot_example.model.Person;

@Controller
@Profile(Application.PROFILE_CONTROLLER + " & " +  Application.PROFILE_JMS_ARTEMIS_EMBEDDED)
public class PersonController {

    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(PersonController.class);
    
    @Autowired
    private EmbeddedArtemisProducer producer;

   
    @RequestMapping("/form")
    public String getPerson(Model model) {
        
        model.addAttribute("person", new Person());
        
        return "form";
    }
    
    @PostMapping("/submit")
    public String submit(@ModelAttribute Person person, Model model){
        model.addAttribute("person", person);
        LOG.info("First name:" + person.getFirstName());
        LOG.info("Last name:" + person.getLastName());
        producer.send(person);
        //return "redirect:/form";
        return "form";
    }    
    
}