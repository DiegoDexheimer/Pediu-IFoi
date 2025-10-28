package br.ifsul.tcc.pediu_ifoi.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    @Size(min = 3, message = "O nome deve ter no mínimo 3 caracteres")
    protected String nome;

    @Column(name = "preco", nullable = false)
    @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero")
    protected Double preco;

    @Column(name = "disponivel", nullable = false)
    protected boolean isDisponivel;

    @Column(name = "ativo", nullable = false)
    private boolean ativo = true;

    @Column(name = "imagem")
    private String imagem;
}
