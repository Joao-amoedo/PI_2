package com.example.vprojetos.Activity;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vprojetos.PaginaCriadorActivity;
import com.example.vprojetos.R;
import com.example.vprojetos.config.Conexao;
import com.example.vprojetos.model.Projeto;
import com.example.vprojetos.model.ProjetoDAO;
import com.example.vprojetos.model.Usuario;
import com.example.vprojetos.model.UsuarioDAO;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class PaginaProjetoActivity extends Activity implements View.OnClickListener, Serializable {
    private TextView tituloDoProjetoTextView;
    private TextView nomeAutorProjetoTextView;
    private TextView descricaoProjetoTextView;
    private TextView nomeCategoriaTextView,
            valorArrecadadoTextView,
            metaValorTextView,
            quantidadeDoadoresTextView,
            diasPassadosTextView,
            quantidadeComentariosTextView;
    private Projeto projeto;
    private TextView lerMaisSobreProjetoTextView;
    private ConstraintLayout layoutAutorConstraintLayout;
    private ImageView imagemCapaImageView;
    private LinearLayout layoutDescricaoProjetoLinearLayout, layoutComentariosLinearLayout;
    private Button contribuirButton;
    private ArrayList<ImageView> arrayListEstrelasImageView = new ArrayList<ImageView>();
    private Uri imagemCapaUri;
    private ArrayList<File> arrayImagensSecundarias = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_projeto);
        inicializa();
    }

    private void inicializa() {
        projeto = (Projeto) getIntent().getExtras().get("projeto");
        inicializaViews();
        inicializaListeners();
        inicializaCapa();
        //inicializaTextos();
        inicializaNotas();

    }

    private void inicializaListeners() {
        layoutAutorConstraintLayout.setOnClickListener(this);
        layoutDescricaoProjetoLinearLayout.setOnClickListener(this);
        contribuirButton.setOnClickListener(this);
        layoutComentariosLinearLayout.setOnClickListener(this);
        for (ImageView estrela : arrayListEstrelasImageView) {
            estrela.setOnClickListener(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        inicializaTextos();
    }

    private void inicializaViews() {
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
        contribuirButton = findViewById(R.id.idButtonPaginaProjetoActivityBotaoContribuir);
        quantidadeComentariosTextView = findViewById(R.id.idTextViewPaginaProjetoQuantidadeDeComentarios);
        layoutComentariosLinearLayout = findViewById(R.id.idLinearLayoutPaginaProjetoActivityLayoutComentarios);

        arrayListEstrelasImageView.add((ImageView) findViewById(R.id.idImageViewPaginaProjetoActivityEstrela1));
        arrayListEstrelasImageView.add((ImageView) findViewById(R.id.idImageViewPaginaProjetoActivityEstrela2));
        arrayListEstrelasImageView.add((ImageView) findViewById(R.id.idImageViewPaginaProjetoActivityEstrela3));
        arrayListEstrelasImageView.add((ImageView) findViewById(R.id.idImageViewPaginaProjetoActivityEstrela4));
        arrayListEstrelasImageView.add((ImageView) findViewById(R.id.idImageViewPaginaProjetoActivityEstrela5));
    }

    private void inicializaCapa() {
        File imagemCapa = (File) getIntent().getExtras().get("imagemCapa");
        imagemCapaUri = Uri.fromFile(imagemCapa);
        //imagemCapaImageView.setImageURI(uri);
        Picasso
                .get()
                .load(imagemCapaUri)
                .fit()
                .centerCrop()
                .into(imagemCapaImageView);
    }

    private void inicializaTextos() {
        tituloDoProjetoTextView.setText(projeto.getNome());
        nomeAutorProjetoTextView.setText(projeto.getNomeAutor());
        descricaoProjetoTextView.setText(projeto.getDescricaoDoProjeto());
        nomeCategoriaTextView.setText(projeto.getCategoria().toString());
        valorArrecadadoTextView.setText("Arrecadado " + projeto.getDinheiroArrecadado() + " R$");
        quantidadeDoadoresTextView.setText(projeto.getUsuariosDoacoes().size() + "");
        metaValorTextView.setText("Meta " + projeto.getDinheiroAlvo() + " R$");
        quantidadeComentariosTextView.setText(projeto.getComentariosHash().size() + "");
        long diferencaDeDias = getDiferencaDeDias(new Date().getTime(), projeto.getDataFim());
        diasPassadosTextView.setText(diferencaDeDias + "");
    }

    private void inicializaNotas() {
        String uid = Conexao.getFirebaseAuth().getUid();
        HashMap<String, Integer> notas = projeto.getNotas();



        if (notas.containsKey(uid)) {
            Integer nota = notas.get(uid) - 1;
            ImageView estrela = arrayListEstrelasImageView.get(nota);
            definePontuacao(estrela);
        }
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
        if (view == layoutAutorConstraintLayout) {
            carregaPaginaAutor();
        } else if (view == layoutDescricaoProjetoLinearLayout) {
            carregaDescricaoProjeto();

        } else if (view == contribuirButton) {
            Intent intent = new Intent(this, PagamentoActivity.class);
            intent.putExtra("projeto", projeto);
            startActivity(intent);
        } else if (view == layoutComentariosLinearLayout) {
            startActivityComentarios();
            //TODO construir pagina de comentarios
        } else if (arrayListEstrelasImageView.contains(view)) {
            definePontuacao(view);
        }


    }

    private void carregaDescricaoProjeto() {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Aguarde");
        dialog.setMessage("Carregando . . .");
        dialog.show();
        pegaImagensSecundarias(1, dialog);
    }

    private void carregaPaginaAutor() {
        final String uidAutor = projeto.getUidAutor();

        Conexao.getDatabase().child("usuarios").child(uidAutor).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap<String, Object> map =(HashMap<String, Object>) dataSnapshot.getValue();
                Usuario usuario = new Usuario(map);

                Intent intent = new Intent(PaginaProjetoActivity.this, PaginaCriadorActivity.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //startActivity(new Intent(this, PaginaCriadorActivity.class));
        //TODO levar para a pagina do autor
    }

    private void startActivityComentarios() {
        Intent intent = new Intent(this, ComentariosActivity.class);
        intent.putExtra("nomeAutor", projeto.getNomeAutor());
        intent.putExtra("projeto", projeto);
        intent.putExtra("capa", imagemCapaUri);
        startActivity(intent);
    }

    private void pegaImagensSecundarias(final Integer numeroDaImagem, final ProgressDialog dialog) {
        StorageReference reference = FirebaseStorage.getInstance().getReference();
        File file = null;
        try {
            file = File.createTempFile("imagem", "jpg");
        } catch (IOException e) {
            dialog.dismiss();
            return;
        }

        final File fileFinal = file;

        reference
                .child("projetos")
                .child(projeto.getNome())
                .child(numeroDaImagem + ".png")
                .getFile(file)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        arrayImagensSecundarias.add(fileFinal);
                        pegaImagensSecundarias(numeroDaImagem + 1, dialog);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
                Log.i("teste", "" + numeroDaImagem);
                startDescricaoProjeto();
            }
        });

    }

    private void startDescricaoProjeto() {

        Intent intent = new Intent(PaginaProjetoActivity.this,
                DescricaoProjetoActivity.class);
        intent.putExtra("capa", imagemCapaUri);
        intent.putExtra("projeto", projeto);
        intent.putExtra("imagensSecundarias", arrayImagensSecundarias);
        startActivity(intent);

    }

    private void definePontuacao(View estrela) {
        int index = arrayListEstrelasImageView.indexOf(estrela);
        for (int i = 0; i < arrayListEstrelasImageView.size(); i++) {
            if (i <= index)
                arrayListEstrelasImageView.get(i).setImageResource(android.R.drawable.btn_star_big_on);
            else
                arrayListEstrelasImageView.get(i).setImageResource(android.R.drawable.btn_star_big_off);
        }
        setNota(index + 1);


    }

    private void setNota(int nota) {
        projeto.adicionarNota(Conexao.getFirebaseAuth().getUid(), nota);
        ProjetoDAO.atualizaNotas( projeto);
        Usuario.usuario.addNotas(projeto.getNome(), nota);
        UsuarioDAO.updateNotas();

    }
}
