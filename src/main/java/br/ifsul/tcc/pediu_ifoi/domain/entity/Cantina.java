package br.ifsul.tcc.pediu_ifoi.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cantina")
public class Cantina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCantina;

    @Column(name = "login", nullable = false, length = 20)
    protected String login;
    
    @Column(name = "senha", nullable = false, length = 20)
    protected String senha;

    @Column(name = "nome", nullable = false, length = 30)
    protected String nome;

    // Pedidos: <pedido> List

    // Estoque: Estoque
}
