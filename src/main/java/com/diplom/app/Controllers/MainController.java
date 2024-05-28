package com.diplom.app.Controllers;

import com.diplom.app.Service.RefusalService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {

    private final RefusalService refusalService;

    @Autowired
    public MainController(RefusalService refusalService) {
        this.refusalService = refusalService;
    }

    @GetMapping()
    public String renderMainPage(){
        return "main.html";
    }

    @GetMapping("/wire")
    public String renderWirePage(){
        return "wire.html";
    }


    @GetMapping("/tollBar")
    public String renderToollBarPage() {
        return "toollBar.html";
    }


}
