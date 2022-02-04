package com.beatriz.androidlistafuncionariosbea.ui.recyclerview.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.beatriz.androidlistafuncionariosbea.R;
import com.beatriz.androidlistafuncionariosbea.model.Funcionario;
import com.beatriz.androidlistafuncionariosbea.retrofit.service.FuncionarioService;
import com.beatriz.androidlistafuncionariosbea.ui.activity.FormularioFuncionarioActivity;
import com.beatriz.androidlistafuncionariosbea.ui.activity.ListaFuncionariosActivity;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListaFuncionariosAdapter extends RecyclerView.Adapter<ListaFuncionariosAdapter.FuncionarioViewHolder> {
    private final List<Funcionario> funcionarios;
    private final Context context;
    private OnItemClickListener onItemClickListener;

    public ListaFuncionariosAdapter(Context context, List<Funcionario> funcionarios){
        this.context = context;
        this.funcionarios = funcionarios;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
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

    public void altera(int posicao, Funcionario funcionario) {
        funcionarios.set(posicao, funcionario);
        notifyDataSetChanged();
    }

    public void remove(int posicao) {
        deletarFuncionario(funcionarios.get(posicao).getId(), posicao);
        //funcionarios.remove(posicao);
        notifyDataSetChanged();
    }

    private static Retrofit getRetrofit() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(FuncionarioService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit;
    }

    public void deletarFuncionario(int idFuncionario, int posicao) {
        Call<Funcionario> funcionarioDell = getRetrofit().create(FuncionarioService.class).deletaFuncionario(idFuncionario);
        funcionarioDell.enqueue(new Callback<Funcionario>() {


            @Override
            public void onResponse(Call<Funcionario> call, Response<Funcionario> response) {
                if(response.isSuccessful()) {
                   // Toast.makeText(ListaFuncionariosActivity.this, "salvo com sucesso", Toast.LENGTH_LONG).show();
                    Log.e("Funcionario deletado", "sucesso");
                    funcionarios.remove(posicao);
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Funcionario> call, Throwable t) {
                Log.i("Erro ao deletar", t.getLocalizedMessage());
            }

        });

    }


    class FuncionarioViewHolder extends RecyclerView.ViewHolder {

        private final TextView nome;
        private final TextView idade;
        private final TextView setor;
        private Funcionario funcionario;

        public FuncionarioViewHolder(View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.item_funcionario_nome);
            idade = itemView.findViewById(R.id.item_funcionario_idade);
            setor = itemView.findViewById(R.id.item_funcionario_setor);

            itemView.setOnClickListener((view) -> {
                    onItemClickListener.onItemClick(funcionario, getAdapterPosition());
                });
        }

        public void vincula(Funcionario funcionario){
            this.funcionario = funcionario;
            preencheCampo(funcionario);
        }

        private void preencheCampo(Funcionario funcionario) {
            nome.setText(funcionario.getNome());
            idade.setText(funcionario.getIdade());
            setor.setText(funcionario.getSetor());
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void adiciona(Funcionario funcionario){
        funcionarios.add(funcionario);
        notifyDataSetChanged();
    }
}
