package com.example.crowdzero.server;

import android.media.session.MediaSession;

import com.example.crowdzero.Login;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface UserService {


        @POST("auth/loginUtilizador")
        Call<RespostaLogin> Login(@Body Map<String,String> Dados);

        @POST("auth/registoUtilizador")
        Call<RespostaLogin> Registrar(@Body Map<String,String> Dados);

        @POST("/associacao/validate")
        Call<RespostaAssociar> ValidarAssociar(@Body Map<String,String> Dados);

        @POST("associacao/create")
        Call<RespostaAssociar> Associar(@Body Map<String,String> Dados);

        @POST("reporte/create")
        Call<String> Reportar(@Body Map<String,String> Dados);

        @GET("associacao/rakingUsers")
        Call<RespostaRank> GetRank();

        @GET("associacao/associacoes/{id}")
        Call<RespostaGetInstituicao> GetInstituicoes(@Path("id")String ID);

        @GET("local/list")
        Call<RespostaLocais> GetLocais(@Header("Authorization") String token, @Query("id")String ID);

        @POST("associado/conquista")
        Call<RespostaConquista> AddConquista(@Body Map<String,String> Dados);


}
