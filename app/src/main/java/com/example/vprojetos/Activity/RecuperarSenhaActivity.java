package com.example.vprojetos.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vprojetos.R;
import com.example.vprojetos.config.Conexao;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;

public class RecuperarSenhaActivity extends AppCompatActivity {

    private Button btRecuperarSenha;
    private EditText etEmail;
    private static FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_senha);

        getSupportActionBar().hide(); //Esconde a Action Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        firebaseAuth = FirebaseAuth.getInstance();
        etEmail = findViewById(R.id.etEmail);
        btRecuperarSenha = findViewById(R.id.btRecuperarSenha);
        listenerButton();

    }


    private void listenerButton() {
            btRecuperarSenha.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String email = etEmail.getText().toString();

                    if (email.isEmpty()) {
                        Toast.makeText(RecuperarSenhaActivity.this, "E-mail obrigatório!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    firebaseAuth.sendPasswordResetEmail(email);
                    Toast.makeText(RecuperarSenhaActivity.this, "Email enviado com sucesso", Toast.LENGTH_SHORT).show();
                    finish();


                }
            });




    }

}