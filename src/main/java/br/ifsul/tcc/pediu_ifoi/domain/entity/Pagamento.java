package br.ifsul.tcc.pediu_ifoi.domain.entity;

import br.ifsul.tcc.pediu_ifoi.domain.entity.enums.StatusPagamento;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Pagamento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Pedido

    private Float valor;

    @Enumerated(EnumType.STRING)
    private StatusPagamento status;

}
