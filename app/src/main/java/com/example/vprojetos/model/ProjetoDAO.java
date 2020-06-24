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
import com.google.android.gms.tasks.OnSuccessListener;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProjetoDAO {
    private final boolean projetoExistente = false;
    private StorageReference storageReference;

    public static void saveProjeto(final Projeto projeto, final Activity activity,
                                   Uri fileImageCapaPath, List<Uri> imagensSecundarias) {
        verificaNomeEGravaNoBanco(projeto, activity, fileImageCapaPath, imagensSecundarias);

    }

    private static void verificaNomeEGravaNoBanco(final Projeto projeto,
                                                  final Activity activity,
                                                  final Uri fileImageCapaPath,
                                                  final List<Uri> imagensSecundarias) {
        Conexao
                .getDatabase()
                .child("projetos")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                        HashMap<String, Object> projetosBanco = (HashMap<String, Object>) dataSnapshot.getValue();

                        if(projetosBanco == null)
                            mensagem(activity, "campo ta super nulo");

                        if (projetosBanco != null && projetosBanco.containsKey(projeto.getNome())) {
                            // NOME JÁ EXISTE NO BANCO
                            mensagem(activity, "Projeto já existe com esse nome");
                        } else {
                            // NOME DO PROJETO NÃO EXISTE, CRIAR NOVO PROJETO NO BANCO
                            gravaImagemEProjetoNoBanco(dataSnapshot, activity, projeto, fileImageCapaPath, imagensSecundarias);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private static void gravaImagemEProjetoNoBanco(@NonNull final DataSnapshot dataSnapshot,
                                                   final Activity activity,
                                                   final Projeto projeto,
                                                   final Uri fileImageCapaPath,
                                                   final List<Uri> imagensSecundarias) {

        final StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle("Realizando Cadastro");
        progressDialog.setMessage("Aguarde enquanto termina o cadastro");
        progressDialog.show();

        Integer i = 1;
        final List<Integer> completos = new ArrayList<>();

        if (!imagensSecundarias.isEmpty()) {
            for (Uri imagemSecundaria : imagensSecundarias) {
                storageReference
                        .child("projetos")
                        .child(projeto.getNome())
                        .child(i.toString() + ".png")
                        .putFile(imagemSecundaria)
                        .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                completos.add(1);
                                if (completos.size() == imagensSecundarias.size()) {
                                    salvaImagemCapa(dataSnapshot, activity, projeto, fileImageCapaPath, storageReference, progressDialog);
                                }
                            }
                        });
                i++;

            }
        } else {
            salvaImagemCapa(dataSnapshot, activity, projeto, fileImageCapaPath, storageReference, progressDialog);
        }


    }

    private static void salvaImagemCapa(@NonNull final DataSnapshot dataSnapshot,
                                        final Activity activity,
                                        final Projeto projeto,
                                        Uri fileImageCapaPath,
                                        StorageReference storageReference,
                                        final ProgressDialog progressDialog) {
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
        Log.i("estou aq", "Estou aq");

        Usuario.usuario.addProjetoCriado(projeto.getNome());

        Conexao
                .getDatabase()
                .child("usuarios")
                .child(projeto.getUidAutor())
                .child("projetosCriados")
                .setValue(Usuario.usuario.getProjetosCriados());

        progressDialog.dismiss();
        mensagem(activity, "Projeto criado com sucesso");
        activity.finish();
    }

    public static void salvaImageCapa(Activity activity, Uri image, Projeto projeto) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
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

    public static void atualizaNotas(Projeto projeto) {
        Conexao
                .getDatabase()
                .child("projetos")
                .child(projeto.getNome())
                .child("notas")
                .setValue(projeto.getNotas());
    }

    public static void atualizaComentario(final Activity activity, Projeto projeto) {
        Conexao
                .getDatabase()
                .child("projetos")
                .child(projeto.getNome())
                .child("comentariosHash")
                .setValue(projeto.getComentariosHash())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        mensagem(activity, "Comentário adicionado com sucesso");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mensagem(activity, e.getMessage());
            }
        });
    }

    public static void atualizaProjeto(Projeto projeto) {
        Conexao
                .getDatabase()
                .child("projetos")
                .child(projeto.getNome())
                .setValue(projeto);

    }


}
