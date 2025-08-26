package br.ifsul.tcc.pediu_ifoi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ifsul.tcc.pediu_ifoi.domain.dto.CantinaDTO;
import br.ifsul.tcc.pediu_ifoi.domain.entity.Cantina;
import br.ifsul.tcc.pediu_ifoi.repository.CantinaRepository;

@Service
public class CantinaService {

    @Autowired
    private CantinaRepository cantinaRepository;

    public Cantina salvarCantina(Cantina cantina) {
        return cantinaRepository.save(cantina);
    }

    public Cantina login(CantinaDTO cantinaDTO) {
        Cantina cantina = cantinaRepository.findByLogin(cantinaDTO.login());
        if (cantina != null && cantina.getSenha().equals(cantinaDTO.senha())) {
            return cantina;
        }
        throw new RuntimeException("Login ou senha inválidos");
    }

}
