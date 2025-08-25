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
@Table(name = "cliente")
public class Cliente{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long idUsuario;

    @Column(name = "login", nullable = false, length = 20)
    protected String login;
    
    @Column(name = "senha", nullable = false, length = 20)
    protected String senha;

    @Column(name = "nome", nullable = false, length = 30)
    protected String nome;

    @Column(name ="email", unique = true, nullable = false)
    private String email;

    //(XX) X-XXXX-XXXX
    @Column(name = "telefone", unique = true, nullable = false, length = 15)
    private String telefone;

}
