package com.example.crowdzero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.crowdzero.models.Instituicao;

public class ComoReportar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_como_reportar);

        ImageView BTN_Voltar = findViewById(R.id.BTN_Voltar_ComoReportar);

        BTN_Voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ComoReportar.this,MapView.class);
                Instituicao AUX = getIntent().getExtras().getParcelable("instituicao");;
                intent.putExtra("instituicao", AUX);
                startActivity(intent);
            }
        });

    }
}