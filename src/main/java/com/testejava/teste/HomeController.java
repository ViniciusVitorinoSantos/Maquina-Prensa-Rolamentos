package com.testejava.teste;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String inicio() {
        return "redirect:/menu";
    }

    @GetMapping("/menu")
    public String menu() {
        return "menu";
    }
}
