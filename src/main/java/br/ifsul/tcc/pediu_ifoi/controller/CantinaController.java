package br.ifsul.tcc.pediu_ifoi.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

import br.ifsul.tcc.pediu_ifoi.domain.entity.Cantina;
import br.ifsul.tcc.pediu_ifoi.service.CantinaService;

@Controller()
public class CantinaController {

    @Autowired
    private CantinaService cantinaService;

    @GetMapping("/cantina/cadastro_cantina")
    public String cadastrarCantina() {
        System.out.println("-> Cadastro de Cantina acessado");
        return "/cantina/cadastro_cantina";
    }

    @PostMapping(value = "/cantina/cadastro_cantina", consumes = "application/x-www-form-urlencoded")
    public String cadastrarCantina(@Valid @ModelAttribute Cantina cantina, BindingResult bindingResult,
            Model model) {
        System.out.println("-> Iniciando cadastro de cantina");

        if (bindingResult.hasErrors()) {
            System.err.println("-> Dados inválidos no cadastro.");
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "/cantina/cadastro_cantina";
        }

        try {
            cantina = cantinaService.salvarCantina(cantina);
            System.out.println(cantina);

            System.out.println("-> Cadastro de Cantina realizado com sucesso");
        } catch (Exception e) {
            System.out.println("-> Erro ao cadastrar Cantina: " + e.getMessage());
            throw new RuntimeException("Erro ao cadastrar Cantina");
        }

        return "redirect:/";
    }

}
