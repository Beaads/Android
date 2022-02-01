package com.beatriz.androidlistafuncionariosbea.model;

import java.io.Serializable;

public class Funcionario implements Serializable {

        private int id;
        private String nome;
        private String idade;
        private String setor;


    public Funcionario(String nome, String idade, String setor) {
        this.nome = nome;
        this.idade = idade;
        this.setor = setor;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public void setSetor(String setor) {
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
