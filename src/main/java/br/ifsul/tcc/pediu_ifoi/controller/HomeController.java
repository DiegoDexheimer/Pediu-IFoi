package br.ifsul.tcc.pediu_ifoi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        System.out.println("-> Home acessado");
        return "home";
    }
}
