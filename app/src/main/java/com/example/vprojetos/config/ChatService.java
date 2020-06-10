package com.example.vprojetos.config;

import com.example.vprojetos.model.Mensagem;
import com.example.vprojetos.model.RespostaServidor;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ChatService {
    @POST("message/text/send")
    Call<RespostaServidor> enviar(@Body Mensagem mensagem);
}
