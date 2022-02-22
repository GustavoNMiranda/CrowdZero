package com.example.crowdzero;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.crowdzero.models.FAKE_INSTITUICOES;
import com.example.crowdzero.models.Instituicao;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MinhasInstituicoes extends AppCompatActivity implements InstituicoesAdapter.OnInstituicaoListener {

    private InstituicoesAdapter instituicoesAdapter;
    private Button BTN_Solicitar;


    private ArrayList L_Instituicoes = MapView.ListaInstituicoes;

    @Override
    public void onBackPressed()
    {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minhas_instituicoes);


        if(!L_Instituicoes.isEmpty()) {
            instituicoesAdapter = new InstituicoesAdapter(L_Instituicoes,this);
            RecyclerView recyclerView = findViewById(R.id.RecyclerIntituicoes);
            recyclerView.setAdapter(instituicoesAdapter);
        }

        //--------------------------------------------------------------------------------------------------------------------------------------------------

        BTN_Solicitar = findViewById(R.id.BTN_SolicitarAssociacao);
        BTN_Solicitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MinhasInstituicoes.this,AssociarInfo.class));
            }
        });
        //--------------------------------------------------------------------------------------------------------------------------------------------------
        BottomNavigationView BottomNav = findViewById(R.id.BottomNavBarView);

        BottomNav.setSelectedItemId(R.id.M_Favoritos);

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

    @Override
    public void onInstituicaoClick(int position) {
        Intent intent = new Intent(MinhasInstituicoes.this,MapView.class);
        Instituicao AUX = (Instituicao) L_Instituicoes.get(position);
        intent.putExtra("instituicao", AUX);
        startActivity(intent);
    }
}


