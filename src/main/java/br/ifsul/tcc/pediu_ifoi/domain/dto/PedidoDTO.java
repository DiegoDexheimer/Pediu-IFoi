package br.ifsul.tcc.pediu_ifoi.domain.dto;

import br.ifsul.tcc.pediu_ifoi.domain.entity.Carrinho;
import br.ifsul.tcc.pediu_ifoi.domain.entity.Cliente;

public record PedidoDTO(Cliente cliente, Carrinho carrinho) {

}
