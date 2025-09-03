package br.ifsul.tcc.pediu_ifoi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ifsul.tcc.pediu_ifoi.domain.entity.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Cliente findByLogin(String login);

    Cliente findByTelefone(String telefone);

}
