/**
 * StreetController.java
 */
package com.sdc.springboot_example.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sdc.springboot_example.model.Strt;
import com.sdc.springboot_example.service.strt.IStrtService;

/**
 * @author Simone.DeCristofaro
 * Oct 14, 2020
 */
@Controller
public class StreetController {

    @Autowired
    private JmsTemplate template;
    
    @Value("${queue2}")
    private String queue;
    
    @Autowired
    private IStrtService strtService;

    @GetMapping("/showStreets")
    public String findStreets(Model model) {

        List<Strt> strts = (List<Strt>) strtService.findByIdno(550049426);

        model.addAttribute("streets", strts);
        
        template.convertAndSend(queue, String.format("> [JMS messages] Showing streets: %s"
                , Arrays.toString(strts.stream().map(Object::toString).toArray(size -> new String[size]))));

        return "showStreets";
    }
    
}
