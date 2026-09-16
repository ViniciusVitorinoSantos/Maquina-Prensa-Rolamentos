package com.testejava.teste;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Maquina {
    private final String nome;
    private final List<Peca> pecas = new ArrayList<>();
    private final List<DadoManutencao> dadosManutencao = new ArrayList<>();

    public Maquina(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void adicionarPeca(Peca peca) {
        pecas.add(peca);
    }

    public void registrarManutencao(DadoManutencao dado) {
        dadosManutencao.add(dado);
    }

    public List<Peca> getPecas() {
        return Collections.unmodifiableList(pecas);
    }

    public List<DadoManutencao> getDadosManutencao() {
        return Collections.unmodifiableList(dadosManutencao);
    }

    public int getTotalMinutosParada() {
        return dadosManutencao.stream()
                .mapToInt(DadoManutencao::getMinutosParada)
                .sum();
    }
}
