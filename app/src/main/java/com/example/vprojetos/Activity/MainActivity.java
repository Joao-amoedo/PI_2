package com.example.vprojetos.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.vprojetos.R;
import com.example.vprojetos.config.Conexao;
import com.example.vprojetos.model.Projeto;
import com.example.vprojetos.model.ProjetoDAO;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ValueEventListener, AdapterView.OnItemClickListener {

    private Button btnCriarNovoProjeto;
    private Button btnPegaProjetos;
    private ListView listaDeProjetosListView;
    private ArrayAdapter<String> adapter;
    HashMap<String, Projeto> projetos;

    private String[]  arrayString = {};
    private File localFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        inicializa();


    }


    private void pegaProjetos() {
        Conexao.getDatabase().child("projetos").addListenerForSingleValueEvent(this);

    }

    private void inicializa() {

        listaDeProjetosListView = findViewById(R.id.idListViewMainActivityListaDeProjetos);

        btnCriarNovoProjeto = findViewById(R.id.idButtonMainActivityCriarNovoProjeto);
        btnPegaProjetos = findViewById(R.id.idButtonMainActivityPegaProjetos);

        btnCriarNovoProjeto.setOnClickListener(this);
        btnPegaProjetos.setOnClickListener(this);

        listaDeProjetosListView.setOnItemClickListener(this);


    }

    private void mensagem(Object msg) {
        Toast.makeText(MainActivity.this, msg.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        if (view == btnCriarNovoProjeto) {
            Intent intent = new Intent(this, CadastroNovoProjetoActivity.class);
            startActivity(intent);
        } else if (view == btnPegaProjetos) {

            pegaProjetos();


        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayString);
        listaDeProjetosListView.setAdapter(adapter);

    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        HashMap<String, HashMap<String, Object>> map = (HashMap<String, HashMap<String, Object>>) dataSnapshot.getValue();
        projetos = new HashMap<String, Projeto>();
        String[] nomes = new String[map.size()];
        int contador = 0;
        for (String key: map.keySet()) {
            Projeto projeto = new Projeto(map.get(key));
            projetos.put(key, projeto);
            nomes[contador++] = key;
        }

        arrayString = nomes;
        onResume();


    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        final String nomeProjeto =(String) adapterView.getItemAtPosition(i);
        final Projeto projeto = projetos.get(nomeProjeto);
        final ArrayAdapter<String> adapter = this.adapter;
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Aguarde");
        dialog.setMessage("Carregando . . .");
        dialog.show();

        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        try {
            localFile = File.createTempFile("images", "jpg");
        } catch (IOException e) {

            e.printStackTrace();
        }

        storageReference.child("projetos").child(projeto.getNome()).child("capa.png").getFile(localFile).addOnCompleteListener(new OnCompleteListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<FileDownloadTask.TaskSnapshot> task) {
            dialog.dismiss();
            Intent intent = new Intent(MainActivity.this, PaginaProjetoActivity.class);
            intent.putExtra("projeto", projeto);
            intent.putExtra("imagemCapa", localFile);
            startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
            }
        });





    }
}
