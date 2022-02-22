package com.example.crowdzero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuConfiguracoes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_configuracoes);

        ImageView BTN_Voltar = findViewById(R.id.BTN_Voltar_Configuracoes);
        BTN_Voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuConfiguracoes.this, Perfil.class));
            }
        });

        TextView BTN_Logout = findViewById(R.id.TXT_Logout);
        BTN_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("LembrarUser",MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("Lembrar",false);
                editor.putString("Email","");
                editor.putString("Senha","");
                editor.apply();
                Login.Dados_usuario.clear();
                startActivity(new Intent(MenuConfiguracoes.this, Login.class));
            }
        });


    }
}