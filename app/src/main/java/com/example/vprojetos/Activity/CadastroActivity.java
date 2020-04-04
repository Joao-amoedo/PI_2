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
import com.example.vprojetos.model.UsuarioDAO;
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

        inicializa();

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

    private void inicializa() {
        campoNomeCompleto = findViewById(R.id.editNomeCompleto);
        campoCPF = findViewById(R.id.editCPF);
        campoEmail = findViewById(R.id.editEmail);
        campoSenha = findViewById(R.id.editSenha);
        campoConfirmarSenha = findViewById(R.id.editConfirmarSenha);
        botaoCadastrar = findViewById(R.id.buttonCadastrar);
    }


    public void cadastrarUsuario(final String email, String senha){

        autenticacao = Conexao.getFirebaseAuth();
        autenticacao.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        alert("Usuario Cadastrado com sucesso");
                        String uid = autenticacao.getCurrentUser().getUid();
                        salvaUsuario(uid);
                        alert("Usuario inserido com sucesso");
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
    }

    private void salvaUsuario(String uid) {
        Usuario usuario = Usuario.usuario;
        usuario.setEmail(campoEmail.getText().toString());
        usuario.setCpf(campoCPF.getText().toString());
        usuario.setNome(campoNomeCompleto.getText().toString());
        UsuarioDAO.saveUsuario();
    }


    public void alert(String mensagem){
        Toast.makeText(CadastroActivity.this, mensagem, Toast.LENGTH_SHORT).show();
    }

    public void abrirTelaLogin(){
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
