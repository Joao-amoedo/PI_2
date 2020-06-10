package com.example.vprojetos.Activity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vprojetos.R;
import com.example.vprojetos.config.Conexao;
import com.example.vprojetos.model.Usuario;
import com.example.vprojetos.model.UsuarioDAO;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class LoginActivity extends AppCompatActivity {

    private EditText campoEmail, campoSenha;
    private TextView campoCadastrar;
    private Button botaoEntrar;
    private Usuario usuario;
    private FirebaseAuth autenticacao;
    private TextView recuperarSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Método que oculta a Action Bar
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        inicializa();

        listenerButton();

        recuperarSenha = findViewById(R.id.textView);

        recuperarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RecuperarSenhaActivity.class);
                startActivity(intent);
            }
        });

        campoCadastrar = findViewById(R.id.idcadastrar);
        campoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirTelaCadastro();
            }
        });
    }

    private void listenerButton() {
        botaoEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String textoEmail = campoEmail.getText().toString();
                String textoSenha = campoSenha.getText().toString();

                if (!textoEmail.isEmpty() & !textoSenha.isEmpty()) {
                    validarLogin(textoEmail, textoSenha);
                } else {
                    mensagem("Preencha os dados");
                }
            }
        });


    }

    private void inicializa() {
        campoEmail = findViewById(R.id.editEmail);
        campoSenha = findViewById(R.id.editSenha);
        botaoEntrar = findViewById(R.id.buttonEntrar);

    }

    private void mensagem(String s) {
        Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();
    }

    public void validarLogin(String email, String senha){

        autenticacao = Conexao.getFirebaseAuth();
        autenticacao.signInWithEmailAndPassword(email, senha
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if ( task.isSuccessful() ){

                    mensagem("Bem-vindo");
                    abrirTelaPrincipal();
                }else {

                    String excecao = "";
                    try {
                        throw task.getException();
                    }catch ( FirebaseAuthInvalidUserException e ) {
                        excecao = "Usuário não está cadastrado.";
                    }catch ( FirebaseAuthInvalidCredentialsException e ){
                        excecao = "E-mail e senha não correspondem a um usuário cadastrado";
                    }catch (Exception e){
                        excecao = "Erro ao cadastrar usuário: "  + e.getMessage();
                        e.printStackTrace();
                    }

                    mensagem(excecao);
                }
            }
        });

    }

    public void abrirTelaPrincipal(){
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public void abrirTelaCadastro(){
        startActivity(new Intent(this, CadastroActivity.class));
        finish();
    }

}

