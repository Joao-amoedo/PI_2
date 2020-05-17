package com.example.vprojetos.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vprojetos.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class TesteImagemActivity extends AppCompatActivity implements View.OnClickListener {

    private StorageReference storageReference;
    private static final int PICK_IMAGE_REQUEST = 1000;
    private static final int PERMISSION_IMAGE_REQUEST = 1001;
    Button btnEscolhe;
    Button btnUpload;
    Uri filePath;
    ImageView imageViewTest;


    private void uploadFile() {

        if (filePath != null) {
            StorageReference riversRef = storageReference.child("images/zebra.png");
            final ProgressDialog progressDialog = new ProgressDialog(this);

            progressDialog.setTitle("Realizando upload");
            progressDialog.show();
            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            mensagem("deu bom");
                            // Get a URL to the uploaded content
                            //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            mensagem("deu ruim " + exception);
                            progressDialog.dismiss();
                            // Handle unsuccessful uploads
                            // ...
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    //double tempo = (taskSnapshot.getBytesTransferred() * 100 ) / taskSnapshot.getTotalByteCount();
                    progressDialog.setMessage("aguarde enquanto termina a transferencia");
                }
            });

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste_imagem);
        Intent intent = new Intent(this, CadastroNovoProjetoActivity.class);
        startActivity(intent);

        inicializa();

    }

    private void inicializa() {
        storageReference = FirebaseStorage.getInstance().getReference();
        btnEscolhe = findViewById(R.id.btnEscolhe);
        btnUpload = findViewById(R.id.btnUpload);
        imageViewTest = findViewById(R.id.imageViewTest23);

        btnUpload.setOnClickListener(this);
        btnEscolhe.setOnClickListener(this);

    }

    private void mensagem(String s) {
        Toast.makeText(TesteImagemActivity.this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        if (view == btnUpload) {
            // faz upload
            uploadFile();
        } else if (view == btnEscolhe) {

            verificaPermissaoEEscolheImagem();

        }
    }

    private void pedePermissao() {
        String[] permissoes = {Manifest.permission.READ_EXTERNAL_STORAGE};
        requestPermissions(permissoes, PERMISSION_IMAGE_REQUEST);
    }

    private void verificaPermissaoEEscolheImagem() {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            showFileChoser();
        } else {
            pedePermissao();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case (PERMISSION_IMAGE_REQUEST):
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showFileChoser();
                } else {
                    mensagem("Necessita de permissão para escolher imagens");
                }
                break;
        }


    }

    public void showFileChoser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select a image"), PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE_REQUEST
                && data != null && data.getData() != null) {
            filePath = data.getData();

            // TODO remover essa solução porca de bitmap
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageViewTest.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }
}

