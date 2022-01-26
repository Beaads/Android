package com.beatriz.androidlistafuncionariosbea.ui.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.beatriz.androidlistafuncionariosbea.R;
import com.beatriz.androidlistafuncionariosbea.model.Funcionario;

import java.util.List;

public class ListaFuncionariosAdapter extends RecyclerView.Adapter<ListaFuncionariosAdapter.FuncionarioViewHolder> {
    private final List<Funcionario> funcionarios;
    private final Context context;

    public ListaFuncionariosAdapter(Context context, List<Funcionario> funcionarios){
        this.context = context;
        this.funcionarios = funcionarios;
    }

    @Override
    public ListaFuncionariosAdapter.FuncionarioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context)
                .inflate(R.layout.item_formulario, parent, false);
        return new FuncionarioViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(ListaFuncionariosAdapter.FuncionarioViewHolder holder, int position) {
        Funcionario funcionario = funcionarios.get(position);
        holder.vincula(funcionario);
    }

    @Override
    public int getItemCount() {
        return funcionarios.size();
    }

    class FuncionarioViewHolder extends RecyclerView.ViewHolder {

        private final TextView nome;
        private final TextView setor;
        private final TextView email;

        public FuncionarioViewHolder(View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.item_funcionario_nome);
            setor = itemView.findViewById(R.id.item_funcionario_setor);
            email = itemView.findViewById(R.id.item_funcionario_email);
        }

        public void vincula(Funcionario funcionario){
            preencheCampo(funcionario);
        }

        private void preencheCampo(Funcionario funcionario) {
            nome.setText(funcionario.getNome());
            setor.setText(funcionario.getSetor());
            email.setText(funcionario.getEmail());
        }
    }

    public void adiciona(Funcionario funcionario){
        funcionarios.add(funcionario);
        notifyDataSetChanged();
    }
}
