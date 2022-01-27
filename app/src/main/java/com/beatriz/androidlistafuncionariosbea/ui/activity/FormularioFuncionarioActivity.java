package com.beatriz.androidlistafuncionariosbea.ui.activity;

import static com.beatriz.androidlistafuncionariosbea.ui.activity.FuncionarioActivityConstantes.CHAVE_FUNCIONARIO;
import static com.beatriz.androidlistafuncionariosbea.ui.activity.FuncionarioActivityConstantes.CODIGO_RESULTADO_FUNCIONARIO_CRIADO;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.beatriz.androidlistafuncionariosbea.R;
import com.beatriz.androidlistafuncionariosbea.model.Funcionario;

import java.io.Serializable;

public class FormularioFuncionarioActivity extends AppCompatActivity {

    private int posicaoRecebida;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_funcionario);

        Intent dadosRecebidos = getIntent();
        if (dadosRecebidos.hasExtra(CHAVE_FUNCIONARIO) && dadosRecebidos.hasExtra("posicao")) {
            Funcionario funcionarioRecebido = (Funcionario) dadosRecebidos.getSerializableExtra(CHAVE_FUNCIONARIO);
            posicaoRecebida = dadosRecebidos.getIntExtra("posicao", -1);
            TextView nome = findViewById(R.id.formulario_funcionario_nome);
            nome.setText(funcionarioRecebido.getNome());

            TextView setor = findViewById(R.id.formulario_funcionario_setor);
            setor.setText(funcionarioRecebido.getSetor());

            TextView email = findViewById(R.id.formulario_funcionario_email);
            email.setText(funcionarioRecebido.getEmail());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario_funcionario_salva, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(ehMenuSalvaFuncionario(item)){
            Funcionario funcionarioCriado = criaFuncionario();
            retornaFuncionario(funcionarioCriado);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void retornaFuncionario (Funcionario funcionario) {
        Intent resultadoInsercao = new Intent();
        resultadoInsercao.putExtra(CHAVE_FUNCIONARIO, funcionario);
        resultadoInsercao.putExtra("posicao", posicaoRecebida);
        setResult(CODIGO_RESULTADO_FUNCIONARIO_CRIADO,resultadoInsercao);
    }


    @NonNull
    private Funcionario criaFuncionario() {
        EditText nome = findViewById(R.id.formulario_funcionario_nome);
        EditText setor = findViewById(R.id.formulario_funcionario_setor);
        EditText email = findViewById(R.id.formulario_funcionario_email);
        return new Funcionario(nome.getText().toString(),
                setor.getText().toString(), email.getText().toString());
    }

    private boolean ehMenuSalvaFuncionario(@NonNull MenuItem item) {
        return item.getItemId() == R.id.menu_formulario_funcionario_ic_salva;
    }
}
