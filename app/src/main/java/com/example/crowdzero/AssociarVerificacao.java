package com.example.crowdzero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.crowdzero.models.Usuario;
import com.example.crowdzero.server.ApiClient;
import com.example.crowdzero.server.DataInstiuicao;
import com.example.crowdzero.server.RespostaAssociar;
import com.example.crowdzero.server.RespostaLogin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssociarVerificacao extends AppCompatActivity {

    private ImageView BTN_Voltar;
    private Button BTN_Confirmar;
    private Button BTN_Tentar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_associar_verificacao);

        TextView TXT_AssociarNomeInstituicao = findViewById(R.id.TXT_AssociarNomeInstituicao);
        TextView TXT_NAssociados = findViewById(R.id.TXT_NAssociados);
        TextView TXT_NEspacos = findViewById(R.id.TXT_NEspacos);
        TextView TXT_Enderecos2 = findViewById(R.id.TXT_Enderecos2);
        TextView TXT_NumTelefone = findViewById(R.id.TXT_NumTelefone);

        Geocoder geo = MapView.geocoder;
        try {
            List<Address> addresses = geo.getFromLocation(
                    Double.parseDouble(getIntent().getExtras().getString("Lat")),
                    Double.parseDouble(getIntent().getExtras().getString("Lon")),
                    1);
            TXT_Enderecos2.setText(addresses.get(0).getAddressLine(0));

        } catch (IOException e) {
            TXT_Enderecos2.setText("");

        }

        TXT_AssociarNomeInstituicao.setText(getIntent().getExtras().getString("Nome"));
        TXT_NAssociados.setText(getIntent().getExtras().getString("N_Associados"));
        TXT_NEspacos.setText(getIntent().getExtras().getString("N_espacos"));
        TXT_NumTelefone.setText(getIntent().getExtras().getString("Telefone"));



        BTN_Voltar = findViewById(R.id.BTN_Voltar_Verificacao);
        BTN_Voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AssociarVerificacao.this,AssociarCodigo.class));
            }
        });

        BTN_Confirmar = findViewById(R.id.BTN_Confirmar_Associar);
        BTN_Confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList usuario = Login.Dados_usuario;
                Usuario AUX_Usuario = (Usuario) usuario.get(0);

                Map<String, String> AUX_Map = new HashMap<String, String>();
                AUX_Map.put("id", AUX_Usuario.getID().toString());
                AUX_Map.put("token_access", getIntent().getExtras().getString("token"));

                Call<RespostaAssociar> Server = ApiClient.getUserService().Associar(AUX_Map);
                Server.enqueue(new Callback<RespostaAssociar>() {
                    @Override
                    public void onResponse(Call<RespostaAssociar> call, Response<RespostaAssociar> response) {
                        if(response.isSuccessful()){
                            RespostaAssociar respostaAssociar = response.body();
                            if(respostaAssociar.isSuccess()){
                                Intent intent = new Intent(AssociarVerificacao.this,AssociarSucesso.class);
                                intent.putExtra("Nome", getIntent().getExtras().getString("Nome"));
                                startActivity(intent);
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<RespostaAssociar> call, Throwable t) {
                    }
                });
            }
        });

        BTN_Tentar = findViewById(R.id.BTN_Tentar);
        BTN_Tentar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AssociarVerificacao.this,AssociarCodigo.class));
            }
        });

    }
}