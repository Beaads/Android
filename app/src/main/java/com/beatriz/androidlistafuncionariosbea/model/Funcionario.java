package com.beatriz.androidlistafuncionariosbea.model;

import java.io.Serializable;

public class Funcionario implements Serializable {

        private final String nome;
        private final String setor;
        private final String email;

    public Funcionario(String nome, String setor, String email) {
        this.nome = nome;
        this.setor = setor;
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public String getSetor() {
        return setor;
    }

    public String getEmail() {
        return email;
    }
}
