package br.ifsul.tcc.pediu_ifoi.domain.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public enum StatusPagamento {
    PENDENTE("Pendente"),
    APROVADO("Aprovado"),
    REJEITADO("Rejeitado");

    @Getter
    @Setter
    private String descricao;


}