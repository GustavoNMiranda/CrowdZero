package com.example.crowdzero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.crowdzero.models.Instituicao;
import com.example.crowdzero.models.Usuario;
import com.example.crowdzero.server.ApiClient;
import com.example.crowdzero.server.DataInstiuicao;
import com.example.crowdzero.server.RespostaAssociar;
import com.example.crowdzero.server.RespostaLogin;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssociarCodigo extends AppCompatActivity {
    private ImageView BTN_Voltar;
    private EditText C1,C2,C3,C4;
    private Button BTN_Confirmar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_associar_codigo);
        BTN_Confirmar = findViewById(R.id.BTN_Continuar);
        BTN_Voltar = findViewById(R.id.BTN_Voltar_Codigo);
        C1 = findViewById(R.id.INP_C1);
        C2 = findViewById(R.id.INP_C2);
        C3 = findViewById(R.id.INP_C3);
        C4 = findViewById(R.id.INP_C4);



        BTN_Voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AssociarCodigo.this,AssociarInfo.class));
            }
        });

        BTN_Confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Codigo = C1.getText().toString() + C2.getText().toString() + C3.getText().toString() + C4.getText().toString();
                if(!Codigo.isEmpty()){

                    Map<String, String> AUX_Map = new HashMap<String, String>();
                    AUX_Map.put("token_access", Codigo);

                    Call<RespostaAssociar> Server = ApiClient.getUserService().ValidarAssociar(AUX_Map);
                    Server.enqueue(new Callback<RespostaAssociar>() {
                        @Override
                        public void onResponse(Call<RespostaAssociar> call, Response<RespostaAssociar> response) {
                            if(response.isSuccessful()){
                                RespostaAssociar respostaAssociar = response.body();
                                if(respostaAssociar.isSuccess()){
                                    Intent intent = new Intent(AssociarCodigo.this,AssociarVerificacao.class);
                                    intent.putExtra("Nome", respostaAssociar.getData().getNome_instituicao());
                                    intent.putExtra("N_Associados", respostaAssociar.getData().getQnt_associados());
                                    intent.putExtra("N_espacos", respostaAssociar.getData().getQnt_espacos());
                                    intent.putExtra("Telefone", respostaAssociar.getData().getContacto_instituicao());
                                    intent.putExtra("Lat", respostaAssociar.getData().getLatitude());
                                    intent.putExtra("Lon", respostaAssociar.getData().getLongitude());
                                    intent.putExtra("token", Codigo);
                                    startActivity(intent);
                                }else {
                                    Toast.makeText(AssociarCodigo.this,getString(R.string.ERRO_CodigoErrado) ,Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(AssociarCodigo.this,getString(R.string.ERRO_Conexao) ,Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<RespostaAssociar> call, Throwable t) {
                            Toast.makeText(AssociarCodigo.this,getString(R.string.ERRO_Conexao) ,Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{
                    Toast.makeText(AssociarCodigo.this,getString(R.string.ERRO_EspacosEmBranco) ,Toast.LENGTH_SHORT).show();
                }

            }
        });

        setOTPInputs();
    }

    private void setOTPInputs(){
        C1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    C2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        C2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    C3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        C3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    C4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        C4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    hideKeyboard(C4.getContext(),C4);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public static void hideKeyboard(Context context, View editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }



}