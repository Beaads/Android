package com.beatriz.androidlistafuncionariosbea.model;

import java.io.Serializable;

public class Funcionario implements Serializable {

        private int id;
        private String nome;
        private String idade;
        private String setor;

    public Funcionario() {
    }

    public Funcionario(String nome, String idade, String setor) {
        this.nome = nome;
        this.idade = idade;
        this.setor = setor;
    }


    public int getId() { return id;}

    public String getNome() {
        return nome;
    }

    public String getIdade() {
        return idade; }

    public String getSetor() {
        return setor;

    }
}
