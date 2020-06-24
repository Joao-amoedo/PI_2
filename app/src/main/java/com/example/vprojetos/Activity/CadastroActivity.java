package com.example.vprojetos.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CadastroActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText campoNomeCompleto, campoEmail, campoSenha, campoConfirmarSenha;
    private Button botaoCadastrar;
    private FirebaseAuth autenticacao;
    private Usuario usuario;
    ProgressDialog dialog;
    TextView regiaoTextView;
    private final int CODE_MAPS = 999;
    private String pais = "";
    private String estado = "";
    private String dataCriacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        getSupportActionBar().hide(); //Esconde a Action Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        inicializa();


    }

    private void inicializa() {
        campoNomeCompleto = findViewById(R.id.idEditTextCadastroActivityNomeCompleto);
        campoEmail = findViewById(R.id.idEditTextCadastroActivityEmail);
        campoSenha = findViewById(R.id.idEditTextCadastroActivitySenha);
        campoConfirmarSenha = findViewById(R.id.idEditTextCadastroActivityConfirmarSenha);
        botaoCadastrar = findViewById(R.id.idButtonCadastroActivityCadastrar);
        regiaoTextView = findViewById(R.id.idTextViewCadastroActivityRegiao);
        regiaoTextView.setOnClickListener(this);
        botaoCadastrar.setOnClickListener(this);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Aguarde");
        dialog.setMessage("Realizando cadastro. . .");

    }


    public void cadastrarUsuario(final String email, String senha) {
        autenticacao = Conexao.getFirebaseAuth();
        autenticacao.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String uid = autenticacao.getCurrentUser().getUid();
                    salvaUsuario(uid);
                    dialog.dismiss();
                    alert("Usuario inserido com sucesso");
                    abrirTelaLogin();
                } else {
                    dialog.dismiss();
                    if (task.getException().getClass() == FirebaseAuthUserCollisionException.class) {
                        alert("Email já cadastrado");
                    } else {
                        alert("Não foi possível cadastrar");
                    }
                }
            }
        });
    }

    private void salvaUsuario(String uid) {
        Usuario usuario = Usuario.usuario;
        usuario.setEmail(campoEmail.getText().toString());
        usuario.setNome(campoNomeCompleto.getText().toString());
        usuario.setPais(pais);
        usuario.setEstado(estado);
        getDataCriacaoFormatada();
        usuario.setDataCriacao(dataCriacao);
        UsuarioDAO.saveUsuario();


    }


    public void alert(String mensagem) {
        Toast.makeText(CadastroActivity.this, mensagem, Toast.LENGTH_SHORT).show();
    }

    public void abrirTelaLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void onClick(View view) {
        if(view == regiaoTextView){
            Intent intent = new Intent(this, MapsActivity.class);
            startActivityForResult(intent, CODE_MAPS);
        }else if(view == botaoCadastrar){
            String nome = campoNomeCompleto.getText().toString();
            String email = campoEmail.getText().toString();
            String senha = campoSenha.getText().toString();
            String confirmarSenha = campoConfirmarSenha.getText().toString();

            //Validar se os campos foram preenchidos
            if (!nome.isEmpty() &
                    !email.isEmpty() &
                    !senha.isEmpty() &
                    !confirmarSenha.isEmpty() &
                    !pais.isEmpty() &
                    !estado.isEmpty()) {

                if (senha.equals(confirmarSenha)) {
                    dialog.show();
                    cadastrarUsuario(email, senha);
                } else
                    alert("As senhas não são iguais");
            } else
                alert("Preencha todos os campos");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode != RESULT_OK)
            return;

        switch (requestCode){
            case(CODE_MAPS):
                Bundle extras = data.getExtras();

                pais = (String) extras.get("pais");
                estado = (String) extras.get("estado");

                regiaoTextView.setText(pais + " - " + estado);
                break;
        }

    }

    private void getDataCriacaoFormatada(){
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        long ultimoLogin = new Date().getTime();
        dataCriacao = formatador.format(ultimoLogin);
    }

}
