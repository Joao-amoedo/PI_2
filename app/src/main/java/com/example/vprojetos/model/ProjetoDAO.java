package com.example.vprojetos.model;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.vprojetos.Activity.MainActivity;
import com.example.vprojetos.config.Conexao;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import android.net.Uri;
import java.util.HashMap;

public class ProjetoDAO {
    private final boolean projetoExistente = false;
    private StorageReference storageReference;
    public static void saveProjeto(final Projeto projeto, final Activity activity, Uri fileImageCapaPath){
        verificaNomeEGravaNoBanco(projeto, activity, fileImageCapaPath);

    }

    private static void verificaNomeEGravaNoBanco(final Projeto projeto, final Activity activity, final Uri fileImageCapaPath) {
        Conexao.getDatabase().child("projetos").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                HashMap<String, Object> projetosBanco = (HashMap<String, Object>) dataSnapshot.getValue();


                if(projetosBanco.containsKey(projeto.getNome())){
                    // NOME JÁ EXISTE NO BANCO
                    mensagem(activity, "Projeto já existe com esse nome");
                }else{
                    // NOME DO PROJETO NÃO EXISTE, CRIAR NOVO PROJETO NO BANCO

                    gravaImagemEProjetoNoBanco(dataSnapshot, projeto, fileImageCapaPath, activity);


                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private static void gravaImagemEProjetoNoBanco(@NonNull final DataSnapshot dataSnapshot, final Projeto projeto, Uri fileImageCapaPath, final Activity activity) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle("Realizando Cadastro");
        progressDialog.show();
        storageReference
                .child("projetos")
                .child(projeto.getNome()).child("capa.png")
                .putFile(fileImageCapaPath)
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                // Terminou de inserir a imagem

                gravaProjetoNoBanco(dataSnapshot, projeto, activity, progressDialog);

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                progressDialog.setMessage("Aguarde enquanto termina o cadastro");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mensagem(activity, "Ocorreu um erro inexperado " + e.getMessage());
                progressDialog.dismiss();
            }
        })

        ;
    }

    private static void gravaProjetoNoBanco(@NonNull DataSnapshot dataSnapshot, Projeto projeto, Activity activity, ProgressDialog progressDialog) {
        dataSnapshot.child(projeto.getNome()).getRef().setValue(projeto);
        Usuario.usuario.addProjetoCriado(projeto.getNome());
        Log.i("estou aq", "Estou aq");
        UsuarioDAO.saveUsuario();

        progressDialog.dismiss();
        mensagem(activity, "Projeto criado com sucesso");
    }

    public static void salvaImageCapa(Activity activity, Uri image, Projeto projeto){
        StorageReference storageReference  = FirebaseStorage.getInstance().getReference();
        storageReference
                .child("projetos")
                .child(projeto.getNome()).child("capa.png")
                .putFile(image);

    }

    private static void mensagem(Activity activity, String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_LONG).show();
    }


    private static DatabaseReference getTree(final String nome) {

        return Conexao
                .getDatabase()
                .child("projetos")
                .child(nome);
    }

}
