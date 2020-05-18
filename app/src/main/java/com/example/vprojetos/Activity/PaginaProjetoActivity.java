package com.example.vprojetos.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vprojetos.R;
import com.example.vprojetos.model.Projeto;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class PaginaProjetoActivity extends Activity implements View.OnClickListener{
    private TextView tituloDoProjetoTextView;
    private TextView nomeAutorProjetoTextView;
    private TextView descricaoProjetoTextView;
    private TextView nomeCategoriaTextView, valorArrecadadoTextView, metaValorTextView, quantidadeDoadoresTextView, diasPassadosTextView, quantidadeComentariosTextView;
    private Projeto projeto;
    private TextView lerMaisSobreProjetoTextView;
    private ConstraintLayout layoutAutorConstraintLayout;
    private ImageView imagemCapaImageView;
    private LinearLayout layoutDescricaoProjetoLinearLayout, layoutComentariosLinearLayout;
    private Button btnContribuirButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_projeto);


        inicializa();


    }

    private void inicializa() {
        tituloDoProjetoTextView = findViewById(R.id.idTextViewPaginaProjetoActivityTituloProjeto);
        nomeAutorProjetoTextView = findViewById(R.id.idTextViewPaginaProjetoActivityNomeAutor);
        descricaoProjetoTextView = findViewById(R.id.idTextViewPaginaProjetoActivityDescricaoProjeto);
        nomeCategoriaTextView = findViewById(R.id.idTextViewPaginaProjetoActivityNomeCategoria);
        metaValorTextView = findViewById(R.id.idTextViewPaginaProjetoActivityMetaValor);
        valorArrecadadoTextView = findViewById(R.id.idTextViewPaginaProjetoActivityValorArrecadado);
        quantidadeDoadoresTextView = findViewById(R.id.idTextViewPaginaProjetoActivityQuantidadeDoadoresDescrição);
        diasPassadosTextView = findViewById(R.id.idTextViewPaginaProjetoActivityDiasRestantes);
        lerMaisSobreProjetoTextView = findViewById(R.id.idTextViewPaginaProjetoActivityLerMaisSobreProjeto);
        layoutAutorConstraintLayout = findViewById(R.id.idConstraintLayoutPaginaProjetoActivityLayoutAutor);
        layoutDescricaoProjetoLinearLayout = findViewById(R.id.idLinearLayoutPaginaProjetoActivityLayoutDescricaoProjeto);
        imagemCapaImageView = findViewById(R.id.idImageViewPaginaProjetoActivityImagemCapa);
        btnContribuirButton = findViewById(R.id.idButtonPaginaProjetoActivityBotaoContribuir);
        quantidadeComentariosTextView = findViewById(R.id.idTextViewPaginaProjetoQuantidadeDeComentarios);
        layoutComentariosLinearLayout = findViewById(R.id.idLinearLayoutPaginaProjetoActivityLayoutComentarios);


        projeto = (Projeto) getIntent().getExtras().get("projeto");
        File imagemCapa = (File) getIntent().getExtras().get("imagemCapa");

        Uri uri = Uri.fromFile(imagemCapa);
        //imagemCapaImageView.setImageURI(uri);

        Picasso
                .get()
                .load(uri)
                .fit()
                .centerCrop()
                .into(imagemCapaImageView);


        tituloDoProjetoTextView.setText(projeto.getNome());
        nomeAutorProjetoTextView.setText(projeto.getNomeAutor());
        descricaoProjetoTextView.setText(projeto.getDescricaoDoProjeto());
        nomeCategoriaTextView.setText(projeto.getCategoria().toString());
        valorArrecadadoTextView.setText("Arrecadado " +projeto.getDinheiroArrecadado() + " R$");
        quantidadeDoadoresTextView.setText(projeto.getUsuariosDoacoes().size() + "");
        metaValorTextView.setText("Meta " + projeto.getDinheiroAlvo() + " R$");
        quantidadeComentariosTextView.setText(projeto.getComentarios().size() + "");

        long diferencaDeDias = getDiferencaDeDias(new Date().getTime(), projeto.getDataFim());
        diasPassadosTextView.setText(diferencaDeDias + "");

        layoutAutorConstraintLayout.setOnClickListener(this);
        layoutDescricaoProjetoLinearLayout.setOnClickListener(this);
        btnContribuirButton.setOnClickListener(this);
        layoutComentariosLinearLayout.setOnClickListener(this);

        //TODO Centrarlizar imagem
        //LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(250, 250);
        //imagemCapaImageView.setLayoutParams(layoutParams);
    }

    private void getImage() throws IOException {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        final File fileImage = File.createTempFile("images", "jpg");
        storageReference.child("projetos").child(projeto.getNome()).child("capa").getFile(fileImage).addOnCompleteListener(new OnCompleteListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<FileDownloadTask.TaskSnapshot> task) {
                String absolutePath = fileImage.getAbsolutePath();
                Picasso
                        .get()
                        .load(absolutePath)
                        .fit()
                        .centerCrop()
                        .into(imagemCapaImageView);
            }
        });


    }

    private long getDiferencaDeDias(long dataInicio, long dataFim) {
        //milliseconds
        long different = dataFim - dataInicio;

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long dias = different / daysInMilli;

        return dias;
    }

    private void mensagem(Object projeto) {
        Toast.makeText(this, projeto.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        if(view == layoutAutorConstraintLayout){
            mensagem("Levar para a pagina do autor");
            //TODO levar para a pagina do autor
        }
        else if(view == layoutDescricaoProjetoLinearLayout){
            mensagem("Levar para descrição detalahda do projeto");
            //TODO Levar para descrição detalahda do projeto
        }
        else if(view == btnContribuirButton){
            mensagem("Levar para pagina do pagamento");
            //TODO construir pagina do pagamento
        }
        else if(view == layoutComentariosLinearLayout){
            mensagem("Levar para pagina de comentarios");
            //TODO construir pagina de comentarios
        }
    }
}
