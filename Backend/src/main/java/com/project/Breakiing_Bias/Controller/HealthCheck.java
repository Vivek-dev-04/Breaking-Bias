package com.project.Breakiing_Bias.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class HealthCheck {
    @GetMapping("/health")
    public String greet(){
        System.out.println("/health");
        return "Hello World!!!!";
    }
}
