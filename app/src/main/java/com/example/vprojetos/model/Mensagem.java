package com.example.vprojetos.model;

import com.google.gson.annotations.SerializedName;

public class Mensagem {

    @SerializedName("text")
    private String texto;
    private int VIEW_TYPE;

    public Mensagem( String text, int VIEW_TYPE) {
        this.texto = text;
        this.VIEW_TYPE = VIEW_TYPE;
    }

    public String getTexto() {
        return texto;
    }

    public int getVIEW_TYPE(){return VIEW_TYPE;}

}

