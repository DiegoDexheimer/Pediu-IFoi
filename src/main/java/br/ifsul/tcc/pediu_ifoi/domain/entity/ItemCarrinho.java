package br.ifsul.tcc.pediu_ifoi.domain.entity;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemCarrinho {

    private Produto produto;
    private Integer quantidade;

    public Double getSubTotal() {
        return produto.getPreco() * quantidade;
    }
}
