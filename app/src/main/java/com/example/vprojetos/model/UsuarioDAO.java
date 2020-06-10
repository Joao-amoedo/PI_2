package com.example.vprojetos.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.vprojetos.config.Conexao;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class UsuarioDAO {
    public static Usuario usuario = Usuario.usuario;

    //Método estático que atualiza o email do usuário
    public static void updateEmail(Usuario usuario){
       getTree()
                .child("email")
                .setValue(usuario.getEmail());
    }

    //Método estático que atualiza o email do usuário corrente
    public static void updateEmail(){
        updateEmail(usuario);
    }

    //Método estático que atualiza o nome do usuário
    public static void updateNome(Usuario usuario){
        getTree()
                .child("nome")
                .setValue(usuario.getNome());
    }

    //Método estático que atualiza o nome do usuário corrente
    public static void updateNome(){
        updateNome(usuario);
    }

    //Método estático que atualiza o CPF do usuário
    public static void updateCPF(Usuario usuario){
        getTree()
                .child("cpf")
                .setValue(usuario.getCpf());
    }

    //Método estático que atualiza o CPF do usuário corrente
    public static void updateCPF(){
        updateCPF(usuario);
    }

    //Método estático com sobrecarga para salvar o usuário com o toMap
    public static void saveUsuario(Usuario usuario){
        getTree().setValue(usuario);
    }

    //Método estático com sobrecarga para salvar o usuário com o toMap
    public static void saveUsuario(){
        saveUsuario(usuario);
    }

    //Método estático que retorna o uid do usuário corrente
    private static String getUid() {
        return Conexao
                .getFirebaseAuth()
                .getCurrentUser()
                .getUid();
    }

    //Método estático que retorna o Database Reference de usuários com uid
    private static DatabaseReference getTree(){
        return   Conexao
                .getDatabase()
                .child("usuarios")
                .child(getUid());
    }


    public static void updateUltimoLogin() {
        getTree().child("ultimoLogin").setValue(Usuario.usuario.getUltimoLogin());
    }

    public static void updateDoacoes() {
        getTree().child("doacoes").setValue(Usuario.usuario.getDoacoes());
    }

    public static void updateNotas() {
        getTree().child("notas").setValue(Usuario.usuario.getNotas());

    }
}
