package com.beatriz.androidlistafuncionariosbea.retrofit.service;

import com.beatriz.androidlistafuncionariosbea.model.Funcionario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface FuncionarioService {

    public static final String BASE_URL = "http://192.168.0.11:8080/";

    @GET("funcionarios/")
   Call<List<Funcionario>> getFuncionarios();

    @POST ("funcionarios/")
    Call<Funcionario> adiciona(@Body Funcionario funcionario);
}
