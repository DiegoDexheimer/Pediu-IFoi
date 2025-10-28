package br.ifsul.tcc.pediu_ifoi.domain.dto;

import org.springframework.web.multipart.MultipartFile;

public record ProdutoDTO(String nome, Double preco, MultipartFile imagem) {
}
