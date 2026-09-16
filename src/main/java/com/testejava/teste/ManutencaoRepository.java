package com.testejava.teste;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ManutencaoRepository extends JpaRepository<DadoManutencao, Long> {
}
