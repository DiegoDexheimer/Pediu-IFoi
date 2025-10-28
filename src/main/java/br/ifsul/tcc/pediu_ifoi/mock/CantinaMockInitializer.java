package br.ifsul.tcc.pediu_ifoi.mock;

import br.ifsul.tcc.pediu_ifoi.domain.entity.Cantina;
import br.ifsul.tcc.pediu_ifoi.repository.CantinaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CantinaMockInitializer implements CommandLineRunner {

    private final CantinaRepository cantinaRepository;

    public CantinaMockInitializer(CantinaRepository cantinaRepository) {
        this.cantinaRepository = cantinaRepository;
    }

    @Override
    public void run(String... args) {

        if (cantinaRepository.count() == 0) {
            Cantina cantina1 = new Cantina();
            cantina1.setNome("Cantina");
            cantina1.setLogin("login");
            cantina1.setSenha("senha");
            cantinaRepository.save(cantina1);
        }

    }
}