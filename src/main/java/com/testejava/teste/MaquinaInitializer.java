package com.testejava.teste;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MaquinaInitializer implements CommandLineRunner {
    private final MaquinaRepository repository;

    public MaquinaInitializer(MaquinaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        if (!repository.existsByCodigo("PR-001")) {
            repository.save(new Maquina(
                    "Maquina Prensa Rolamentos",
                    "PR-001",
                    "Linha piloto"));
        }
    }
}