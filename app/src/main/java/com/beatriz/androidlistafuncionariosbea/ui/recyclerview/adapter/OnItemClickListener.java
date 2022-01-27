package com.beatriz.androidlistafuncionariosbea.ui.recyclerview.adapter;

import com.beatriz.androidlistafuncionariosbea.model.Funcionario;

public interface OnItemClickListener {
    void onItemClick(Funcionario funcionario, int posicao);
}
