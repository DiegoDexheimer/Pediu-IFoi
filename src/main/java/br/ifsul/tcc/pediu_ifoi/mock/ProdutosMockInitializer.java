package br.ifsul.tcc.pediu_ifoi.mock;

import br.ifsul.tcc.pediu_ifoi.domain.entity.Produto;
import br.ifsul.tcc.pediu_ifoi.repository.ProdutoRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ProdutosMockInitializer implements CommandLineRunner {

    private final ProdutoRepository produtoRepository;

    public ProdutosMockInitializer(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Override
    public void run(String... args) {

        if (produtoRepository.count() == 0) {
            Produto produto1 = new Produto();
            produto1.setNome("Produto 1");
            produto1.setPreco(10.0);
            produto1.setDisponivel(true);
            produtoRepository.save(produto1);

            Produto produto2 = new Produto();
            produto2.setNome("Produto 2");
            produto2.setPreco(20.0);
            produto2.setDisponivel(true);
            produtoRepository.save(produto2);
        }
    }
}