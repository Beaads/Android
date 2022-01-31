package com.beatriz.androidlistafuncionariosbea.model;

import java.io.Serializable;

public class Funcionario implements Serializable {

        private final String nome;
        private final String idade;
        private final String setor;


    public Funcionario(String nome, String idade, String setor) {
        this.nome = nome;
        this.idade = idade;
        this.setor = setor;
    }

    public String getNome() {
        return nome;
    }

    public String getIdade() {
        return idade; }

    public String getSetor() {
        return setor;

    }
}
