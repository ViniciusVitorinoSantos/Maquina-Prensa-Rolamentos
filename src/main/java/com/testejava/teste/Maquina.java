package com.testejava.teste;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "maquinas")
public class Maquina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String codigo;
    private String localizacao;
    private boolean ativa;

    protected Maquina() {
    }

    public Maquina(String nome, String codigo, String localizacao) {
        this.nome = nome;
        this.codigo = codigo;
        this.localizacao = localizacao;
        this.ativa = true;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public boolean isAtiva() {
        return ativa;
    }
}
