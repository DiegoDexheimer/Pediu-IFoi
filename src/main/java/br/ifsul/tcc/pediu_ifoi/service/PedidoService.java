package br.ifsul.tcc.pediu_ifoi.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ifsul.tcc.pediu_ifoi.domain.dto.PedidoDTO;
import br.ifsul.tcc.pediu_ifoi.domain.entity.Carrinho;
import br.ifsul.tcc.pediu_ifoi.domain.entity.Cliente;
import br.ifsul.tcc.pediu_ifoi.domain.entity.ItemCarrinho;
import br.ifsul.tcc.pediu_ifoi.domain.entity.ItemPedido;
import br.ifsul.tcc.pediu_ifoi.domain.entity.Pedido;
import br.ifsul.tcc.pediu_ifoi.domain.entity.Produto;
import br.ifsul.tcc.pediu_ifoi.domain.entity.enums.StatusPedido;
import br.ifsul.tcc.pediu_ifoi.repository.PedidoRepository;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoService produtoService;

    public void salvarPedido(PedidoDTO pedido) {
        Cliente cliente = pedido.cliente();
        System.out.println("Salvando pedido para o cliente: " + cliente.getNome());

        Carrinho carrinho = pedido.carrinho();
        List<ItemPedido> produtos = new ArrayList<>();
        for (ItemCarrinho item : carrinho.getItens()) {
            Produto produto = item.getProduto();
            Integer quantidade = item.getQuantidade();
            produto = produtoService.buscarProdutoPorId(produto.getId());
            produtos.add(new ItemPedido(null, produto, quantidade));
            System.out.println("Adicionando produto: " + produto.getNome() + " com quantidade: " + quantidade);
        }

        Pedido pedidoEntity = new Pedido();
        pedidoEntity.setCliente(cliente);
        pedidoEntity.setItensPedido(produtos);
        pedidoEntity.setValorTotal(carrinho.getTotal());
        pedidoEntity.setDataPedido(new Date(System.currentTimeMillis()));
        pedidoEntity.setStatusPedido(StatusPedido.PENDENTE);
        pedidoRepository.save(pedidoEntity);
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

    public Pedido atualizarStatus(Long idPedido, StatusPedido novoStatus) {
        Pedido pedido = findById(idPedido);
        pedido.setStatusPedido(novoStatus);
        return pedidoRepository.save(pedido);
    }

    public List<Pedido> listarTodosPedidos() {
        return pedidoRepository.findAll();
    }

    public List<Pedido> buscarPedidosPorCliente(Long clienteId) {
        return pedidoRepository.findByCliente_IdUsuario(clienteId);
    }

}
