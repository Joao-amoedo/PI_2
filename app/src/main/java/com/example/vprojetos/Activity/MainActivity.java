package com.example.vprojetos.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.vprojetos.R;
import com.google.android.material.navigation.NavigationView;
public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Configura barra de navegação
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Cria referencia para a área de NavigationDrawer
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        //Cria referencia para a área de navegação
        NavigationView menuNavegacao = findViewById(R.id.menunav_view);

        //Define configurações do NavigationDrawer
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_informacao,
                R.id.nav_perfil, R.id.nav_config, R.id.nav_sair)
                .setDrawerLayout(drawer)
                .build();
        //Configura a area que irá carregar os fragments
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        //Configura menu superior de navegacao
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);

        //Configura a navegação para NavigationView
        NavigationUI.setupWithNavController(menuNavegacao, navController);
    }
    @Override
    //Método que habilita a opção de navegação
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
