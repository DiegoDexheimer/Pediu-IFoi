package br.ifsul.tcc.pediu_ifoi.controller;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

import br.ifsul.tcc.pediu_ifoi.domain.dto.CantinaDTO;
import br.ifsul.tcc.pediu_ifoi.domain.dto.ProdutoDTO;
import br.ifsul.tcc.pediu_ifoi.domain.entity.Cantina;
import br.ifsul.tcc.pediu_ifoi.domain.entity.Produto;
import br.ifsul.tcc.pediu_ifoi.service.CantinaService;
import br.ifsul.tcc.pediu_ifoi.service.ProdutoService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CantinaController {

    @Autowired
    private CantinaService cantinaService;

    @Autowired
    private ProdutoService produtoService;

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

    @GetMapping("/cantina/login_cantina")
    public String loginCantina() {
        System.out.println("-> Acessando tela de login de Cantina");
        return "/cantina/login_cantina";
    }

    @PostMapping("/cantina/login_cantina")
    public String loginCantina(@Valid @ModelAttribute CantinaDTO cantinaDTO, BindingResult bindingResult,
            Model model, HttpServletResponse response) {

        System.out.println("-> Iniciando login de cantina");

        if (bindingResult.hasErrors()) {
            System.err.println("-> Dados inválidos no login.");
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "/cantina/login_cantina";
        }

        try {
            Cantina cantina = cantinaService.login(cantinaDTO);
            System.out.println(cantina);

            // Gerar token e salvar no cookie
            String token = cantinaService.generateToken(cantina);
            Cookie cookie = new Cookie("cantina_token", token);
            cookie.setMaxAge(5 * 60); // 5 minutos
            cookie.setPath("/");
            response.addCookie(cookie);

            System.out.println("-> Login de Cantina realizado com sucesso");
            return "redirect:/cantina/home_cantina";
        } catch (Exception e) {
            System.out.println("-> Erro ao realizar login de Cantina: " + e.getMessage());
            model.addAttribute("loginError", "Login ou senha inválidos");
        }

        return "/cantina/login_cantina";
    }

    @GetMapping("/cantina/home_cantina")
    public String homeCantina(HttpServletRequest request) {
        System.out.println("-> Acessando home da Cantina");

        String token = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("cantina_token".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        if (token == null || !cantinaService.isTokenValid(token)) {
            System.out.println("-> Token inválido ou expirado. Redirecionando para login.");
            return "redirect:/cantina/login_cantina";
        }

        return "/cantina/home_cantina";
    }

    @GetMapping("/cantina/cadastrar_produto")
    public String cadastrarProduto() {
        System.out.println("-> Acessando tela de cadastro de produto");
        return "/cantina/cadastrar_produto";
    }

    @PostMapping("/cantina/cadastrar_produto")
    public String cadastrarProduto(@Valid @ModelAttribute ProdutoDTO produtoDTO, BindingResult bindingResult,
            Model model) {

        System.out.println("-> Iniciando cadastro de produto");

        if (bindingResult.hasErrors()) {
            System.err.println("-> Erros de validação encontrados.");
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "/cantina/cadastrar_produto";
        }

        try {
            System.out.println(produtoDTO);
            Produto produto = new Produto(null, produtoDTO.nome(), produtoDTO.preco(), true);
            produto = produtoService.cadastrarProduto(produto);
            return "redirect:/cantina/listar_produtos";

        } catch (Exception e) {
            System.out.println("-> Erro ao cadastrar Cantina: " + e.getMessage());
            throw new RuntimeException("Erro ao cadastrar Cantina");
        }
    }

    @GetMapping("/cantina/listar_produtos")
    public String listarProdutos(Model model) {
        System.out.println("-> Acessando tela de listagem de produtos");

        try {
            System.out.println("-> Buscando produtos");
            List<Produto> produtos = produtoService.listarProdutos();
            System.out.println("-> Produtos encontrados: " + produtos.size());
            model.addAttribute("produtos", produtos);
            return "/cantina/listar_produtos";
        } catch (Exception e) {
            System.out.println("-> Erro ao listar produtos: " + e.getMessage());
            throw new RuntimeException("Erro ao listar produtos");
        }
    }
}
