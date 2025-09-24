package br.ifsul.tcc.pediu_ifoi.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ifsul.tcc.pediu_ifoi.domain.dto.PedidoDTO;
import br.ifsul.tcc.pediu_ifoi.domain.entity.Cliente;
import br.ifsul.tcc.pediu_ifoi.domain.entity.ItemPedido;
import br.ifsul.tcc.pediu_ifoi.domain.entity.Pedido;
import br.ifsul.tcc.pediu_ifoi.domain.entity.Produto;
import br.ifsul.tcc.pediu_ifoi.domain.entity.StatusPedido;
import br.ifsul.tcc.pediu_ifoi.repository.PedidoRepository;

@Service
public class PedidoService {
    
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ProdutoService produtoService;

    public void salvarPedido(PedidoDTO pedido) {
        Cliente cliente = clienteService.buscarClientePorId(pedido.idCliente());
        System.out.println("Salvando pedido para o cliente: " + cliente.getNome());

        List<ItemPedido> produtos = new ArrayList<>();
        for (Map.Entry<Long, Integer> entry : pedido.produtos().entrySet()) {
            Long produtoId = entry.getKey();
            Integer quantidade = entry.getValue();
            Produto produto = produtoService.buscarProdutoPorId(produtoId);
            produtos.add(new ItemPedido(null, produto, quantidade));
            System.out.println("Adicionando produto: " + produto.getNome() + " com quantidade: " + quantidade);
        }

        Pedido pedidoEntity = new Pedido();
        pedidoEntity.setCliente(cliente);
        pedidoEntity.setItensPedido(produtos);
        pedidoEntity.setValorTotal(calcularValorTotal(produtos));
        pedidoEntity.setDataPedido(new Date(System.currentTimeMillis()));
        pedidoEntity.setStatusPedido(StatusPedido.PENDENTE);
        pedidoRepository.save(pedidoEntity);
    }

    private Double calcularValorTotal(List<ItemPedido> produtos) {
        Double total = 0.0;
        for (ItemPedido item : produtos) {
            total += item.getProduto().getPreco() * item.getQuantidade();
            System.out.println("Produto: " + item.getProduto().getNome() + ", Quantidade: " + item.getQuantidade() + ", Subtotal: " + (item.getProduto().getPreco() * item.getQuantidade()));
        }
        System.out.println("Valor total do pedido: " + total);
        return total;
    }

    public List<Pedido> listarPedidosPendentes() {
        return pedidoRepository.findByStatusPedido(StatusPedido.PENDENTE);
    }

    public List<Pedido> listarPedidosEmAndamento() {
        return pedidoRepository.findByStatusPedido(StatusPedido.EM_ANDAMENTO);
    }

    public List<Pedido> listarPedidosFinalizados() {
        return pedidoRepository.findByStatusPedido(StatusPedido.FINALIZADO);
    }

    public Pedido findById(Long id) {
        return pedidoRepository.findById(id).orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    }
}
