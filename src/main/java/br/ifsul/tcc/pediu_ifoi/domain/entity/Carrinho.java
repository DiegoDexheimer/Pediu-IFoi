package br.ifsul.tcc.pediu_ifoi.domain.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Carrinho {

    private List<ItemCarrinho> itens = new ArrayList<>();

    public void adicionarProduto(Produto produto, Integer quantidade) {
        for (ItemCarrinho item : itens) {
            if (item.getProduto().equals(produto)) {
                item.setQuantidade(item.getQuantidade() + quantidade);
                return;
            }
        }
        ItemCarrinho novoItem = new ItemCarrinho(produto, quantidade);
        itens.add(novoItem);

    }


    public void removerProduto(Produto produto, Integer quantidade) {
        for (ItemCarrinho item : itens) {
            if (item.getProduto().equals(produto)) {
                item.setQuantidade(item.getQuantidade() - quantidade);
                if (item.getQuantidade() <= 0) {
                    itens.remove(item);
                }
                return;
            }
        }
    }

    public Double getTotal() {
        return itens.stream()
                .mapToDouble(ItemCarrinho::getSubTotal)
                .sum(); 
    }

}
