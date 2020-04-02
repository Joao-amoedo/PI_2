package com.example.vprojetos.model;

import com.google.firebase.auth.FirebaseAuth;
import java.util.HashMap;
class Projetos {

    private double dinheiroArrecadado;

    private HashMap<String, Double> usuarios = new HashMap<String, Double>();

    public void teste(){

        usuarios.put("Tp4Fjj1sZ3REDy8EXu9sS4Cl9v03", 33.0);
    }



    public void recebeDoacao(double novoValor){
        String uid = FirebaseAuth.getInstance().getUid();

        if (usuarios.containsKey(uid)){
            Double valorDoadoDoUsuario = usuarios.get(uid);
            usuarios.put(uid, novoValor + valorDoadoDoUsuario);
        }else {
            usuarios.put(uid, novoValor);
        }
    }
}
