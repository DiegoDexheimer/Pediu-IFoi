package br.ifsul.tcc.pediu_ifoi.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@ToString
public enum StatusPedido {

    PENDENTE("Pendente"),
    EM_ANDAMENTO("Em andamento"),
    FINALIZADO("Finalizado"),
    CANCELADO("Cancelado"),
    ENVIADO("Enviado");

    @Getter
    @Setter
    private String descricao;

}
