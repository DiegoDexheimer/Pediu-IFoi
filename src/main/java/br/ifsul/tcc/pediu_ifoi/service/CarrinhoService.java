package br.ifsul.tcc.pediu_ifoi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ifsul.tcc.pediu_ifoi.domain.dto.PedidoDTO;
import br.ifsul.tcc.pediu_ifoi.domain.entity.Carrinho;
import br.ifsul.tcc.pediu_ifoi.domain.entity.Cliente;
import br.ifsul.tcc.pediu_ifoi.domain.entity.Pedido;
import br.ifsul.tcc.pediu_ifoi.domain.entity.enums.StatusPedido;
import br.ifsul.tcc.pediu_ifoi.repository.ClienteRepository;

@Service
public class CarrinhoService {
    
    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ClienteRepository clienteRepository;

    public void finalizarCompra(Carrinho carrinho, Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));
        PedidoDTO pedidoDTO = new PedidoDTO(cliente, carrinho);
        pedidoService.salvarPedido(pedidoDTO);
    }

}
