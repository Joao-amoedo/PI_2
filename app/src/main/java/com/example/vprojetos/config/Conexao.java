package com.example.vprojetos.config;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Conexao {
    private static FirebaseAuth firebaseAuth;
    private static FirebaseAuth.AuthStateListener authStateListener;
    private static FirebaseUser firebaseUser;
    private static DatabaseReference database;
    private static FirebaseDatabase firebaseDatabase;


    public static FirebaseAuth getFirebaseAuth(){
        if (firebaseAuth == null){
            inicializaFirebase();
        }
        return firebaseAuth;
    }

    public static void inicializaFirebase(){

        firebaseAuth = FirebaseAuth.getInstance();

        if (authStateListener == null){
            authStateListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if(user != null){
                        firebaseUser = user;
                    }
                }
            };
            firebaseAuth.addAuthStateListener(authStateListener);
        }
    }

    public static DatabaseReference getDatabase(){
        if(database == null){
            Conexao.inicializaFirebase();
            Conexao.inicializaFirebaseDatabase();
            database = firebaseDatabase.getReference();
        }
        return database;
    }

    private static void inicializaFirebaseDatabase() {
        if(firebaseDatabase == null){
            Conexao.inicializaFirebase();
            firebaseDatabase = FirebaseDatabase.getInstance();
        }
    }

    public static FirebaseUser getFirebaseUser(){
        if(firebaseAuth == null){
            inicializaFirebase();
        }

        if(firebaseUser == null){
            firebaseUser = firebaseAuth.getCurrentUser();
        }

        return firebaseUser;
    }

    public static void logoff(){
        firebaseAuth.signOut();
    }


}
