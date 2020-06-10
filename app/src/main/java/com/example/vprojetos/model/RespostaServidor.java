package com.example.vprojetos.model;

import com.google.gson.annotations.SerializedName;

public class RespostaServidor {

    @SerializedName("message")
    private String texto ;

    public String getTexto(){
        return texto;
    }

}