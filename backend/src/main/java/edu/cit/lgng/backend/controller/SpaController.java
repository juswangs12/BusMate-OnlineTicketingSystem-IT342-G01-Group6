package edu.cit.lgng.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SpaController {

    // Forward all frontend routes to index.html
    @RequestMapping(value = {
            "/{path:^(?!api$)[^\\.]*}",
            "/**/{path:^(?!api$)[^\\.]*}"
    })

    public String forwardReactRoutes() {
        return "forward:/index.html";
    }
}
