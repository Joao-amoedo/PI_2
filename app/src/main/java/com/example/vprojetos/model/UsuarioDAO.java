package com.example.vprojetos.model;

import com.example.vprojetos.config.Conexao;

public class UsuarioDAO {
    public static Usuario usuario = Usuario.usuario;

    //Método estático que atualiza o email do usuário
    public static void updateEmail(Usuario usuario){
        Conexao
                .getDatabase()
                .child(getUid())
                .child("email")
                .setValue(usuario.getEmail());
    }

    //Método estático que atualiza o email do usuário corrente
    public static void updateEmail(){
        updateEmail(usuario);
    }

    //Método estático que atualiza o nome do usuário
    public static void updateNome(Usuario usuario){
        Conexao
                .getDatabase()
                .child(getUid())
                .child("nome")
                .setValue(usuario.getNome());
    }

    //Método estático que atualiza o nome do usuário corrente
    public static void updateNome(){
        updateNome(usuario);
    }

    //Método estático que atualiza o CPF do usuário
    public static void updateCPF(Usuario usuario){
        Conexao
                .getDatabase()
                .child(getUid())
                .child("cpf")
                .setValue(usuario.getCpf());
    }

    //Método estático que atualiza o CPF do usuário corrente
    public static void updateCPF(){
        updateCPF(usuario);
    }

    //Método estático com sobrecarga para salvar o usuário com o toMap
    public static void saveUsuario(Usuario usuario){
        Conexao.getDatabase().child(getUid()).setValue(usuario.toMap());
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
}
