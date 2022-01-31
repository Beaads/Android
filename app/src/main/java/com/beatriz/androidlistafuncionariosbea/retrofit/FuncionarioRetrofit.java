//package com.beatriz.androidlistafuncionariosbea.retrofit;
//
//import android.util.Log;
//
//import com.beatriz.androidlistafuncionariosbea.model.Funcionario;
//import com.beatriz.androidlistafuncionariosbea.retrofit.service.FuncionarioService;
//
//import java.io.IOException;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//public class FuncionarioRetrofit {
//
//    Funcionario funcionario = new Funcionario("Bea","18","dev");
//
//    public FuncionarioRetrofit() throws IOException {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(FuncionarioService.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//      FuncionarioService service = retrofit.create(FuncionarioService.class);
//        Call<Funcionario> funcionarios = service.adiciona(funcionario);
//        funcionarios.execute();
//    }
//}
//
