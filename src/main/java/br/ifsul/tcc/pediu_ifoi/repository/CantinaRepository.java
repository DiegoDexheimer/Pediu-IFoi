package br.ifsul.tcc.pediu_ifoi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ifsul.tcc.pediu_ifoi.domain.entity.Cantina;

@Repository
public interface CantinaRepository extends JpaRepository<Cantina, Long> {

}
