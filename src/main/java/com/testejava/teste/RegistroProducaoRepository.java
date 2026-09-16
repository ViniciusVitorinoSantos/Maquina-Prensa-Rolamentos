package com.testejava.teste;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistroProducaoRepository extends JpaRepository<RegistroProducao, Long> {
    List<RegistroProducao> findAllByOrderByInicioDesc();
}
