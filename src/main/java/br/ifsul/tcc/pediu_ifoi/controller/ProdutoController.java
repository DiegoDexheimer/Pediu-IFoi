package br.ifsul.tcc.pediu_ifoi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import br.ifsul.tcc.pediu_ifoi.service.ProdutoService;
import br.ifsul.tcc.pediu_ifoi.service.ClienteService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/produto")
public class ProdutoController {


    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/{id}")
    public String produtoDetalhe(@PathVariable Long id, Model model, HttpServletRequest request) {
        if (!isAuthenticated(request)) {
            return "redirect:/cliente/login_cliente";
        }
        var produto = produtoService.buscarProdutoPorId(id);
        if (produto == null) {
            return "redirect:/cliente/home_cliente";
        }
        model.addAttribute("produto", produto);
        return "cliente/produto_detalhe";
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
