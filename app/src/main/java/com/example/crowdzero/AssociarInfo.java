package com.example.crowdzero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class AssociarInfo extends AppCompatActivity {
    ImageView BTN_Voltar;
    Button BTN_Solicitar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_associar_info);

        BTN_Voltar = findViewById(R.id.BTN_Voltar_Associar);
        BTN_Voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AssociarInfo.this,MinhasInstituicoes.class));
            }
        });

        BTN_Solicitar = findViewById(R.id.BTN_Solicitar);
        BTN_Solicitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(AssociarInfo.this,AssociarCodigo.class));
            }
        });


    }
}