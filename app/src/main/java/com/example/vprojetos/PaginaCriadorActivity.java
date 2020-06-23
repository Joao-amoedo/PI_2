package com.example.vprojetos;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.vprojetos.model.Usuario;

import java.text.SimpleDateFormat;
import java.util.Date;

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




        inicializa();


    }

    private void inicializa() {
        nomeTextView = findViewById(R.id.idTextViewPaginaUsuarioActivityNome);
        bioTextView = findViewById(R.id.idTextViewPaginaUsuarioActivityBio);
        enderecoTextView = findViewById(R.id.idTextViewPaginaUsuarioActivityEndereco);
        dataCriacaoContaTextView = findViewById(R.id.idTextViewPaginaUsuarioActivityEmail);
        quantidadeContribuicoesTextView = findViewById(R.id.idTextViewPaginaUsuarioActivityQuantidadeContribuicoes);
        quantidadeProjetosCriadosTextView = findViewById(R.id.idTextViewPaginaUsuarioActivityQuantidadeProjetosCriados);
        TextView dataUltimaConexaoTextView =  findViewById(R.id.idTextViewPaginaUsuarioActivityCPF);



        Bundle extras = getIntent().getExtras();
        usuario = (Usuario) extras.get("usuario");

        int quantidadeProjetosCriados = usuario.getProjetosCriados().size();
        String nome = usuario.getNome();
        int quantidadeContribuicoes = usuario.getDoacoes().size();

        nomeTextView.setText(nome);
        quantidadeContribuicoesTextView.setText(quantidadeContribuicoes + "");
        quantidadeProjetosCriadosTextView.setText(quantidadeProjetosCriados + "");
        enderecoTextView.setText( usuario.getPais() + ", " + usuario.getEstado() );
        //dataUltimaConexaoTextView.setText(usuario.getUltimoLogin());

    }


}
