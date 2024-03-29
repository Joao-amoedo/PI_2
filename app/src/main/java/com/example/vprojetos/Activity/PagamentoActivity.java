package com.example.vprojetos.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vprojetos.R;
import com.example.vprojetos.config.ConfigPaypal;
import com.example.vprojetos.model.Projeto;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;

public class PagamentoActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int PAYPAL_REQUEST_CODE = 7171;
    private Projeto projeto;

    PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX) //Usar sandbox enquanto for teste, depois mudar para produção
            //.defaultUserEmail("sb-ngudt1856920@personal.example.com")
            .clientId(ConfigPaypal.PAYPAL_CLIENT_ID);
    TextView quantiaEditText;
    String quantia = "";
    private TextView tituloProjetoTextView;
    private ImageButton subtrairImageButton;
    private ImageButton adicionarImageButton;
    private TextView quantiaTextView;
    private Button pagarButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagamento);


        tituloProjetoTextView = findViewById(R.id.idTextviewPagamentoActivityTituloProjeto);
        subtrairImageButton = findViewById(R.id.idButtonPagamentoActivitySubtrair);
        adicionarImageButton = findViewById(R.id.idButtonPagamentoActivityAdicionar);
        quantiaTextView = findViewById(R.id.idEditTextPagamentoActivityQuantia);
        pagarButton = findViewById(R.id.idButtonPagamentoActivityPagar);
        Bundle extras = getIntent().getExtras();
        projeto = (Projeto) extras.get("projeto");

        tituloProjetoTextView.setText(projeto.getNome());
        subtrairImageButton.setOnClickListener(this);
        adicionarImageButton.setOnClickListener(this);
        pagarButton.setOnClickListener(this);



        // Inicializando Serviço do Paypal


        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);

        //inicializa();

    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();

    }

    private void inicializa() {
        pagarButton = findViewById(R.id.idButtonPagamentoActivityPagar);
        quantiaEditText = findViewById(R.id.idEditTextPagamentoActivityQuantia);
        //copiaEmailButton = findViewById(R.id.idButtonPagamentoActivityCopiaEmail);
        pagarButton.setOnClickListener(this);
        //copiaEmailButton.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        projeto = (Projeto) extras.get("projeto");


    }

    @Override
    public void onClick(View view) {
        if (view == pagarButton){
            processamentoDoPagamento();
        }
        else if(view == subtrairImageButton){
            Integer integer = Integer.valueOf(quantiaTextView.getText().toString());
            if(Integer.parseInt(quantiaTextView.getText().toString()) > 10)
                integer -= 10;
            quantiaTextView.setText(integer + "");

        }else if(view == adicionarImageButton){
            Integer integer = Integer.valueOf(quantiaTextView.getText().toString());
            integer += 10;
            quantiaTextView.setText(integer + "");

        }
    }

    private void processamentoDoPagamento() {

        quantia = quantiaTextView.getText().toString();
        BigDecimal quantiaBigDecimal = new BigDecimal(String.valueOf(quantia));

        PayPalPayment payPalPayment = new PayPalPayment(quantiaBigDecimal, "BRL", "Pagamento no valor de", PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);

        startActivityForResult(intent, PAYPAL_REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("pagamentodetalhe", "cheguei aq");
        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation != null) {
                    try {
                        String paymentDetails = confirmation.toJSONObject().toString(4);

                        Intent intent = new Intent(this, DetalhesPagamentoActivity.class)
                                .putExtra("DetalhesPagamento", paymentDetails)
                                .putExtra("projeto", projeto)
                                .putExtra("QuantidadePagamento", quantia);
                        startActivity(intent);
                        finish();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == RESULT_CANCELED) {
                mensagem("Cancelado");
            }
        }
    }

    private void setClipboard(Context context, String text) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
            clipboard.setPrimaryClip(clip);
        }
    }

    private void mensagem(Object s) {
        Toast.makeText(this, s.toString(), Toast.LENGTH_SHORT).show();
    }
}
