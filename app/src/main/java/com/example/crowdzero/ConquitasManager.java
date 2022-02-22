package com.example.crowdzero;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.crowdzero.models.Conquista;
import com.example.crowdzero.models.Instituicao;
import com.example.crowdzero.models.Usuario;
import com.example.crowdzero.server.ApiClient;
import com.example.crowdzero.server.RespostaConquista;
import com.example.crowdzero.server.RespostaLogin;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class ConquitasManager {
    ArrayList<Conquista> L_AllConquistas =  new ArrayList<Conquista>();
    ArrayList<Conquista> L_Conquistas = new ArrayList<Conquista>();
    ArrayList<Integer> L_Num = new ArrayList<Integer>();
    ArrayList<Boolean> L_Boo = new ArrayList<Boolean>();

    public int N_Reportes;
    Context context;

    public void Reporte(){
        GetConquistas();
        N_Reportes = N_Reportes + 1;
        SaveConquistas();

            for (int X = 0; X < L_Conquistas.size(); ++X) {


                if (Integer.parseInt(L_Conquistas.get(X).getNome()) <= N_Reportes) {
                  if (!L_Conquistas.get(X).isFeito()) {
                      L_Boo.set(X, true);
                      ArrayList usuario = Login.Dados_usuario;
                      Usuario AUX_Usuario = (Usuario) usuario.get(0);
                      AUX_Usuario.setPontos(AUX_Usuario.getPontos() + Integer.parseInt(L_Conquistas.get(X).getNPontos()));
                      AUX_Usuario.setConquistas(AUX_Usuario.getConquistas() + 1);
                      SeverCall(AUX_Usuario.getID().toString(), L_Conquistas.get(X).getNPontos());
                      SaveConquistas();
                  }
                }


            }
    }

    public ArrayList<Conquista> GetConquistas(){
        L_Conquistas.clear();
        L_Num.clear();
        L_Boo.clear();

        boolean help = LembrarUser();

        if(!help){
            GerarConquistas();
        }else{
            for (int Y = 0; Y < 3; ++Y) {
            Conquista AUX_Conquista = L_AllConquistas.get(L_Num.get(Y));
            AUX_Conquista.setFeito(L_Boo.get(Y));
            if(!AUX_Conquista.isFeito()){
                L_Conquistas.add(AUX_Conquista);
            }
            }
        }


        return L_Conquistas;
    }

    public void SeverCall(String associado, String pontuacao){
        Map<String,String> AUX_Map = new HashMap<String, String>();
        AUX_Map.put("associado",associado);
        AUX_Map.put("pontuacao",pontuacao);

        Call<RespostaConquista> Server = ApiClient.getUserService().AddConquista(AUX_Map);
        Server.enqueue(new Callback<RespostaConquista>() {
            @Override
            public void onResponse(Call<RespostaConquista> call, Response<RespostaConquista> response) {
                if(response.isSuccessful()) {
                    RespostaConquista respostaConquista = response.body();
                    ArrayList usuario = Login.Dados_usuario;
                    Usuario AUX_Usuario = (Usuario) usuario.get(0);
                    AUX_Usuario.setNivel(Integer.parseInt(respostaConquista.getData().getNivel()));
                    AUX_Usuario.setDivisao(Integer.parseInt(respostaConquista.getData().getDivisao()));

                }
            }

            @Override
            public void onFailure(Call<RespostaConquista> call, Throwable t) {

            }
        });
    }


    public void GerarConquistas() {
        int random1 = -1;
        ArrayList<Integer> AUX_Index = new ArrayList<Integer>();
        for (int Y = 0; Y < 3; ++Y) {
            do{
                random1 = new Random().nextInt(L_AllConquistas.size());
            }while(AUX_Index.contains(random1));
            AUX_Index.add(random1);
        }

        Collections.sort(AUX_Index,Collections.reverseOrder());
        for(int Y = 0; Y < 3; ++Y){

            AUX_Index.get(Y);
            L_Conquistas.add(L_AllConquistas.get( AUX_Index.get(Y)));
            L_Boo.add(false);
            N_Reportes = 0;
            L_Num.add( AUX_Index.get(Y));
        }
        AUX_Index.clear();
        SaveConquistas();
    }



    //************************* Guardar Lembrar ********************************************

    public void SaveConquistas(){
        SharedPreferences preferences = context.getSharedPreferences("ConquistaSave",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("N_Reportes",N_Reportes);
        editor.putInt("Index_1", L_Num.get(0));
        editor.putBoolean("Feito_1",L_Boo.get(0));

        editor.putInt("Index_2",L_Num.get(1));
        editor.putBoolean("Feito_2",L_Boo.get(1));

        editor.putInt("Index_3",L_Num.get(2));
        editor.putBoolean("Feito_3",L_Boo.get(2));

        Log.d("ID",L_Boo.get(0).toString());
        Log.d("ID",L_Boo.get(1).toString());
        Log.d("ID",L_Boo.get(2).toString());

        editor.apply();
    }

    public boolean LembrarUser() {

        SharedPreferences preferences = context.getSharedPreferences("ConquistaSave",MODE_PRIVATE);

        if(preferences.getInt("Index_1", -1) != -1) {
            N_Reportes = preferences.getInt("N_Reportes",0);
            L_Num.add(preferences.getInt("Index_1", 0));
            L_Num.add(preferences.getInt("Index_2", 0));
            L_Num.add(preferences.getInt("Index_3", 0));
            L_Boo.add(preferences.getBoolean("Feito_1", false));
            L_Boo.add(preferences.getBoolean("Feito_2", false));
            L_Boo.add(preferences.getBoolean("Feito_3", false));

            Log.d("ID",L_Boo.get(0).toString());
            Log.d("ID",L_Boo.get(1).toString());
            Log.d("ID",L_Boo.get(2).toString());


            if(L_Boo.get(0) && L_Boo.get(1) && L_Boo.get(2)){
                L_Num.clear();
                L_Boo.clear();
                N_Reportes = 0;
                return false;
            }else {
                return true;
            }

        }
        return false;

    }



    //************************* Sets ********************************************
    public void Set_AllConquistas(ArrayList<Conquista> L){
        L_AllConquistas = L;
    }

    public void Set_Context(Context C){
        context = C;
    }

}
