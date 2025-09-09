package br.ifsul.tcc.pediu_ifoi.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class Cliente{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long idUsuario;

    @Column(name ="nome", nullable = false)
    @Size(min = 3, max = 80, message = "O nome deve ter entre 3 e 80 caracteres")
    private String nome;

    @Column(name ="login", unique = true, nullable = false)
    @Size(min = 5, max = 80, message = "O login deve ter entre 5 e 80 caracteres")
    private String login;

    @Column(name ="senha", nullable = false)
    @Size(min = 5, max = 100, message = "A senha deve ter entre 6 e 100 caracteres")
    private String senha;

    @Column(name = "telefone", unique = true, nullable = true, length = 12)
    private String telefone;

}
