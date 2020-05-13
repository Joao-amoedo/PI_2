package com.example.vprojetos.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.vprojetos.R;
import com.example.vprojetos.model.Usuario;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Log.i("uid", Usuario.usuario.getNome());
        Toast.makeText(this, Usuario.usuario.getNome(), Toast.LENGTH_LONG).show();


    }

    @Override
    protected void onResume() {
        super.onResume();



    }
}
