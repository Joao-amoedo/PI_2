package com.example.vprojetos.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vprojetos.R;
import com.example.vprojetos.enums.Categoria;
import com.example.vprojetos.model.Projeto;
import com.example.vprojetos.model.ProjetoDAO;

import java.util.ArrayList;
import java.util.Arrays;

public class CadastroNovoProjetoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private static final int IMAGE_PICK_REQUEST_PERMISSION_CODE = 1000;
    private static final int PICK_IMAGE_REQUEST = 1001;

    private Spinner spinnerCategoria;
    private TextView categoriaTextView;
    private EditText nomeProjetoEditText, valorDesejadoEditText, descricaoProjetoEditText;
    private Button cadastroButton, escolheImagemCapaButton, adicionaImagemButton, mapsButton;
    private boolean imagemSelecionada = false;


    private String categoria;
    private String valorDesejadoString;
    private String nomeProjeto;
    private String descricaoProjeto;
    private double dinheiroAlvo;
    private Uri fileImageCapa;
    private ArrayList<Uri> arrayImagens;
    private boolean adicionaImagemSecundaria;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_novo_projeto);
        inicializa();

    }

    private void setAdapter() {
        String[] teste = Categoria.getAllEnums();
        ArrayAdapter<String> adapterEnum = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, teste);
        adapterEnum.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerCategoria.setAdapter(adapterEnum);
        spinnerCategoria.setOnItemSelectedListener(this);
    }

    private void inicializa() {
        spinnerCategoria = findViewById(R.id.idSpinnerCadastroNovoProjetoCategoria);
        categoriaTextView = findViewById(R.id.idTextViewCadastroNovoProjetoDincamicCategoria);
        descricaoProjetoEditText = findViewById(R.id.idEditTextCadastroNovoProjetoDescricaoProjeto);
        nomeProjetoEditText = findViewById(R.id.idEditTextCadastroNovoProjetoNomeProjeto);
        valorDesejadoEditText = findViewById(R.id.idEditTextCadastroNovoProjetoValorDesejado);
        cadastroButton = findViewById(R.id.idButtonCadastroNovoProjetoCadastrar);
        escolheImagemCapaButton = findViewById(R.id.idButtonCadastroNovoProjetoEscolheImagemCapa);
        adicionaImagemButton = findViewById(R.id.idButtonCadastroNovoProjetoAdicionaImagem);
        mapsButton = findViewById(R.id.idButtonGoogleMaps);


        escolheImagemCapaButton.setOnClickListener(this);
        cadastroButton.setOnClickListener(this);
        adicionaImagemButton.setOnClickListener(this);
        arrayImagens = new ArrayList<>();
        mapsButton.setOnClickListener(this);

        setAdapter();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Object itemAtPosition = adapterView.getItemAtPosition(i);
        categoriaTextView.setText(itemAtPosition.toString());

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void mensagem(Object s) {
        Toast.makeText(this, s.toString(), Toast.LENGTH_SHORT).show();
    }




    @Override
    public void onClick(View view) {
        if (view == cadastroButton) {
            if (validaCampos()) {
                Projeto projeto = new Projeto(nomeProjeto, Categoria.valueOf(categoria),
                                                dinheiroAlvo, descricaoProjeto);
                ProjetoDAO.saveProjeto(projeto, this, fileImageCapa, arrayImagens);

            } else {
                //Algum campo é nulo
                mensagem("Todos os campos são obrigatórios");

            }
        } else if (view == escolheImagemCapaButton) {
            adicionaImagemSecundaria = false;
            pedePermissao();
        } else if(view == adicionaImagemButton){
            adicionaImagemSecundaria = true;
            pedePermissao();
        }else if(view == mapsButton){

            Intent intent = new Intent(CadastroNovoProjetoActivity.this,MapsActivity.class);
            startActivity(intent);
        }

    }

    private void pedePermissao() {

        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            // Permissão foi concedida
            pickImage();

        } else {
            String[] permissoes = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permissoes, IMAGE_PICK_REQUEST_PERMISSION_CODE);
        }

    }

    private void pickImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if( !(resultCode == RESULT_OK ))
            return;

        switch (requestCode){
            case(PICK_IMAGE_REQUEST):

                if(!adicionaImagemSecundaria){
                imagemSelecionada = true;
                fileImageCapa = data.getData();
                }else{
                    Uri fileImagemSecundaria = data.getData();
                    arrayImagens.add(fileImagemSecundaria);
                }
                break;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == IMAGE_PICK_REQUEST_PERMISSION_CODE) {
            int index = new ArrayList<String>(Arrays.asList(permissions))
                    .indexOf(Manifest.permission.READ_EXTERNAL_STORAGE);

            if (grantResults[index] == PackageManager.PERMISSION_GRANTED) {
                pickImage();
            } else {
                mensagem("É necessário permissão para acessar as imagens");
            }
        }
    }

    private boolean validaCampos() {
        valorDesejadoString = valorDesejadoEditText.getText().toString().trim();
        nomeProjeto = nomeProjetoEditText.getText().toString().trim();
        descricaoProjeto = descricaoProjetoEditText.getText().toString().trim();
        categoria = categoriaTextView.getText().toString().trim();

        if (nomeProjeto.length() == 0 ||
                valorDesejadoString.length() == 0 ||
                descricaoProjeto.length() == 0 ||
                categoria.length() == 0 ||
                imagemSelecionada == false
        ) {
            // Algum valor é nulo
            return false;
        } else {
            dinheiroAlvo = Double.parseDouble(valorDesejadoString);
            return true;
        }
    }

}
