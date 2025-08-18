package br.ifsul.tcc.pediu_ifoi.domain.entity;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class Usuario {

    @Column(name = "login", nullable = false, length = 20)
    protected String login;
    
    @Column(name = "senha", nullable = false, length = 20)
    protected String senha;

    @Column(name = "nome", nullable = false, length = 30)
    protected String nome;
}
