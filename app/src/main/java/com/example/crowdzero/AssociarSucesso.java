package com.example.crowdzero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AssociarSucesso extends AppCompatActivity {
    private Button BTN_VoltarPrincipal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_associar_sucesso);

        TextView TXT_NomeInstituicao_AssociarSucesso = findViewById(R.id.TXT_NomeInstituicao_AssociarSucesso);
        TXT_NomeInstituicao_AssociarSucesso.setText(getIntent().getExtras().getString("Nome"));

        BTN_VoltarPrincipal = findViewById(R.id.BTN_VoltarPrincipal);
        BTN_VoltarPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AssociarSucesso.this,MapView.class));
            }
        });

    }
}