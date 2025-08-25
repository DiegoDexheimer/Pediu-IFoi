package br.ifsul.tcc.pediu_ifoi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import br.ifsul.tcc.pediu_ifoi.domain.entity.Cantina;
import br.ifsul.tcc.pediu_ifoi.service.CantinaService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller()
public class CantinaController {

    @Autowired
    private CantinaService cantinaService;

    @GetMapping("/cantina/cadastro")
    public String cadastraCantina(Cantina cantina) {
        System.out.println(cantina);
        return "/cantina/cadastro";
    }

    @PostMapping("/cantina/cadastro")
    public String postMethodName(@RequestBody Cantina cantina) {
        System.out.println(cantina);

        cantinaService.salvarCantina(cantina);

        return "/cantina/cadastro";
    }
    
    
}
