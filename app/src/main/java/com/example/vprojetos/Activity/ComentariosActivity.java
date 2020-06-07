package com.example.vprojetos.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vprojetos.R;
import com.example.vprojetos.config.Conexao;
import com.example.vprojetos.model.Comentario;
import com.example.vprojetos.model.Projeto;
import com.example.vprojetos.model.ProjetoDAO;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

public class ComentariosActivity extends AppCompatActivity implements View.OnClickListener {


    private Button enviarButton;
    private EditText comentarioEditText;
    private Projeto projeto;
    private String uid;
    private ListView comentariosListView;
    private Uri imagemCapaUri;
    private ImageView imagemCapaImageView;
    private TextView tituloImagemCapaTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentarios);

        inicializa();


    }

    private void inicializa() {
        comentarioEditText = findViewById(R.id.idEditTextComentariosActivityComentario);
        comentariosListView = findViewById(R.id.idListViewComentariosActivityComentarios);
        imagemCapaImageView = findViewById(R.id.idImageViewComentariosActivityCapa);
        tituloImagemCapaTextView = findViewById(R.id.idTextViewComentariosActivityTextoCapa);

        Bundle extras = getIntent().getExtras();
        projeto = (Projeto) extras.get("projeto");
        setImagemCapa(extras);

        uid = (String) extras.get("uid");
        enviarButton = findViewById(R.id.idButtonComentariosActivityEnviar);
        enviarButton.setOnClickListener(this);


    }

    private void setImagemCapa(Bundle extras) {
        imagemCapaUri = (Uri) extras.get("capa");

        tituloImagemCapaTextView.setText(projeto.getNome());


        Picasso
                .get()
                .load(imagemCapaUri)
                .fit()
                .centerCrop()
                .into(imagemCapaImageView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setAdapter();
    }

    private void setAdapter() {
        HashMap<String, Comentario> comentariosHash = projeto.getComentariosHash();
        Set<String> keySet = comentariosHash.keySet();
        ArrayList<Long> arrayList = new ArrayList<>();
        for (String key : keySet) {
            long l = Long.parseLong(key);
            arrayList.add(l);
        }


        Collections.sort(arrayList);
        Collections.reverse(arrayList);

        ArrayList<String> arrayListDescricao = new ArrayList<>();

        for (Long key : arrayList) {
            Comentario comentario = comentariosHash.get(key + "");
            String descricao = comentario.getDescricao();
            arrayListDescricao.add(descricao);
        }


        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayListDescricao);
        comentariosListView.setAdapter(stringArrayAdapter);
    }

    @Override
    public void onClick(View view) {
        if (view == enviarButton) {
            if (verificaCampos()) {
                adicionaNovoComentario();
            }
        }
    }

    private void adicionaNovoComentario() {
        String descricaoComentario = comentarioEditText.getText().toString();
        long dataComentario = new Date().getTime();
        Comentario comentario = new Comentario(projeto.getNomeAutor(), descricaoComentario, dataComentario);
        String uid = Conexao.getFirebaseAuth().getUid();
        projeto.addComentario(comentario);
        ProjetoDAO.atualizaComentario(this, projeto);
        comentarioEditText.clearFocus();
        comentarioEditText.setText("");
        setAdapter();
    }

    private boolean verificaCampos() {
        String comentario = comentarioEditText.getText().toString();
        if (comentario == null || comentario == "" || comentario.length() == 0) {
            mensagem("Escreva um comentário");
            return false;
        }
        return true;
    }

    private void mensagem(Object s) {
        Toast.makeText(this, s.toString(), Toast.LENGTH_SHORT).show();
    }
}
