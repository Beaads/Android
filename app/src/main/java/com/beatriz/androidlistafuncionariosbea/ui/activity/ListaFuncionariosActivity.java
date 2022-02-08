package com.beatriz.androidlistafuncionariosbea.ui.activity;

import static com.beatriz.androidlistafuncionariosbea.ui.activity.FuncionarioActivityConstantes.CHAVE_FUNCIONARIO;
import static com.beatriz.androidlistafuncionariosbea.ui.activity.FuncionarioActivityConstantes.CODIGO_REQUISICAO_INSERE_FUNCIONARIO;
import static com.beatriz.androidlistafuncionariosbea.ui.activity.FuncionarioActivityConstantes.CODIGO_RESULTADO_FUNCIONARIO_CRIADO;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.beatriz.androidlistafuncionariosbea.R;
import com.beatriz.androidlistafuncionariosbea.model.Funcionario;
import com.beatriz.androidlistafuncionariosbea.retrofit.service.FuncionarioService;
import com.beatriz.androidlistafuncionariosbea.ui.recyclerview.adapter.ListaFuncionariosAdapter;
import com.beatriz.androidlistafuncionariosbea.ui.recyclerview.adapter.OnItemClickListener;
import com.beatriz.androidlistafuncionariosbea.ui.recyclerview.helper.callback.FuncionarioItemTouchHelperCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
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
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ListaFuncionariosActivity extends AppCompatActivity {

    private ListaFuncionariosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_funcionarios);
        getTodosFuncionarios();
        botaoNovoFuncionario();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setContentView(R.layout.activity_lista_funcionarios);
        getTodosFuncionarios();
        botaoNovoFuncionario();
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

    public void getTodosFuncionarios() {
        Observable<List<Funcionario>> observable = getRetrofit().create(FuncionarioService.class).getFuncionarios();
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Funcionario>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(ListaFuncionariosActivity.this, "Erro: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(List<Funcionario> funcionarios) {
                        Toast.makeText(ListaFuncionariosActivity.this, "Sucesso", Toast.LENGTH_SHORT).show();
                        configuraRecyclerView(funcionarios);
                    }
                });
}

    // Botão Novo Funiconario - Cadastro de novo funcionario
    private void botaoNovoFuncionario() {
        FloatingActionButton botaoNovoFuncionario = findViewById(R.id.lista_funcionarios_insere_funcionario);
        botaoNovoFuncionario.setOnClickListener(view -> vaiParaFormularioFuncionarioActivity());
    }

    // Vai para formulário para cadastrar novo funcionário
    private void vaiParaFormularioFuncionarioActivity() {
        Intent iniciaFormularioNota =
                new Intent(ListaFuncionariosActivity.this,
                        FormularioFuncionarioActivity.class);
        startActivityIfNeeded(iniciaFormularioNota, CODIGO_REQUISICAO_INSERE_FUNCIONARIO);
    }

    private void configuraRecyclerView(List<Funcionario> todosFuncionarios) {
        RecyclerView listaFuncionarios = findViewById(R.id.lista_notas_recyclerview);
        configuraAdapter(todosFuncionarios, listaFuncionarios);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new FuncionarioItemTouchHelperCallback(adapter));
        itemTouchHelper.attachToRecyclerView(listaFuncionarios);
    }

    private void configuraAdapter(List<Funcionario> todosFuncionarios, RecyclerView listaFuncionarios) {
        adapter = new ListaFuncionariosAdapter(this, todosFuncionarios);
        listaFuncionarios.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Funcionario funcionario, int posicao) {
                Intent abreFormularioComFuncionario = new Intent(ListaFuncionariosActivity.this, FormularioFuncionarioActivity.class);
                abreFormularioComFuncionario.putExtra(CHAVE_FUNCIONARIO, funcionario);
                abreFormularioComFuncionario.putExtra("posicao", posicao);
                startActivityIfNeeded(abreFormularioComFuncionario, 2);
            }
        });
    }
}

