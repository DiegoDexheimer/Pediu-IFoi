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
            cliente1.setNome("Diego");
            cliente1.setLogin("diego");
            cliente1.setSenha("senha");
            cliente1.setTelefone("549999999999");
            clienteRepository.save(cliente1);

            Cliente cliente2 = new Cliente();
            cliente2.setNome("Eduardo");
            cliente2.setLogin("eduardo");
            cliente2.setSenha("senha");
            cliente2.setTelefone("549888888888");
            clienteRepository.save(cliente2);

            Cliente cliente3 = new Cliente();
            cliente3.setNome("Cliente");
            cliente3.setLogin("login");
            cliente3.setSenha("senha");
            cliente3.setTelefone("549777777777");
            clienteRepository.save(cliente3);
        }
    }
}