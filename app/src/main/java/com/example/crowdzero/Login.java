package com.example.crowdzero;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Login extends AppCompatActivity {
    public static ArrayList Dados_usuario = new ArrayList<Usuario>();


    //--------------------------------------------------------------------------------------------------------------------------------------------------
    @Override
    public void onBackPressed()
    {
    }

    //--------------------------------------------------------------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LembrarUser();
        setContentView(R.layout.activity_login);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);



    //--------------------------------------------------------------------------------------------------------------------------------------------------
        EditText inp_password = findViewById(R.id.INP_Password);
       /* inp_password.setOnTouchListener(new View.OnTouchListener(){
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (inp_password.getRight() - inp_password.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        Toast.makeText(Login.this,"Destruiu",Toast.LENGTH_SHORT).show();
                        inp_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        return true;
                    }
                }else{
                    inp_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);

                }

                return false;
            }
        });*/

    //--------------------------------------------------------------------------------------------------------------------------------------------------
        TextView btn_registrar = findViewById(R.id.BTN_Registrar);
        btn_registrar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) { startActivity(new Intent(Login.this,Registar.class)); }
        });

    //--------------------------------------------------------------------------------------------------------------------------------------------------
        EditText inp_email = findViewById(R.id.INP_Email);
        Button btn_confirmar = findViewById(R.id.BTN_Confirmar);

        btn_confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string_email = inp_email.getText().toString();
                String string_password = inp_password.getText().toString();

                if(!string_email.isEmpty() && !string_password.isEmpty()) {

                    FazerLogin(string_email,string_password);
                }else{
                    Toast.makeText(Login.this,getString(R.string.ERRO_EspacosEmBranco) ,Toast.LENGTH_SHORT).show();

                  /*  Usuario usuario = new Usuario();
                    usuario.setID(2);
                    usuario.setNome("Mario Antonio");
                    usuario.setNivel(10);
                    usuario.setConquistas(10);
                    usuario.setDivisao(2);
                    usuario.setPontos(200);
                    usuario.setReportes(30);
                    Dados_usuario.add(usuario);
                    startActivity(new Intent(Login.this,MapView.class));*/

                    //FazerLogin("maria@gmail.com","12345");

                }

            }
        });
    }

    //--------------------------------------------------------------------------------------------------------------------------------------------------

    public void FazerLogin(String Email,String Senha){
        Map<String,String> AUX_Map = new HashMap<String, String>();
        AUX_Map.put("email",Email);
        AUX_Map.put("password",Senha);

        Call<RespostaLogin> Server = ApiClient.getUserService().Login(AUX_Map);
        Server.enqueue(new Callback<RespostaLogin>() {
            @Override
            public void onResponse(Call<RespostaLogin> call, Response<RespostaLogin> response) {
                if(response.isSuccessful()) {

                    RespostaLogin respostaLogin = response.body();

                    if(respostaLogin.isSuccess()) {
                        Usuario usuario = new Usuario();

                        usuario.setID(Integer.parseInt(respostaLogin.getData().getId()));
                        usuario.setNome(respostaLogin.getData().getNome());
                        usuario.setNivel(Integer.parseInt(respostaLogin.getData().getNivel()));
                        usuario.setConquistas(Integer.parseInt(respostaLogin.getData().getConquistas()));
                        usuario.setDivisao(Integer.parseInt(respostaLogin.getData().getDivisao()));
                        usuario.setPontos(Integer.parseInt(respostaLogin.getData().getPontuacao()));
                        usuario.setReportes(Integer.parseInt(respostaLogin.getData().getReportes()));
                        usuario.setToken(respostaLogin.getToken());

                        Dados_usuario.add(usuario);
                        GuardarUser(Email,Senha);
                        startActivity(new Intent(Login.this, MapView.class));

                    }else{
                        Toast.makeText(Login.this,getString(R.string.ERRO_LoginSenha) ,Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(Login.this,getString(R.string.ERRO_LoginSenha) ,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RespostaLogin> call, Throwable t) {
                Toast.makeText(Login.this,getString(R.string.ERRO_Conexao) ,Toast.LENGTH_SHORT).show();
            }
        });

    }

    //--------------------------------------------------------------------------------------------------------------------------------------------------

    public void GuardarUser(String Email,String Senha){
        SharedPreferences preferences = getSharedPreferences("LembrarUser",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("Lembrar",true);
        editor.putString("Email",Email);
        editor.putString("Senha",Senha);
        editor.apply();
    }

     public void LembrarUser() {
        SharedPreferences preferences = getSharedPreferences("LembrarUser",MODE_PRIVATE);
        if(preferences.getBoolean("Lembrar",false)){
            String AUX_E = preferences.getString("Email","");
            String AUX_S = preferences.getString("Senha","");
            FazerLogin(AUX_E, AUX_S);
        }

    }

    //--------------------------------------------------------------------------------------------------------------------------------------------------
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    //--------------------------------------------------------------------------------------------------------------------------------------------------


}