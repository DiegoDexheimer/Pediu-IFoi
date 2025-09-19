package br.ifsul.tcc.pediu_ifoi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import br.ifsul.tcc.pediu_ifoi.domain.entity.Carrinho;
import br.ifsul.tcc.pediu_ifoi.domain.entity.Produto;
import br.ifsul.tcc.pediu_ifoi.service.ProdutoService;

@Controller
@RequestMapping("/carrinho")
@SessionAttributes("carrinho")
public class CarrinhoController {

    @Autowired
    private ProdutoService produtoService;

    @ModelAttribute("carrinho")
    public Carrinho criarCarrinho() {
        return new Carrinho();
    }

    @PostMapping("/adicionar")
    public String adicionarCarrinho(@ModelAttribute("carrinho") Carrinho carrinho, @RequestParam Long produtoId, @RequestParam(defaultValue = "1") Integer quantidade) {

        Produto produto = produtoService.buscarProdutoPorId(produtoId);

        carrinho.adicionarProduto(produto, quantidade);
        return "redirect:/produtos"; // Redireciona para a página de produtos
    }
}
