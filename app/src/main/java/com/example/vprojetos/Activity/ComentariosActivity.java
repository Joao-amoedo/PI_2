package com.example.vprojetos.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.vprojetos.R;

public class ComentariosActivity extends AppCompatActivity {

    ListView nomesListView, comentariosListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentarios);

        nomesListView = findViewById(R.id.idListViewComentariosActivityNomes);
        comentariosListView = findViewById(R.id.idListViewComentariosActivityNomes);

        String[] nomes = {"José","Maria","Rodolfo"};
        String[] comentarios = {"Esse projeto é maravilhoso","Esse projeto é perfeito","Esse projeto é divino"};


        ArrayAdapter<String> nomesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nomes);
        ArrayAdapter<String> comentariosAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, comentarios);

        nomesListView.setAdapter(nomesAdapter);
        comentariosListView.setAdapter(comentariosAdapter);

    }
}
