package com.example.vprojetos.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.vprojetos.R;
import com.paypal.android.sdk.payments.PayPalOAuthScopes;
import com.paypal.android.sdk.payments.PayPalProfileSharingActivity;

public class InitialActivity extends AppCompatActivity implements View.OnClickListener{

    Button cadastrarButton;
    Button loginButton;
    Button pagamentoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);

        getSupportActionBar().hide(); //Esconde a Action Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        inicializa();

    }

    private void inicializa() {
        cadastrarButton = findViewById(R.id.idButtonInitialActivityCadastro);
        loginButton = findViewById(R.id.idButtonInitialActivityLogin);
        pagamentoButton = findViewById(R.id.idButtonInitialActivityPagamento);

        cadastrarButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        pagamentoButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == cadastrarButton){
            Intent intent = new Intent(this, CadastroActivity.class);
            startActivity(intent);
        }else if(view == loginButton){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }else if(view == pagamentoButton){
            Intent intent = new Intent(this, PagamentoActivity.class);
            startActivity(intent);
        }
    }
}
