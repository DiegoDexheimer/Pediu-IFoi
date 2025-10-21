package br.ifsul.tcc.pediu_ifoi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ifsul.tcc.pediu_ifoi.domain.dto.ProdutoDTO;
import br.ifsul.tcc.pediu_ifoi.domain.entity.Produto;
import br.ifsul.tcc.pediu_ifoi.repository.ProdutoRepository;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto cadastrarProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    public List<Produto> listarProdutos() {
        return produtoRepository.findByAtivo(true);
    }

    public Produto buscarProdutoPorId(Long id) {
        return produtoRepository.findById(id).orElse(null);
    }

    public void atualizarProduto(Long id, ProdutoDTO produtoDTO) {
        Produto produto = buscarProdutoPorId(id);
        produto.setNome(produtoDTO.nome());
        produto.setPreco(produtoDTO.preco());

        produtoRepository.save(produto);
    }

    public void removerProduto(Long id) {
        Produto produto = buscarProdutoPorId(id);
        produto.setAtivo(false);
        produtoRepository.save(produto);
    }
}
