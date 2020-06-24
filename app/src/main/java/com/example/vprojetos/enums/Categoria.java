package com.example.vprojetos.enums;

import java.util.ArrayList;
import java.util.Arrays;

public enum Categoria {

    Jogos, Filmes, Teatro, Tecnologia, Musica, Curso, Alimentação, Moda, Outros;

    public static String[] getAllEnums(){
        // Retorna todos os ENUMs em um Array de string
        String[] categoriasStr = new String[Categoria.values().length];
        int contador = 0;

        for (Categoria categoria: Categoria.values()) {
            String s = categoria.toString();
            categoriasStr[contador++] = s;
        }
        return categoriasStr;
    }

}
