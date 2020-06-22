package com.example.vprojetos.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.vprojetos.Activity.ui.recyclerview.adapter.ListaProjetosAdapter;
import com.example.vprojetos.R;
import com.example.vprojetos.config.Conexao;
import com.example.vprojetos.model.Projeto;
import com.example.vprojetos.utils.QuickSort;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        ValueEventListener, ListaProjetosAdapter.OneProjetoListener {

    private ListaProjetosAdapter adapter;

    private File localFile;
    private List<File> arrayImagensSecundarias;
    private List<Projeto> listProjeto;
    private RecyclerView listaProjetosRecyclerView;
    private ImageView paginaUsuarioImageView;
    private ImageView criarProjetoImageView;
    private ImageView chatBotImageView;

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

        chatBotImageView = findViewById(R.id.idImageViewMainActivityChatBot);
        criarProjetoImageView = findViewById(R.id.idImageViewMainActivityCriarProjeto);
        paginaUsuarioImageView = findViewById(R.id.idImageViewPaginaCriadorActivityImagem);



        findViewById(R.id.idImageViewMainActivityChatBot);
        listaProjetosRecyclerView = findViewById(R.id.idRecyclerViewMainActivityListaDeProjetos);
        arrayImagensSecundarias = new ArrayList<>();


        chatBotImageView.setOnClickListener(this);
        criarProjetoImageView.setOnClickListener(this);
        paginaUsuarioImageView.setOnClickListener(this);


    }

    private void mensagem(Object msg) {
        Toast.makeText(MainActivity.this, msg.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        if (view == criarProjetoImageView) {
            Intent intent = new Intent(this, CadastroNovoProjetoActivity.class);
            startActivity(intent);
        }else if(view == chatBotImageView){
            Intent intent = new Intent(MainActivity.this,ChatActivity.class);
            startActivity(intent);

        }else if(view == paginaUsuarioImageView){
            startActivity(new Intent(this, PaginaUsuarioActivity.class));
        }
    }

    private void ordenaMediaDecrescente() {
        Projeto[] arrayProjetos = getProjetosArray();
        ordenaProjetos(arrayProjetos, QuickSort.COMPARACAO_MEDIA, true);
        configureAdapter(listProjeto);
    }

    private void ordenaProjetos(Projeto[] arrayProjetos, int comparacao, boolean decrescente) {
        QuickSort quickSort = new QuickSort(comparacao);
        quickSort.sort(arrayProjetos, 0, arrayProjetos.length - 1 );
        listProjeto = Arrays.asList(arrayProjetos);
        if(decrescente)
            Collections.reverse(listProjeto);
    }

    private Projeto[] getProjetosArray() {
        Projeto[] arrayProjetos = new Projeto[listProjeto.size()];
        int i = 0;
        for (Projeto projeto : listProjeto) {
            arrayProjetos[i++] = projeto;
        }
        return arrayProjetos;
    }

    @Override
    protected void onResume() {
        super.onResume();
        pegaProjetos();

    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

        pegaTodosProjetos(dataSnapshot);


        configureAdapter(listProjeto);


    }

    public void configureAdapter(List<Projeto> listProjeto) {
        adapter = new ListaProjetosAdapter(this, listProjeto, this);
        listaProjetosRecyclerView.setAdapter(adapter);


    }

    private void pegaTodosProjetos(@NonNull DataSnapshot dataSnapshot) {
        listProjeto = new ArrayList<>();
        HashMap<String, HashMap<String, Object>> map =
                (HashMap<String, HashMap<String, Object>>) dataSnapshot.getValue();
        for (String key : map.keySet()) {
            Projeto projeto = new Projeto(map.get(key));
            listProjeto.add(projeto);
        }

        ordenaMediaDecrescente();

    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
    }

    @Override
    public void onProjetoClick(int position) {
        Projeto projeto = listProjeto.get(position);

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


        storageReference.child("projetos")
                .child(projeto.getNome())
                .child("capa.png")
                .getFile(localFile)
                .addOnCompleteListener(new OnCompleteListener<FileDownloadTask.TaskSnapshot>() {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.idItemMenuMainSearch);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });

        return true;
    }
}
