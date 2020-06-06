package com.example.vprojetos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.vprojetos.model.Usuario;

public class PaginaCriadorActivity extends Activity {

    private Usuario usuario;
    private TextView nomeTextView;
    private TextView bioTextView;
    private TextView enderecoTextView;
    private TextView dataCriacaoContaTextView;
    private TextView quantidadeContribuicoesTextView;
    private TextView quantidadeProjetosCriadosTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_criador);

        //inicializa();


    }

    private void inicializa() {
        nomeTextView = findViewById(R.id.idTextViewPaginaCriadorActivityNome);
        bioTextView = findViewById(R.id.idTextViewPaginaCriadorActivityBio);
        enderecoTextView = findViewById(R.id.idTextViewPaginaCriadorActivityEndereco);
        dataCriacaoContaTextView = findViewById(R.id.idTextViewPaginaCriadorActivityDataCriacaoConta);
        quantidadeContribuicoesTextView = findViewById(R.id.idTextViewPaginaCriadorActivityQuantidadeContribuicoes);
        quantidadeProjetosCriadosTextView = findViewById(R.id.idTextViewPaginaCriadorActivityQuantidadeProjetosCriados);

        Bundle extras = getIntent().getExtras();
        usuario = (Usuario) extras.get("usuario");

        int quantidadeProjetosCriados = usuario.getProjetosCriados().size();
        String nome = usuario.getNome();
        int quantidadeContribuicoes = usuario.getDoacoes().size();

        nomeTextView.setText(nome);
        quantidadeContribuicoesTextView.setText(quantidadeContribuicoes + "");
        quantidadeProjetosCriadosTextView.setText(quantidadeProjetosCriados + "");
    }
}
