package com.example.vprojetos.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.vprojetos.R;
import com.example.vprojetos.model.Projeto;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;

public class DescricaoProjetoActivity extends Activity {

    ImageView imagemCapaImageView;
    TextView textoCapaTextView, descricaoProjetoTextView;


    private Projeto projeto;
    private Uri imagemCapaUri;
    private ArrayList<File> imagensSecundarias;
    private LinearLayout imagensSecundariasLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descricao_projeto);

        inicializa();
        setImagensSecundarias();
    }

    private void inicializa() {
        imagemCapaImageView = findViewById(R.id.idImageViewDescricaoProjetoActivityCapa);
        textoCapaTextView = findViewById(R.id.idTextViewDescricaoProjetoActivityTextoCapa);
        descricaoProjetoTextView = findViewById(R.id.idTextViewDescricaoProjetoActivityDescricao);
        imagensSecundariasLinearLayout = findViewById(R.id.idLinearLayoutDescricaoProjetoActivityLayoutImagens);

        Bundle extras = getIntent().getExtras();
        imagemCapaUri = (Uri) extras.get("capa");
        projeto = (Projeto) extras.get("projeto");
        imagensSecundarias = (ArrayList<File>) extras.get("imagensSecundarias");


        Picasso
                .get()
                .load(imagemCapaUri)
                .fit()
                .centerCrop()
                .into(imagemCapaImageView);

        textoCapaTextView.setText(projeto.getNome());
        descricaoProjetoTextView.setText(projeto.getDescricaoDoProjeto());

    }

    private void setImagensSecundarias() {

        for (File file : imagensSecundarias) {

            LinearLayout parent = new LinearLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 10, 0, 10);
            parent.setGravity(Gravity.CENTER);
            parent.setLayoutParams(params);
            parent.setOrientation(LinearLayout.HORIZONTAL);

            ImageView imageView = new ImageView(this);
            Uri uri = Uri.fromFile(file);
            imageView.setImageURI(uri);

            parent.addView(imageView);


            imagensSecundariasLinearLayout.addView(parent);


        }

    }

}
