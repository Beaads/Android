package com.beatriz.androidlistafuncionariosbea.ui.activity;

import static com.beatriz.androidlistafuncionariosbea.ui.activity.FuncionarioActivityConstantes.CHAVE_FUNCIONARIO;
import static com.beatriz.androidlistafuncionariosbea.ui.activity.FuncionarioActivityConstantes.CODIGO_REQUISICAO_INSERE_FUNCIONARIO;
import static com.beatriz.androidlistafuncionariosbea.ui.activity.FuncionarioActivityConstantes.CODIGO_RESULTADO_FUNCIONARIO_CRIADO;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.beatriz.androidlistafuncionariosbea.R;
import com.beatriz.androidlistafuncionariosbea.dao.FuncionarioDAO;
import com.beatriz.androidlistafuncionariosbea.model.Funcionario;
import com.beatriz.androidlistafuncionariosbea.ui.recyclerview.adapter.ListaFuncionariosAdapter;
import com.beatriz.androidlistafuncionariosbea.ui.recyclerview.adapter.OnItemClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ListaFuncionariosActivity extends AppCompatActivity {

    private ListaFuncionariosAdapter adapter;
    public static final String TITULO_APPBAR = "Lista de funcionarios";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_funcionarios);

        List<Funcionario> todosFuncionarios = pegaTodosFuncionarios();
        configuraRecyclerView(todosFuncionarios);
        configuraNovoFuncionario();
    }


    private void configuraNovoFuncionario() {
        FloatingActionButton botaoNovoAluno = findViewById(R.id.lista_funcionarios_insere_funcionario);
        botaoNovoAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vaiParaFormularioNotaActivity();
            }
        });
    }

    private void vaiParaFormularioNotaActivity() {
        Intent iniciaFormularioNota =
                new Intent(ListaFuncionariosActivity.this,
                        FormularioFuncionarioActivity.class);
        startActivityForResult(iniciaFormularioNota,
                CODIGO_REQUISICAO_INSERE_FUNCIONARIO);
    }

    private List<Funcionario> pegaTodosFuncionarios() {
        FuncionarioDAO dao = new FuncionarioDAO();
        return dao.todos();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(ehResultadoComNota(requestCode, resultCode, data)){
            Funcionario funcionarioRecebido = (Funcionario) data.getSerializableExtra(CHAVE_FUNCIONARIO);
            adiciona(funcionarioRecebido);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void adiciona(Funcionario funcionario) {
        new FuncionarioDAO().insere(funcionario);
        adapter.adiciona(funcionario);
    }

    private boolean ehResultadoComNota(int requestCode, int resultCode, Intent data) {
        return ehCodigoRequisicaoInsereFuncionario(requestCode) &&
                ehCodigoResultadoFuncionarioCriado(resultCode) &&
                temNota(data);
    }

    private boolean temNota(Intent data) {
        return data.hasExtra(CHAVE_FUNCIONARIO);
    }

    private boolean ehCodigoResultadoFuncionarioCriado(int resultCode) {
        return resultCode == CODIGO_RESULTADO_FUNCIONARIO_CRIADO;
    }

    private boolean ehCodigoRequisicaoInsereFuncionario(int requestCode) {
        return requestCode == CODIGO_REQUISICAO_INSERE_FUNCIONARIO;
    }

    private void configuraRecyclerView(List<Funcionario> todosFuncionarios) {
        RecyclerView listaFuncionarios = findViewById(R.id.lista_notas_recyclerview);
        configuraAdapter(todosFuncionarios, listaFuncionarios);
    }

    private void configuraAdapter(List<Funcionario> todosFuncionarios, RecyclerView listaFuncionarios) {
        adapter = new ListaFuncionariosAdapter(this, todosFuncionarios);
        listaFuncionarios.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick() {
                Toast.makeText(ListaFuncionariosActivity.this, "viewholder activity", Toast.LENGTH_SHORT).show();
            }
        });
    }


//
//    private void abreFormularioModoEditaAluno(Funcionario funcionario) {
//        Intent vaiParaFormularioActivity = new Intent(ListaFuncionariosActivity.this, FormularioFuncionarioActivity.class);
//        vaiParaFormularioActivity.putExtra(CHAVE_FUNCIONARIO, funcionario);
//        startActivity(vaiParaFormularioActivity);
//    }
}

