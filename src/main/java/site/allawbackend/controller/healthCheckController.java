package site.allawbackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class healthCheckController {
    @GetMapping("/healthCheck")
    public String healthCheck(){
        return "200 OK";
    }
}
