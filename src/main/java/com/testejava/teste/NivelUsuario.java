package com.testejava.teste;

public enum NivelUsuario {
    ADMIN("Admin"),
    MANUTENCAO("Manutencao"),
    OPERADOR("Operador");

    private final String descricao;

    NivelUsuario(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
