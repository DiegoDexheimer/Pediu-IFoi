package br.ifsul.tcc.pediu_ifoi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpServletRequest;

import br.ifsul.tcc.pediu_ifoi.domain.dto.PedidoDTO;
import br.ifsul.tcc.pediu_ifoi.domain.entity.Pedido;
import br.ifsul.tcc.pediu_ifoi.domain.entity.enums.StatusPedido;
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

    @PutMapping("/{id}/status")
    public ResponseEntity<Pedido> atualizarStatus(@PathVariable Long id, @RequestParam StatusPedido status) {
        System.out.println("Atualizando status do pedido ID: #" + id + " para: " + status.getDescricao());
        Pedido pedidoAtualizado = pedidoService.atualizarStatus(id, status);
        return ResponseEntity.ok(pedidoAtualizado);
    }

    @GetMapping("")
    public String historicoPedidosCliente(HttpServletRequest request, Model model) {
        Long clienteId = (Long) request.getSession().getAttribute("clienteId");
        if (clienteId == null) {
            return "redirect:/cliente/login_cliente";
        }
        model.addAttribute("pedidos", pedidoService.buscarPedidosPorCliente(clienteId));
        return "cliente/pedidos";
    }
}
