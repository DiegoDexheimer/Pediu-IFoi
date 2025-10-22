package br.ifsul.tcc.pediu_ifoi.mock;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.ifsul.tcc.pediu_ifoi.domain.entity.Cliente;
import br.ifsul.tcc.pediu_ifoi.domain.entity.ItemPedido;
import br.ifsul.tcc.pediu_ifoi.domain.entity.Pedido;
import br.ifsul.tcc.pediu_ifoi.domain.entity.Produto;
import br.ifsul.tcc.pediu_ifoi.domain.entity.enums.StatusPedido;
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
            Cliente cliente = clienteRepository.findByLogin("login");
            if (cliente == null) {
                cliente = new Cliente();
                cliente.setNome("Cliente");
                cliente.setLogin("login");
                cliente.setSenha("senha");
                cliente.setTelefone("549777777777");
                cliente = clienteRepository.save(cliente);
            }

            Produto produto1 = new Produto();
            produto1.setNome("Produto Mock");
            produto1.setPreco(10.0);
            produto1.setDisponivel(true);
            produto1.setImagem("/images/produtos/default.png");
            produto1 = produtoRepository.save(produto1);

            Produto produto2 = new Produto();
            produto2.setNome("Produto Mock 2");
            produto2.setPreco(20.0);
            produto2.setDisponivel(true);
            produto2.setImagem("/images/produtos/default.png");
            produto2 = produtoRepository.save(produto2);

            ItemPedido itemPedido1 = new ItemPedido();
            itemPedido1.setProduto(produto1);
            itemPedido1.setQuantidade(2);

            ItemPedido itemPedido2 = new ItemPedido();
            itemPedido2.setProduto(produto2);
            itemPedido2.setQuantidade(1);

            List<ItemPedido> itensPedido1 = new ArrayList<>();
            itensPedido1.add(itemPedido1);
            itensPedido1.add(itemPedido2);

            Pedido pedido1 = new Pedido();
            pedido1.setCliente(cliente);
            pedido1.setItensPedido(itensPedido1);
            pedido1.setStatusPedido(StatusPedido.PENDENTE);
            pedido1.setDataPedido(new java.sql.Date(System.currentTimeMillis()));
            pedido1.setValorTotal(itensPedido1.stream()
                    .mapToDouble(item -> item.getProduto().getPreco() * item.getQuantidade())
                    .sum());
            pedidoRepository.save(pedido1);

            // Pedido 2 - EM_ANDAMENTO
            Cliente cliente2 = new Cliente();
            cliente2.setNome("Cliente Andamento");
            cliente2.setLogin("andamento");
            cliente2.setSenha("654321");
            cliente2.setTelefone("51988888888");
            cliente2 = clienteRepository.save(cliente2);

            Produto produto3 = new Produto();
            produto3.setNome("Produto Andamento");
            produto3.setPreco(15.0);
            produto3.setDisponivel(true);
            produto3.setImagem("/images/produtos/default.png");
            produto3 = produtoRepository.save(produto3);

            ItemPedido itemPedido3 = new ItemPedido();
            itemPedido3.setProduto(produto3);
            itemPedido3.setQuantidade(3);

            List<ItemPedido> itensPedido2 = new ArrayList<>();
            itensPedido2.add(itemPedido3);

            Pedido pedido2 = new Pedido();
            pedido2.setCliente(cliente2);
            pedido2.setItensPedido(itensPedido2);
            pedido2.setStatusPedido(StatusPedido.EM_ANDAMENTO);
            pedido2.setDataPedido(new java.sql.Date(System.currentTimeMillis()));
            pedido2.setValorTotal(itensPedido2.stream()
                    .mapToDouble(item -> item.getProduto().getPreco() * item.getQuantidade())
                    .sum());
            pedidoRepository.save(pedido2);

            // Pedido 3 - FINALIZADO
            Cliente cliente3 = new Cliente();
            cliente3.setNome("Cliente Finalizado");
            cliente3.setLogin("finalizado");
            cliente3.setSenha("abcdef");
            cliente3.setTelefone("51977777777");
            cliente3 = clienteRepository.save(cliente3);

            Produto produto4 = new Produto();
            produto4.setNome("Produto Finalizado");
            produto4.setPreco(25.0);
            produto4.setDisponivel(true);
            produto4.setImagem("/images/produtos/default.png");
            produto4 = produtoRepository.save(produto4);

            ItemPedido itemPedido4 = new ItemPedido();
            itemPedido4.setProduto(produto4);
            itemPedido4.setQuantidade(1);

            List<ItemPedido> itensPedido3 = new ArrayList<>();
            itensPedido3.add(itemPedido4);

            Pedido pedido3 = new Pedido();
            pedido3.setCliente(cliente3);
            pedido3.setItensPedido(itensPedido3);
            pedido3.setStatusPedido(StatusPedido.FINALIZADO);
            pedido3.setDataPedido(new java.sql.Date(System.currentTimeMillis()));
            pedido3.setValorTotal(itensPedido3.stream()
                    .mapToDouble(item -> item.getProduto().getPreco() * item.getQuantidade())
                    .sum());
            pedidoRepository.save(pedido3);
        }
    }

}
