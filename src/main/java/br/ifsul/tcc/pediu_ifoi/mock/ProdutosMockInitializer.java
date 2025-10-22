package br.ifsul.tcc.pediu_ifoi.mock;

import br.ifsul.tcc.pediu_ifoi.domain.entity.Produto;
import br.ifsul.tcc.pediu_ifoi.repository.ProdutoRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class ProdutosMockInitializer implements CommandLineRunner {

    private final ProdutoRepository produtoRepository;

    public ProdutosMockInitializer(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Override
    public void run(String... args) {

        if (produtoRepository.count() < 5) {
            Random random = new Random();

            for (int i = 0; i < 20; i++) {
                Produto produto = new Produto();
                produto.setNome("Produto " + i);
                double preco = 5 + (20-5) * random.nextDouble();
                produto.setPreco(Math.round(preco * 100.00) / 100.0);
                produto.setDisponivel(true);
                produto.setImagem("/images/produtos/default.png");
                produtoRepository.save(produto);
            }
        }
    }
}
