package br.ifsul.tcc.pediu_ifoi.mock;

import br.ifsul.tcc.pediu_ifoi.domain.entity.Produto;
import br.ifsul.tcc.pediu_ifoi.repository.ProdutoRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ProdutosMockInitializer implements CommandLineRunner {

    private final ProdutoRepository produtoRepository;

    public ProdutosMockInitializer(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Override
    public void run(String... args) {

        List<Map<String, Object>> produtos = List.of(
                Map.of("nome", "Coxinha de frango", "preco", 6.00, "imagem", "coxinha"),
                Map.of("nome", "Pão de queijo", "preco", 4.00, "imagem", "pao"),
                Map.of("nome", "Misto quente", "preco", 7.50, "imagem", "misto"),
                Map.of("nome", "Pastel de carne", "preco", 6.50, "imagem", "pastel"),
                Map.of("nome", "Sanduíche natural de frango", "preco", 8.00, "imagem", "sanduiche"),
                Map.of("nome", "Bolo de cenoura com cobertura", "preco", 5.50, "imagem", "bolo"),
                Map.of("nome", "Cookie", "preco", 4.50, "imagem", "cookie"),
                Map.of("nome", "Suco natural de laranja", "preco", 6.00, "imagem", "suco"),
                Map.of("nome", "Refrigerante (lata)", "preco", 5.00, "imagem", "refrigerante"),
                Map.of("nome", "Água mineral", "preco", 3.00, "imagem", "agua"),
                Map.of("nome", "Café expresso", "preco", 4.00, "imagem", "cafe"),
                Map.of("nome", "Capuccino", "preco", 6.50, "imagem", "capuccino"),
                Map.of("nome", "Barra de cereal", "preco", 3.50, "imagem", "barra"),
                Map.of("nome", "Salada de frutas", "preco", 7.00, "imagem", "salada"),
                Map.of("nome", "Marmita tradicional (arroz, feijão, carne, salada)", "preco", 15.00, "imagem",
                        "marmita"));

        if (produtoRepository.count() < 5) {

            for (Map<String, Object> p : produtos) {
                Produto produto = new Produto();
                produto.setNome(p.get("nome").toString());
                produto.setPreco((Double) p.get("preco"));
                produto.setDisponivel(true);
                produto.setImagem(String.format("/images/produtos/%s.webp", p.get("imagem")));
                produtoRepository.save(produto);
            }
        }
    }
}
