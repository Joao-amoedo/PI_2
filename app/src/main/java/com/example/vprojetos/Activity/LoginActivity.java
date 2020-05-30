package com.example.vprojetos.Activity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vprojetos.R;
import com.example.vprojetos.config.Conexao;
import com.example.vprojetos.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    private EditText campoEmail, campoSenha;
    private Button botaoEntrar;
    private Usuario usuario;

    private FirebaseAuth autenticacao;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide(); //Esconde a Action Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        inicializa();


        listenerButton();

    }

    private void listenerButton() {
        botaoEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String textoEmail = campoEmail.getText().toString();
                String textoSenha = campoSenha.getText().toString();

                if (!textoEmail.isEmpty() & !textoSenha.isEmpty()) {
                    dialog.show();
                    validarLogin(textoEmail, textoSenha);
                } else {
                    mensagem("Preencha os dados");
                }
            }
        });


    }

    private void inicializa() {
        campoEmail = findViewById(R.id.idEditTextCadastroActivityEmail);
        campoSenha = findViewById(R.id.idEditTextCadastroActivitySenha);
        botaoEntrar = findViewById(R.id.buttonEntrar);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Aguarde");
        dialog.setMessage("Efetuando Login . . .");

    }

    private void mensagem(String s) {
        Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();
    }

    public void validarLogin(String email, String senha) {

        autenticacao = Conexao.getFirebaseAuth();
        autenticacao.signInWithEmailAndPassword(email, senha
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {


                    getUsuarioFirebase();


                } else {
                    dialog.dismiss();

                    String excecao = "";
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e) {
                        excecao = "Usuário não está cadastrado.";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        excecao = "E-mail e senha não correspondem a um usuário cadastrado";
                    } catch (Exception e) {
                        excecao = "Erro ao cadastrar usuário: " + e.getMessage();
                        e.printStackTrace();
                    }

                    mensagem(excecao);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
            }
        });

    }

    private void getUsuarioFirebase() {
        String uid = Conexao.getFirebaseAuth().getCurrentUser().getUid();
        final ProgressDialog dialog = new ProgressDialog(this);
        Conexao.getDatabase().child("usuarios").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario usuario = Usuario.usuario;

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                HashMap<String, Object> value = (HashMap<String, Object>) dataSnapshot.getValue();
                Log.i("progresso", "Cheguei aq");
                Usuario.usuario = new Usuario(value, LoginActivity.this);
                dialog.dismiss();
                startActivity(intent);
                finish();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                dialog.dismiss();
                Log.i("uid", "deu ruim");
            }
        });

    }


}
