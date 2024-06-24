package com.diplom.app.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping()
    public String renderWirePage(){
        return "wire.html";
    }

    @GetMapping("/documentation")
    public String renderDocumentationPage() {
        return "documentation.html";
    }

    @GetMapping("/description")
    public String renderDescriptionPage() {
        return "description.html";
    }


}
