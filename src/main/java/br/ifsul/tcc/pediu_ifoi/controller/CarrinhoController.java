package br.ifsul.tcc.pediu_ifoi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import br.ifsul.tcc.pediu_ifoi.service.ProdutoService;

@Controller
@RequestMapping("/carrinho")
@SessionAttributes("carrinho")
public class CarrinhoController {

    @Autowired
    private ProdutoService produtoService;

    
}
