package com.example.crowdzero;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.crowdzero.models.Conquista;
import com.example.crowdzero.models.Usuario;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import com.example.crowdzero.models.ListaConquistas;

public class Perfil extends AppCompatActivity {
    private ConquistasAdapter conquistasAdapter;

    @Override
    public void onBackPressed()
    {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        ImageView BTN_Configuracoes;

        ConquitasManager conquitasManager =new ConquitasManager();
        conquitasManager.Set_AllConquistas(new ArrayList<>(ListaConquistas.LConquistas()));
        conquitasManager.Set_Context(this);
        ArrayList<Conquista> L_Conquistas = conquitasManager.GetConquistas();


        conquistasAdapter = new ConquistasAdapter(L_Conquistas);
        RecyclerView recyclerView = findViewById(R.id.RecyclerConquistas);
        recyclerView.setAdapter(conquistasAdapter);
        //--------------------------------------------------------------------------------------------------------------------------------------------------

        TextView TXT_Nome = findViewById(R.id.TXT_Nome);
        TextView TXT_Pontos = findViewById(R.id.TXT_NPontos);
        TextView TXT_Reportes = findViewById(R.id.TXT_NReportes);
        TextView TXT_Conquistas = findViewById(R.id.TXT_NConquistas);
        TextView TXT_Nivel = findViewById(R.id.TXT_Nivel);
        ImageView IMG_Perfil = findViewById(R.id.IMG_Perfil);


        ArrayList usuario = Login.Dados_usuario;
        Usuario AUX_Usuario = (Usuario) usuario.get(0);

        TXT_Nome.setText(AUX_Usuario.getNome());
        TXT_Nivel.setText(TXT_Nivel.getText() + " " + AUX_Usuario.getNivel());
        TXT_Pontos.setText(AUX_Usuario.getPontos().toString());
        TXT_Reportes.setText(AUX_Usuario.getReportes().toString());
        TXT_Conquistas.setText(AUX_Usuario.getConquistas().toString());


        switch(AUX_Usuario.getDivisao()){
            case 1:
                IMG_Perfil.setImageDrawable(getResources().getDrawable(R.drawable.avatar_bronze));
                break;
            case 2:
                IMG_Perfil.setImageDrawable(getResources().getDrawable(R.drawable.avatar_silver));
                break;
            case 3:
                IMG_Perfil.setImageDrawable(getResources().getDrawable(R.drawable.avatar_gold));
                break;
            default:
        }


        //--------------------------------------------------------------------------------------------------------------------------------------------------
        BTN_Configuracoes = findViewById(R.id.BTN_Configuracoes);
        BTN_Configuracoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Perfil.this,MenuConfiguracoes.class));
            }
        });

        //--------------------------------------------------------------------------------------------------------------------------------------------------
        BottomNavigationView BottomNav = findViewById(R.id.BottomNavBarView);

        BottomNav.setSelectedItemId(R.id.M_Perfil);

        BottomNav.setOnNavigationItemSelectedListener (new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.M_MapView:
                        startActivity(new Intent(getApplicationContext(),MapView.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.M_Favoritos:
                        startActivity(new Intent(getApplicationContext(), MinhasInstituicoes.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.M_Estatisticas:
                        startActivity(new Intent(getApplicationContext(),Estatisticas.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.M_Perfil:
                        startActivity(new Intent(getApplicationContext(),Perfil.class));
                        overridePendingTransition(0,0);
                        break;
                }
                return true;
            }
        });
        //--------------------------------------------------------------------------------------------------------------------------------------------------
    }
}