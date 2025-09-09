package br.ifsul.tcc.pediu_ifoi.mock;

import br.ifsul.tcc.pediu_ifoi.domain.entity.Cliente;
import br.ifsul.tcc.pediu_ifoi.repository.ClienteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ClienteMockInitializer implements CommandLineRunner {

    private final ClienteRepository clienteRepository;

    public ClienteMockInitializer(ClienteRepository clienteRepository) {

        
        this.clienteRepository = clienteRepository;
    }

    @Override
    public void run(String... args) {
        if (clienteRepository.count() == 0) {
            Cliente cliente1 = new Cliente();
            cliente1.setNome("teste");
            cliente1.setLogin("teste");
            cliente1.setSenha("teste");
            cliente1.setTelefone("549999999999");
            clienteRepository.save(cliente1);

            Cliente cliente2 = new Cliente();
            cliente2.setNome("teste2");
            cliente2.setLogin("teste2");
            cliente2.setSenha("teste2");
            cliente2.setTelefone("549888888888");
            clienteRepository.save(cliente2);
        }
    }
}