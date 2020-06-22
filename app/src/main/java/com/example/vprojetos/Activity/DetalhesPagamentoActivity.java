package com.example.vprojetos.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vprojetos.R;
import com.example.vprojetos.config.Conexao;
import com.example.vprojetos.model.Projeto;
import com.example.vprojetos.model.ProjetoDAO;
import com.example.vprojetos.model.Usuario;
import com.example.vprojetos.model.UsuarioDAO;

import org.json.JSONException;
import org.json.JSONObject;

public class DetalhesPagamentoActivity extends AppCompatActivity {

    TextView idTextView, quantiaTextView, statusTextView;

    ImageView ivSucesso;

    private Projeto projeto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);

        inicializa();
        Intent intent = getIntent();
        projeto = (Projeto) intent.getExtras().get("projeto");



        try {

            JSONObject detalhesPagamento = new JSONObject(intent.getStringExtra("DetalhesPagamento"));
            verificaStatus(detalhesPagamento.getJSONObject("response"), intent.getStringExtra("QuantidadePagamento"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ivSucesso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voltarTelaPrincipal();
            }
        });
    }

    private void verificaStatus(JSONObject response, String quantidadePagamento) {
        try {


            String state = response.getString("state");
            if(state.equals("approved")){


                Double quantidade = Double.parseDouble(quantidadePagamento);
                String uid = Conexao.getFirebaseAuth().getUid();


                projeto.addDoacao(quantidade);

                ProjetoDAO.atualizaProjeto(projeto);
                Usuario.usuario.addDoacoes(projeto.getNome(), quantidade);

                UsuarioDAO.updateDoacoes();



            }else{
                statusTextView.setText("deu ruim");
            }
            idTextView.setText(response.getString(String.format("$%s", quantidadePagamento)));



            if(state.equals("approved")){
                mensagem("Pagamento confirmado");
            }else{
                mensagem("Erro ao realizar pagamento");
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }



    }



    private void  mensagem(Object s){
        Toast.makeText(this, s.toString(), Toast.LENGTH_SHORT).show();
    }
    private void inicializa() {
        idTextView = findViewById(R.id.idTextViewPaymentDetailsActivityId);
        quantiaTextView = findViewById(R.id.idTextViewPaymentDetailsActivityQuantia);
        statusTextView = findViewById(R.id.idTextViewPaymentDetailsActivityStatus);
        ivSucesso = findViewById(R.id.ivSucesso);
    }


    public void voltarTelaPrincipal(){

        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

}
