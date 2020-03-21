package com.example.vprojetos.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vprojetos.R;
import com.example.vprojetos.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.example.vprojetos.config.ConfiguracaoFirebase;

public class CadastroActivity extends AppCompatActivity {

    private EditText campoNome, campoSobrenome, campoEmail, campoSenha, campoConfirmarSenha;
    private Button botaoCadastrar;
    private FirebaseAuth autenticacao;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        getSupportActionBar().hide(); //Esconde a Action Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        campoNome = findViewById(R.id.editNome);
        campoSobrenome = findViewById(R.id.editSobrenome);
        campoEmail = findViewById(R.id.editEmail);
        campoSenha = findViewById(R.id.editSenha);
        campoConfirmarSenha = findViewById(R.id.editConfirmarSenha);
        botaoCadastrar = findViewById(R.id.buttonCadastrar);

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textoNome = campoNome.getText().toString();
                String textoSobrenome = campoSobrenome.getText().toString();
                String textoEmail = campoEmail.getText().toString();
                String textoSenha = campoSenha.getText().toString();
                String textoConfirmarSenha = campoConfirmarSenha.getText().toString();

                //Validar se os campos foram preenchidos
                if( !textoNome.isEmpty() ){
                    if( !textoSobrenome.isEmpty() ){
                        if( !textoEmail.isEmpty()){
                            if( !textoSenha.isEmpty()){
                                if( !textoConfirmarSenha.isEmpty()){

                                    usuario = new Usuario();
                                    usuario.setNome(textoNome);
                                    usuario.setSobrenome(textoSobrenome);
                                    usuario.setEmail(textoEmail);
                                    usuario.setSenha(textoSenha);
                                    usuario.setConfirmarSenha(textoConfirmarSenha);
                                    cadastrarUsuario();

                                }else{
                                    Toast.makeText(CadastroActivity.this, "Confirme a Senha!", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(CadastroActivity.this, "Digite a Senha!", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(CadastroActivity.this, "Preencha o Email!", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(CadastroActivity.this, "Preencha o Sobrenome!", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(CadastroActivity.this, "Preencha o nome!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void cadastrarUsuario(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(CadastroActivity.this, "Sucesso ao cadastrar usuário!", Toast.LENGTH_SHORT).show();
                }else{


                    Toast.makeText(CadastroActivity.this, "Erro ao cadastrar usuário!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
