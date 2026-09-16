package com.testejava.teste;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MaquinaRepository extends JpaRepository<Maquina, Long> {
    boolean existsByCodigo(String codigo);
}