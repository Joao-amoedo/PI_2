package com.example.vprojetos.callback;

import android.widget.Toast;

import com.example.vprojetos.activity.MainActivity;
import com.example.vprojetos.modelo.Mensagem;
import com.example.vprojetos.modelo.RespostaServidor;
import com.example.vprojetos.Activity.ChatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnviarMensagemCallback implements Callback<RespostaServidor> {
    private final  int  VIEW_MY_MESSAGE = 1;
    private  final int  VIEW_BOT_MESSAGE = 2;

    ChatActivity activity;
    public EnviarMensagemCallback(ChatActivity activity){
        this.activity = activity;
    }

    @Override
    public void onResponse(Call<RespostaServidor> call, Response<RespostaServidor> response) {

        if(response.isSuccessful()){
//            Toast.makeText(activity.getApplicationContext(),"SUCESSO", Toast.LENGTH_SHORT).show();
            RespostaServidor respostaServidor = response.body();
            RespostaServidor id = respostaServidor;
            activity.colocaNaLista(new Mensagem(id.getTexto(),VIEW_BOT_MESSAGE));
        }  else {
            Toast.makeText(activity.getApplicationContext(),"Resposta ERRO", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Call<RespostaServidor> call, Throwable t) {
    }

}
