package br.ifsul.tcc.pediu_ifoi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import br.ifsul.tcc.pediu_ifoi.domain.dto.ClienteLoginDTO;
import br.ifsul.tcc.pediu_ifoi.domain.entity.Cliente;
import br.ifsul.tcc.pediu_ifoi.service.ClienteService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/cliente/cadastro_cliente")
    public String cadastroCliente() {
        System.out.println("-> Cadastro de Cliente acessado");
        return "cliente/cadastro_cliente";
    }

    @PostMapping(value = "/cliente/cadastro_cliente", consumes = "application/x-www-form-urlencoded")
    public String cadastrarCliente(@Valid @ModelAttribute Cliente cliente, BindingResult bindingResult, Model model) {
        System.out.println("-> Iniciando cadastro de cliente");

        if (bindingResult.hasErrors()) {
            System.err.println("-> Dados inválidos no cadastro");
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "cliente/cadastro_cliente";
        }

        try {
            cliente = clienteService.saveCliente(cliente);
            System.out.println(cliente);
            System.out.println("-> Cliente cadastrado com sucesso");
        } catch (Exception e) {
            System.out.println("Erro ao salvar cliente: " + e.getMessage());
            throw new RuntimeException("Erro ao cadastrar cliente");
        }
        //adicionar confirmação de criação bem sucedida e um delay para redirecionar para login
        return "redirect:/cliente/login_cliente";
    }

    @GetMapping("/cliente/login_cliente")
    public String loginCliente() {
        System.out.println("-> Acessando tela de login de cliente");
        return "cliente/login_cliente";
    }

    @PostMapping("/cliente/login_cliente")
    public String loginCliente(@Valid @ModelAttribute ClienteLoginDTO clienteLoginDTO, BindingResult bindingResult,
            Model model, HttpServletResponse response) {

        System.out.println("-> Iniciando login de cliente");

        if (bindingResult.hasErrors()) {
            System.err.println("-> Dados inválidos no login");
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "cliente/login_cliente";
        }

        try {
            Cliente cliente = clienteService.loginCliente(clienteLoginDTO);
            System.out.println(cliente);

            String token = clienteService.generateToken(cliente);
            Cookie cookie = new Cookie("token", token);
            cookie.setMaxAge(60 * 5);
            cookie.setPath("/");
            response.addCookie(cookie);

            System.out.println("-> Login de cliente realizado com sucesso");
            return "redirect:/cliente/home_cliente";

        } catch (Exception e) {
            System.out.println("Erro ao logar cliente: " + e.getMessage());
            model.addAttribute("loginError", "Login ou senha inválidos");
            return "cliente/login_cliente";
        }
    }

}
