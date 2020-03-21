package com.example.vprojetos.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.vprojetos.R;

public class InitialActivity extends AppCompatActivity {

    Button buttonCadastrar;
    Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);

        getSupportActionBar().hide(); //Esconde a Action Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Button buttonCadastrar = findViewById(R.id.buttoncadastroid);
        Button buttonLogin = findViewById(R.id.buttonloginid);

        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InitialActivity.this, CadastroActivity.class);
                startActivity(intent);
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InitialActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
