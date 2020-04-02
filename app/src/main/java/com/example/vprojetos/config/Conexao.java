package com.example.vprojetos.config;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Conexao {
    private static FirebaseAuth firebaseAuth;
    private static FirebaseAuth.AuthStateListener authStateListener;
    private static FirebaseUser firebaseUser;


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

    public static FirebaseUser getFirebaseUser(){
        return firebaseUser;
    }

    public static void logoff(){
        firebaseAuth.signOut();
    }


}
