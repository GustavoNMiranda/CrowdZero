package com.example.crowdzero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crowdzero.models.Usuario;
import com.example.crowdzero.server.ApiClient;
import com.example.crowdzero.server.RespostaLogin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registar);

        ImageButton btn_voltar = findViewById(R.id.BTN_Voltar_Registar);
        btn_voltar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) { startActivity(new Intent(Registar.this,Login.class)); }
        });

        TextView BTN_VoltarLogin = findViewById(R.id.BTN_Login_R);
        BTN_VoltarLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Registar.this,Login.class));
            }
        });


        EditText INP_Nome_R = findViewById(R.id.INP_Nome_R);
        EditText INP_Email_R = findViewById(R.id.INP_Email_R);
        EditText INP_Password_R = findViewById(R.id.INP_Password_R);
        EditText INP_Password2_R = findViewById(R.id.INP_Password2_R);


       TextView btn_login = findViewById(R.id.BTN_Registar_R);
       btn_login.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v) {

               String S_Nome = INP_Nome_R.getText().toString();
               String S_Email = INP_Email_R.getText().toString();
               String S_Password = INP_Password_R.getText().toString();
               String S_Password2 = INP_Password2_R.getText().toString();

               if(!S_Nome.isEmpty() || !S_Email.isEmpty() || !S_Password.isEmpty() || !S_Password2.isEmpty()){
                   if(S_Password.equals(S_Password2)) {
                       if(S_Password.length() >= 8) {

                           Map<String, String> AUX_Map = new HashMap<String, String>();
                           AUX_Map.put("nome", S_Nome);
                           AUX_Map.put("email", S_Email);
                           AUX_Map.put("password", S_Password);

                           Call<RespostaLogin> Server = ApiClient.getUserService().Registrar(AUX_Map);
                           Server.enqueue(new Callback<RespostaLogin>() {
                               @Override
                               public void onResponse(Call<RespostaLogin> call, Response<RespostaLogin> response) {
                                   if (response.isSuccessful()) {
                                     RespostaLogin respostaLogin = response.body();
                                       if(respostaLogin.isSuccess()){
                                           startActivity(new Intent(Registar.this, Login.class));
                                       }else{
                                           Toast.makeText(Registar.this, getString(R.string.ERRO_JaExiste), Toast.LENGTH_SHORT).show();
                                       }
                                   } else {
                                       Toast.makeText(Registar.this, getString(R.string.ERRO_Conexao), Toast.LENGTH_SHORT).show();
                                   }
                               }
                               @Override
                               public void onFailure(Call<RespostaLogin> call, Throwable t) {
                                   Toast.makeText(Registar.this, getString(R.string.ERRO_Conexao), Toast.LENGTH_SHORT).show();
                               }
                           });
                       }else{
                           Toast.makeText(Registar.this,getString(R.string.ERRO_SenhaCurta) ,Toast.LENGTH_SHORT).show();
                       }
                   }else{
                       Toast.makeText(Registar.this,getString(R.string.ERRO_SenhasDiferentes) ,Toast.LENGTH_SHORT).show();
                   }

               }else{
                   Toast.makeText(Registar.this,getString(R.string.ERRO_EspacosEmBranco) ,Toast.LENGTH_SHORT).show();
               }
           }
       });


    }


    public void GuardarUser(String Email,String Senha){
        SharedPreferences preferences = getSharedPreferences("LembrarUser",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("Lembrar",true);
        editor.putString("Email",Email);
        editor.putString("Senha",Senha);
        editor.apply();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}