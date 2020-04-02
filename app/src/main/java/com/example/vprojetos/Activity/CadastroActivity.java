package com.example.vprojetos.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vprojetos.R;
import com.example.vprojetos.config.Conexao;
import com.example.vprojetos.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class CadastroActivity extends AppCompatActivity {

    private EditText campoNomeCompleto, campoCPF, campoEmail, campoSenha, campoConfirmarSenha;
    private Button botaoCadastrar;
    private FirebaseAuth autenticacao;
    private Usuario usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        getSupportActionBar().hide(); //Esconde a Action Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        campoNomeCompleto = findViewById(R.id.editNomeCompleto);
        campoCPF = findViewById(R.id.editCPF);
        campoEmail = findViewById(R.id.editEmail);
        campoSenha = findViewById(R.id.editSenha);
        campoConfirmarSenha = findViewById(R.id.editConfirmarSenha);
        botaoCadastrar = findViewById(R.id.buttonCadastrar);

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = campoNomeCompleto.getText().toString();
                String cpf = campoCPF.getText().toString();
                String email = campoEmail.getText().toString();
                String senha = campoSenha.getText().toString();
                String confirmarSenha = campoConfirmarSenha.getText().toString();

                //Validar se os campos foram preenchidos
                if (!nome.isEmpty() & !cpf.isEmpty() &
                    !email.isEmpty() & !senha.isEmpty() & !confirmarSenha.isEmpty()){
                    if(senha.equals(confirmarSenha)){
                        cadastrarUsuario(email, senha);
                    }else
                        alert("As senhas não são iguais");
                }else
                    alert("Preencha todos os campos");

            }
        });
    }



    public void cadastrarUsuario(String email, String senha){

        autenticacao = Conexao.getFirebaseAuth();
        autenticacao.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        alert("Usuario Cadastrado com sucesso");
                        abrirTelaLogin();
                    } else {
                        if(task.getException().getClass() == FirebaseAuthUserCollisionException.class){
                            alert("Email já cadastrado");
                        }else{
                            alert("Não foi possível cadastrar");
                        }

                    }

                }

            });




/*
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        mensagem("Cheguei aq   "+  autenticacao.toString());
        autenticacao.createUserWithEmailAndPassword(usuario.getEmail(), senha
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    mensagem("Usuário Cadastrado com Sucesso");
                    abrirTelaLogin();

                }else{
                    String excecao = " ";
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        excecao = "Digite uma senha mais forte!";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        excecao = "Por favor, digite uma e-mail valido!";
                    }catch (FirebaseAuthUserCollisionException e){
                        excecao = "Esta conta já foi cadastrada";
                    }catch (Exception e){
                        excecao = "Erro ao cadastrar usuário: " + e.getMessage();
                        e.printStackTrace();
                    }

                    mensagem(excecao);
                }
            }
        });*/
    }

    public void alert(String mensagem){
        Toast.makeText(CadastroActivity.this, mensagem, Toast.LENGTH_SHORT).show();
    }

    public void abrirTelaLogin(){
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
