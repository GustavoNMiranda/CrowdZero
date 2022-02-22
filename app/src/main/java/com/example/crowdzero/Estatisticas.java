package com.example.crowdzero;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.crowdzero.server.DataRank;
import com.example.crowdzero.server.ApiClient;
import com.example.crowdzero.server.RespostaRank;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Estatisticas extends AppCompatActivity {

    private RankingAdapter rankingAdapter;

    @Override
    public void onBackPressed()
    {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estatisticas);

        Call<RespostaRank> Server = ApiClient.getUserService().GetRank();
        Server.enqueue(new Callback<RespostaRank>() {
            @Override
            public void onResponse(Call<RespostaRank> call, Response<RespostaRank> response) {
                if(response.isSuccessful()){
                    RespostaRank respostaRank = response.body();
                    if(respostaRank.isSuccess()){

                        rankingAdapter = new RankingAdapter(respostaRank.getData());
                        RecyclerView recyclerView = findViewById(R.id.RecyclerRank);
                        recyclerView.setAdapter(rankingAdapter);
                    }

                }
            }

            @Override
            public void onFailure(Call<RespostaRank> call, Throwable t) {

            }
        });








        //--------------------------------------------------------------------------------------------------------------------------------------------------
        BottomNavigationView BottomNav = findViewById(R.id.BottomNavBarView);

        BottomNav.setSelectedItemId(R.id.M_Estatisticas);

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