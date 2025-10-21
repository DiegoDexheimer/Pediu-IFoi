package br.ifsul.tcc.pediu_ifoi.controller;

import br.ifsul.tcc.pediu_ifoi.service.ProdutoService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import jakarta.servlet.http.HttpServletRequest;

import br.ifsul.tcc.pediu_ifoi.domain.dto.ClienteLoginDTO;
import br.ifsul.tcc.pediu_ifoi.domain.entity.Cliente;
import br.ifsul.tcc.pediu_ifoi.domain.entity.Produto;
import br.ifsul.tcc.pediu_ifoi.service.ClienteService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/cadastro_cliente")
    public String cadastroCliente() {
        System.out.println("-> Cadastro de Cliente acessado");
        return "cliente/cadastro_cliente";
    }

    @PostMapping(value = "/cadastro_cliente", consumes = "application/x-www-form-urlencoded")
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
            model.addAttribute("alertError", e.getMessage());
            return "cliente/cadastro_cliente";
        }
        // adicionar confirmação de criação bem sucedida e um delay para redirecionar
        // para login
        return "redirect:/cliente/login_cliente";
    }

    @GetMapping("/login_cliente")
    public String loginCliente() {
        System.out.println("-> Acessando tela de login de cliente");
        return "cliente/login_cliente";
    }

    @PostMapping("/login_cliente")
    public String loginCliente(@Valid @ModelAttribute ClienteLoginDTO clienteLoginDTO, BindingResult bindingResult,
            Model model, HttpServletResponse response, HttpServletRequest request) {

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
            Cookie cookie = new Cookie("cliente_token", token);
            cookie.setMaxAge(5 * 60);
            cookie.setPath("/");
            response.addCookie(cookie);

            request.getSession().setAttribute("clienteId", cliente.getIdUsuario());
            System.out.println("-> Login de cliente realizado com sucesso");
            return "redirect:/cliente/home_cliente";

        } catch (Exception e) {
            System.out.println("Erro ao logar cliente: " + e.getMessage());
            model.addAttribute("loginError", "Login ou senha inválidos");
            return "cliente/login_cliente";
        }
    }

    private boolean isAuthenticated(HttpServletRequest request) {
        String token = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("cliente_token".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        return token != null && clienteService.isTokenValid(token);
    }

    @GetMapping("/home_cliente")
    public String homeCliente(HttpServletRequest request, Model model) {
        System.out.println("-> Acessando home do Cliente");

        if (!isAuthenticated(request)) {
            System.out.println("-> Token inválido ou expirado. Redirecionando para login.");
            return "redirect:/cliente/login_cliente";
        }
        List<Produto> produtos = produtoService.listarProdutos();
        model.addAttribute("produtos", produtos);
        return "cliente/home_cliente";
    }

    @ExceptionHandler({ org.springframework.web.bind.MethodArgumentNotValidException.class,
            org.springframework.beans.TypeMismatchException.class })
    public String handleValidationException(Exception ex, Model model) {
        String errorMsg = "Erro ao processar requisição: ";
        if (ex instanceof org.springframework.web.bind.MethodArgumentNotValidException) {
            errorMsg += "Verifique se os dados estão corretos.";
        } else if (ex instanceof org.springframework.beans.TypeMismatchException) {
            errorMsg += "O campo está com tipo inválido.";
        } else {
            errorMsg += "Dados inválidos.";
        }
        model.addAttribute("alertError", errorMsg);
        return "cliente/login_cliente";
    }

}
