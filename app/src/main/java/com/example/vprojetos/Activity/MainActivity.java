package com.example.vprojetos.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vprojetos.R;
import com.example.vprojetos.config.Conexao;
import com.example.vprojetos.model.Projeto;
import com.example.vprojetos.model.ProjetoDAO;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnCriarNovoProjeto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializa();


    }

    private void inicializa() {

        btnCriarNovoProjeto = findViewById(R.id.idButtonMainActivityCriarNovoProjeto);
        btnCriarNovoProjeto.setOnClickListener(this);

    }

    private void mensagem(Object msg) {
        Toast.makeText(MainActivity.this, msg.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {
        if (view == btnCriarNovoProjeto) {
            Intent intent = new Intent(this, CadastroNovoProjetoActivity.class);
            startActivity(intent);
        }
    }

}
