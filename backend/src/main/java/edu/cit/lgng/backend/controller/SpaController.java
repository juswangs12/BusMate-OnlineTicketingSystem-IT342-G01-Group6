package edu.cit.lgng.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SpaController {

    // Forward all frontend routes to index.html
    @GetMapping(value = { 
        "/", 
        "/dashboard/**", 
        "/admin/**", 
        "/routes/**", 
        "/booking/**", 
        "/profile/**", 
        "/my-bookings/**" 
    })
    public String forwardReactRoutes() {
        return "forward:/index.html";
    }
}
