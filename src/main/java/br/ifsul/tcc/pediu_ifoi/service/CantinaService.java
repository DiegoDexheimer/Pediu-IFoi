package br.ifsul.tcc.pediu_ifoi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ifsul.tcc.pediu_ifoi.domain.dto.CantinaDTO;
import br.ifsul.tcc.pediu_ifoi.domain.entity.Cantina;
import br.ifsul.tcc.pediu_ifoi.repository.CantinaRepository;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CantinaService {

    @Autowired
    private CantinaRepository cantinaRepository;

    private final ConcurrentHashMap<String, Long> tokenStore = new ConcurrentHashMap<>();
    private static final long TOKEN_VALIDITY_MS = 5 * 60 * 1000; // 5 minutos

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

    public String generateToken(Cantina cantina) {
        String token = UUID.randomUUID().toString();
        long expiry = System.currentTimeMillis() + TOKEN_VALIDITY_MS;
        tokenStore.put(token, expiry);
        return token;
    }

    public boolean isTokenValid(String token) {
        Long expiry = tokenStore.get(token);
        if (expiry == null)
            return false;
        if (System.currentTimeMillis() > expiry) {
            tokenStore.remove(token);
            return false;
        }
        return true;
    }

    public void removeToken(String token) {
        tokenStore.remove(token);
    }

}
