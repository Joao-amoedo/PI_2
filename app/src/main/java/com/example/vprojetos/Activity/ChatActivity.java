package com.example.vprojetos.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.example.vprojetos.Activity.ui.adpter.MensagemAdapter;
import com.example.vprojetos.R;
import com.example.vprojetos.callback.EnviarMensagemCallback;
import com.example.vprojetos.config.ChatService;
import com.example.vprojetos.model.Mensagem;
import com.example.vprojetos.model.RespostaServidor;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatActivity extends AppCompatActivity {



    private final  int  VIEW_MY_MESSAGE = 1;
    private  final int  VIEW_BOT_MESSAGE = 2;

    private Button button;
    private int idClient = 1;
    private EditText editText;
    private ListView listaDeMensagens;
    private List<Mensagem> mensagens;
    private ChatService chatService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        getSupportActionBar().setTitle("ChatBot");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        listaDeMensagens =  findViewById(R.id.lv_mensagens);
        mensagens = new ArrayList<>();

        MensagemAdapter adapter = new MensagemAdapter(1, mensagens, this);
        listaDeMensagens.setAdapter((ListAdapter) adapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dialogflow-server-helppro-pi2.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();

        chatService = retrofit.create(ChatService.class);
        button =  findViewById(R.id.btn_enviar);
        editText = findViewById(R.id.et_texto);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //coloquei a my_message uma variavel antes de enviar invez de criar a my_message direto sem atribuir direto
                String textoCompo = editText.getText().toString();
                if(!textoCompo.isEmpty()){
                    Mensagem mensagem = new Mensagem( textoCompo,VIEW_MY_MESSAGE);
                    Call<RespostaServidor> call = chatService.enviar(mensagem);
                    call.enqueue(new EnviarMensagemCallback(ChatActivity.this));
                    colocaNaLista(mensagem);
                    editText.setText(null);
                }else{
                    Toast.makeText(ChatActivity.this, "Digite a mensagem", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public  void colocaNaLista(Mensagem mensagem){
        mensagens.add(mensagem);

        MensagemAdapter adapter = new MensagemAdapter(idClient,mensagens,this);

        listaDeMensagens.setAdapter((ListAdapter) adapter);
    }
}
