package br.ifsul.tcc.pediu_ifoi.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Cliente extends Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long idUsuario;

    @Column(name ="email", unique = true, nullable = false)
    private String email;

    //(XX) X-XXXX-XXXX
    @Column(name = "telefone", unique = true, nullable = false, length = 15)
    private String telefone;

}
