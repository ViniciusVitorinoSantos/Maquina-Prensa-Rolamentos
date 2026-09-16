package com.testejava.teste;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class DadoManutencao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate data;
    private String descricao;
    private int minutosParada;

    protected DadoManutencao() {
        this.data = null;
        this.descricao = null;
        this.minutosParada = 0;
    }

    public DadoManutencao(LocalDate data, String descricao, int minutosParada) {
        if (minutosParada < 0) {
            throw new IllegalArgumentException("O tempo de parada nao pode ser negativo.");
        }

        this.data = data;
        this.descricao = descricao;
        this.minutosParada = minutosParada;
    }

    public LocalDate getData() {
        return data;
    }

    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getMinutosParada() {
        return minutosParada;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setMinutosParada(int minutosParada) {
        if (minutosParada < 0) {
            throw new IllegalArgumentException("O tempo de parada nao pode ser negativo.");
        }
        this.minutosParada = minutosParada;
    }
}
