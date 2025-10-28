package br.ifsul.tcc.pediu_ifoi.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.ifsul.tcc.pediu_ifoi.domain.dto.ProdutoDTO;
import br.ifsul.tcc.pediu_ifoi.domain.entity.Produto;
import br.ifsul.tcc.pediu_ifoi.repository.ProdutoRepository;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto cadastrarProduto(Produto produto, MultipartFile imagem) {
        if (imagem != null && !imagem.isEmpty()) {
        try {
            // Validação: aceitar apenas imagens
            if (!imagem.getContentType().startsWith("image/")) {
                throw new IllegalArgumentException("Apenas arquivos de imagem são permitidos.");
            }

            String uploadDir = "src/main/resources/static/images/produtos/";
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            String fileName = UUID.randomUUID().toString() + "_" + produto.getNome() + imagem.getOriginalFilename().substring(imagem.getOriginalFilename().lastIndexOf("."));
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(imagem.getInputStream(), filePath);
            produto.setImagem("/images/produtos/" + fileName); // Caminho relativo para acesso via web
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar imagem: " + e.getMessage());
        }
    }
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
