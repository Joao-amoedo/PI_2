package com.example.vprojetos.callback;

import android.widget.Toast;

import com.example.vprojetos.Activity.ChatActivity;
import com.example.vprojetos.model.Mensagem;
import com.example.vprojetos.model.RespostaServidor;
import com.google.android.gms.common.api.Response;

import androidx.recyclerview.widget.SortedList;
import okhttp3.Call;

public class EnviarMensagemCallback implements SortedList.Callback<RespostaServidor> {
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
