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
import jakarta.validation.constraints.Size;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cantina")
public class Cantina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCantina;

    @Column(name = "login", nullable = false, length = 20, unique = true)
    @Size(min = 5, message = "O login deve ter no mínimo 5 caracteres")
    protected String login;
    
    @Column(name = "senha", nullable = false, length = 20)
    @Size(min = 5, message = "A senha deve ter no mínimo 5 caracteres")
    protected String senha;

    @Column(name = "nome", nullable = false, length = 30)
    @Size(min = 5, message = "O nome deve ter no mínimo 5 caracteres")
    protected String nome;

    // Pedidos: <pedido> List

    // Estoque: Estoque
}
