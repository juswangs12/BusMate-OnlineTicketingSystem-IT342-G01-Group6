package edu.cit.lgng.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;

public class FrontentController {

    // Catch-all for non-API routes
    @RequestMapping(value = {
            "/{path:[^\\.]*}",              // root-level paths
            "/**/{path:^(?!api|static|assets|favicon).*}"  // nested paths except API/static
    })
    public String forward() {
        return "forward:/index.html";
    }
    
}
