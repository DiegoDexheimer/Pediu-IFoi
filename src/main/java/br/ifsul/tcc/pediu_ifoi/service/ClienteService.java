package br.ifsul.tcc.pediu_ifoi.service;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ifsul.tcc.pediu_ifoi.domain.dto.ClienteLoginDTO;
import br.ifsul.tcc.pediu_ifoi.domain.entity.Cliente;
import br.ifsul.tcc.pediu_ifoi.repository.ClienteRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    private final ConcurrentHashMap<String, Long> tokenStore = new ConcurrentHashMap<>();
    private final static long TOKEN_VALIDITY_MS = 5 * 60 * 1000;

    public Cliente saveCliente(Cliente cliente) {

        Cliente existentePorLogin = clienteRepository.findByLogin(cliente.getLogin());
        if (existentePorLogin != null) {
            throw new RuntimeException("Já existe um cliente com este login.");
        }
        
        if (cliente.getTelefone() != null && !cliente.getTelefone().isEmpty()) {
            Cliente existentePorTelefone = clienteRepository.findByTelefone(cliente.getTelefone());
            if (existentePorTelefone != null) {
                throw new RuntimeException("Já existe um cliente com este telefone.");
            }
        }
        return clienteRepository.save(cliente);
    }

    public Cliente loginCliente(ClienteLoginDTO clienteLoginDTO) {

        Cliente cliente = clienteRepository.findByLogin(clienteLoginDTO.login());

        if (cliente != null && cliente.getSenha().equals(clienteLoginDTO.senha())) {
            return cliente;
        } else {
            throw new RuntimeException("Login ou senha incorretos");
        }
    }

    public String generateToken(Cliente cliente) {
        String token = UUID.randomUUID().toString();
        long expiry = System.currentTimeMillis() + TOKEN_VALIDITY_MS;
        tokenStore.put(token, expiry);

        return token;
    }
    
    public boolean isTokenValid(String token) {
        Long expiry = tokenStore.get(token);
        if (expiry == null) {
            return false;
        }
        if (System.currentTimeMillis() > expiry) {
            tokenStore.remove(token);
            return false;
        }
        System.out.println("-> Token válido " + token);
        return true;
    }

    public void removeToken(String token) {
        tokenStore.remove(token);
    }
}
