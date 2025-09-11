package br.ifsul.tcc.pediu_ifoi.domain.dto;

import java.util.HashMap;

public record PedidoDTO(Long idCliente, HashMap<Long, Integer> produtos) {

}
