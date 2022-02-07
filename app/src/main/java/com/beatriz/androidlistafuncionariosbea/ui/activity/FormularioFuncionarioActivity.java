package com.beatriz.androidlistafuncionariosbea.ui.activity;

import static com.beatriz.androidlistafuncionariosbea.ui.activity.FuncionarioActivityConstantes.CHAVE_FUNCIONARIO;
import static com.beatriz.androidlistafuncionariosbea.ui.activity.FuncionarioActivityConstantes.CODIGO_RESULTADO_FUNCIONARIO_CRIADO;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.beatriz.androidlistafuncionariosbea.R;
import com.beatriz.androidlistafuncionariosbea.model.Funcionario;
import com.beatriz.androidlistafuncionariosbea.retrofit.service.FuncionarioService;

import java.io.IOException;
import java.io.Serializable;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FormularioFuncionarioActivity extends AppCompatActivity {

    public Funcionario funcionarioUpdate = new Funcionario();

    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
    OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)).build();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(FuncionarioService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build();

    private int posicaoRecebida;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_funcionario);


        Intent dadosRecebidos = getIntent();

        if (dadosRecebidos.hasExtra(CHAVE_FUNCIONARIO) && dadosRecebidos.hasExtra("posicao")) {
            setTitle("Alterar Funcionario");
            Funcionario funcionarioRecebido = (Funcionario) dadosRecebidos.getSerializableExtra(CHAVE_FUNCIONARIO);
            funcionarioUpdate = funcionarioRecebido;
            posicaoRecebida = dadosRecebidos.getIntExtra("posicao", -1);
            TextView nome = findViewById(R.id.formulario_funcionario_nome);
            nome.setText(funcionarioRecebido.getNome());

            TextView idade = findViewById(R.id.formulario_funcionario_idade);
            idade.setText(funcionarioRecebido.getIdade());

            TextView setor = findViewById(R.id.formulario_funcionario_setor);
            setor.setText(funcionarioRecebido.getSetor());
            return;
        }
        setTitle("Criar Funcionario");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario_funcionario_salva, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (funcionarioUpdate.getNome() != null) {
            Funcionario funcionarioAlterado = criaFuncionario();
            //retornaFuncionario(funcionarioAlterado);
            FuncionarioService service = retrofit.create(FuncionarioService.class);
            Call<Funcionario> funcionarioAtt = service.atualizaFuncionario(funcionarioUpdate.getId(), funcionarioAlterado);
            funcionarioAtt.enqueue(new Callback<Funcionario>() {
                @Override
                public void onResponse(Call<Funcionario> call, Response<Funcionario> response) {
                    if(response.isSuccessful()) {
                        Toast.makeText(FormularioFuncionarioActivity.this, "Alterado com sucesso!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(FormularioFuncionarioActivity.this, "Erro ao alterar, tente novamente!", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Funcionario> call, Throwable t) {
                    Toast.makeText(FormularioFuncionarioActivity.this, "Ocorreu um erro, tente novamente mais tarde!" + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });
            funcionarioAtt.request();
            //finish();
            return super.onOptionsItemSelected(item);
        }

        if(ehMenuSalvaFuncionario(item)){
            Funcionario funcionarioCriado = criaFuncionario();
            //retornaFuncionario(funcionarioCriado);
            FuncionarioService service = retrofit.create(FuncionarioService.class);
            Call<Funcionario> funcionarios = service.adiciona(funcionarioCriado);
            funcionarios.enqueue(new Callback<Funcionario>() {
                @Override
                public void onResponse(Call<Funcionario> call, Response<Funcionario> response) {
                    if(response.isSuccessful()) {
                        Toast.makeText(FormularioFuncionarioActivity.this, "salvo com sucesso", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(FormularioFuncionarioActivity.this, "erro", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Funcionario> call, Throwable t) {
                    Toast.makeText(FormularioFuncionarioActivity.this, "erro grande " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });

            funcionarios.request();
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
        EditText idade = findViewById(R.id.formulario_funcionario_idade);
        EditText setor = findViewById(R.id.formulario_funcionario_setor);

        return new Funcionario(nome.getText().toString(),
                idade.getText().toString(), setor.getText().toString());

    }
    // cria botao check cria/altera funcionario
    private boolean ehMenuSalvaFuncionario(@NonNull MenuItem item) {
        return item.getItemId() == R.id.menu_formulario_funcionario_ic_salva;

    }
}
