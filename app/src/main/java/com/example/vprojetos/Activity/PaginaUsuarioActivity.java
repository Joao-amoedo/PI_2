package com.example.vprojetos.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.vprojetos.R;
import com.example.vprojetos.model.Usuario;

public class PaginaUsuarioActivity extends AppCompatActivity {

    private TextView quantidadeProjetosCriacdosTextView;
    private TextView quantidadeContribuicoesTextView;
    private TextView nomeTextView;
    private TextView enderecoTextView;
    private TextView emailTextView;
    private TextView bioTextView;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_usuario);

        inicializa();

    }

    private void inicializa() {
        bioTextView = findViewById(R.id.idTextViewPaginaUsuarioActivityBio);
        emailTextView = findViewById(R.id.idTextViewPaginaUsuarioActivityEmail);
        enderecoTextView = findViewById(R.id.idTextViewPaginaUsuarioActivityEndereco);
        nomeTextView = findViewById(R.id.idTextViewPaginaUsuarioActivityNome);
        quantidadeContribuicoesTextView = findViewById(R.id.idTextViewPaginaUsuarioActivityQuantidadeContribuicoes);
        quantidadeProjetosCriacdosTextView = findViewById(R.id.idTextViewPaginaUsuarioActivityQuantidadeProjetosCriados);

        usuario = Usuario.usuario;

        emailTextView.setText(usuario.getEmail());
        nomeTextView.setText(usuario.getNome());
        quantidadeContribuicoesTextView.setText(usuario.getDoacoes().size() + "");
        quantidadeProjetosCriacdosTextView.setText(usuario.getProjetosCriados().size() + "");
        enderecoTextView.setText(usuario.getPais() + "," + usuario.getEstado());

    }


}
