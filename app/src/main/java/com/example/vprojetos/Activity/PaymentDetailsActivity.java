package com.example.vprojetos.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vprojetos.R;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentDetailsActivity extends AppCompatActivity {

    TextView idTextView, quantiaTextView, statusTextView;
    ImageView ivSucesso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);


        inicializa();
        Intent intent = getIntent();


        try {

            JSONObject detalhesPagamento = new JSONObject(intent.getStringExtra("DetalhesPagamento"));
            showDetails(detalhesPagamento.getJSONObject("response"), intent.getStringExtra("QuantidadePagamento"));
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

    private void showDetails(JSONObject response, String quantidadePagamento) {
        try {
            idTextView.setText(response.getString("id"));
            statusTextView.setText(response.getString("state"));
            idTextView.setText(response.getString(String.format("$%s", quantidadePagamento)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
