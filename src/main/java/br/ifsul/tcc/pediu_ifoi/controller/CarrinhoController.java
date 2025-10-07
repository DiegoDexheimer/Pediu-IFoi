package br.ifsul.tcc.pediu_ifoi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import br.ifsul.tcc.pediu_ifoi.domain.entity.Cliente;
import br.ifsul.tcc.pediu_ifoi.domain.entity.Produto;
import br.ifsul.tcc.pediu_ifoi.service.ProdutoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import br.ifsul.tcc.pediu_ifoi.domain.entity.Carrinho;
import br.ifsul.tcc.pediu_ifoi.service.ClienteService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/carrinho")
@SessionAttributes("carrinho")
public class CarrinhoController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ProdutoService produtoService;

    @ModelAttribute("carrinho")
    public Carrinho criarCarrinho() {
        return new Carrinho();
    }

    @GetMapping("")
    public String verCarrinho(@ModelAttribute("carrinho") Carrinho carrinho, Model model, HttpServletRequest request) {
        if (!isAuthenticated(request)) {
            return "redirect:/cliente/login_cliente";
        }
        model.addAttribute("carrinho", carrinho);
        System.out.println(carrinho.getItens().toString());
        return "cliente/carrinho";
    }

    @PostMapping("/adicionar")
    public String adicionarAoCarrinho(@RequestParam Long produtoId,
                                      @RequestParam int quantidade,
                                      @ModelAttribute("carrinho") Carrinho carrinho,
                                      HttpServletRequest request) {
        if (!isAuthenticated(request)) {
            return "redirect:/cliente/login_cliente";
        }
        Produto produto = produtoService.buscarProdutoPorId(produtoId);

        carrinho.adicionarProduto(produto, quantidade);

        return "redirect:/cliente/home_cliente";

    }

    @PostMapping("/remover")
    public String removerItemCarrinho(@RequestParam Long produtoId,
                                      @ModelAttribute("carrinho") Carrinho carrinho,
                                      HttpServletRequest request) {
        if (!isAuthenticated(request)) {
            return "redirect:/cliente/login_cliente";
        }
        Produto produto = produtoService.buscarProdutoPorId(produtoId);

        carrinho.removerProduto(produto);

        return "redirect:/carrinho";
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

    
}
