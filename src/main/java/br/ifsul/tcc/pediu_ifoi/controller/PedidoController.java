package br.ifsul.tcc.pediu_ifoi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import br.ifsul.tcc.pediu_ifoi.domain.dto.PedidoDTO;
import br.ifsul.tcc.pediu_ifoi.service.PedidoService;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping("/criar")
    public void criarPedido(@RequestBody PedidoDTO pedido) {
        System.out.println("Criando pedido: " + pedido);
        pedidoService.salvarPedido(pedido);
    }
}
