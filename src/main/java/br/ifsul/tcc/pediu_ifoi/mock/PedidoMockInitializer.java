package br.ifsul.tcc.pediu_ifoi.mock;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.ifsul.tcc.pediu_ifoi.domain.entity.Cliente;
import br.ifsul.tcc.pediu_ifoi.domain.entity.ItemPedido;
import br.ifsul.tcc.pediu_ifoi.domain.entity.Pedido;
import br.ifsul.tcc.pediu_ifoi.domain.entity.Produto;
import br.ifsul.tcc.pediu_ifoi.domain.entity.StatusPedido;
import br.ifsul.tcc.pediu_ifoi.repository.PedidoRepository;
import br.ifsul.tcc.pediu_ifoi.repository.ClienteRepository;
import br.ifsul.tcc.pediu_ifoi.repository.ProdutoRepository;

@Component
public class PedidoMockInitializer implements CommandLineRunner {
    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    public PedidoMockInitializer(PedidoRepository pedidoRepository, ClienteRepository clienteRepository,
            ProdutoRepository produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
    }

    @Override
    public void run(String... args) {
        if (pedidoRepository.count() == 0) {
            // Cria e salva um cliente
            Cliente cliente = new Cliente();
            cliente.setNome("Cliente Mock");
            cliente.setLogin("mockuser");
            cliente.setSenha("123456");
            cliente.setTelefone("51999999999");
            cliente = clienteRepository.save(cliente);

            // Cria e salva um produto
            Produto produto = new Produto();
            produto.setNome("Produto Mock");
            produto.setPreco(10.0);
            produto.setDisponivel(true);
            produto = produtoRepository.save(produto);

            Produto produto2 = new Produto();
            produto2.setNome("Produto Mock 2");
            produto2.setPreco(20.0);
            produto2.setDisponivel(true);
            produto2 = produtoRepository.save(produto2);
            // Cria o item do pedido
            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setProduto(produto);
            itemPedido.setQuantidade(2);

            ItemPedido itemPedido2 = new ItemPedido();
            itemPedido2.setProduto(produto2);
            itemPedido2.setQuantidade(1);

            List<ItemPedido> itensPedido = new ArrayList<>();
            itensPedido.add(itemPedido);
            itensPedido.add(itemPedido2);

            // Cria o pedido
            Pedido pedido = new Pedido();
            pedido.setCliente(cliente);
            pedido.setItensPedido(itensPedido);
            pedido.setStatusPedido(StatusPedido.PENDENTE);
            pedido.setDataPedido(new java.sql.Date(System.currentTimeMillis()));
            pedido.setValorTotal(produto.getPreco() * itemPedido.getQuantidade());

            pedidoRepository.save(pedido);
        }
    }

}
