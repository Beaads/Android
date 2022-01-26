package com.beatriz.androidlistafuncionariosbea.dao;

import com.beatriz.androidlistafuncionariosbea.model.Funcionario;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FuncionarioDAO{

        private final static ArrayList<Funcionario> funcionarios = new ArrayList<>();

        public List<Funcionario> todos() {
            return (List<Funcionario>) funcionarios.clone();
        }

        public void insere(Funcionario... funcionarios) {
            FuncionarioDAO.funcionarios.addAll(Arrays.asList(funcionarios));
        }

        public void altera(int posicao, Funcionario funcionario) {
            funcionarios.set(posicao, funcionario);
        }

        public void remove(int posicao) {
            funcionarios.remove(posicao);
        }

        public void troca(int posicaoInicio, int posicaoFim) {
            Collections.swap(funcionarios, posicaoInicio, posicaoFim);
        }

        public void removeTodos() {
            funcionarios.clear();
        }
    }

