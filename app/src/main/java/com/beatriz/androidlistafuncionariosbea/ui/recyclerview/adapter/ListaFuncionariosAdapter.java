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
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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

    public void remove(int posicao) {
        deletarFuncionario(funcionarios.get(posicao).getId(), posicao);
        notifyDataSetChanged();
    }

    private static Retrofit getRetrofit() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(FuncionarioService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit;
    }

    public void deletarFuncionario(int idFuncionario, int posicao) {
        Observable<Funcionario> observable2 = (Observable<Funcionario>) getRetrofit().create(FuncionarioService.class).deletaFuncionario(idFuncionario);
        observable2
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Funcionario>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("Erro ao deletar", e.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(Funcionario funcionario) {
                        Log.e("Funcionario deletado", "sucesso");
                        funcionarios.remove(posicao);
                        notifyDataSetChanged();
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
}

