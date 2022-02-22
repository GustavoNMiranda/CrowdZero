package com.example.crowdzero;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.example.crowdzero.models.Conquista;
import com.example.crowdzero.models.Instituicao;
import com.example.crowdzero.models.ListaConquistas;
import com.example.crowdzero.models.Usuario;
import com.example.crowdzero.server.ApiClient;
import com.example.crowdzero.server.RespostaLogin;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.maps.android.heatmaps.Gradient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportarFragment extends BottomSheetDialogFragment
{


    String Nome;
    String ID;
    String AUX_Reporte_Value;
    Instituicao instituicao;
    public static ReportarFragment newInstance(){
        return new ReportarFragment();
    }

    public void setDados(String nome,String id){
        Nome = nome;
        ID = id;
    }
    public void setInstituicao(Instituicao institu){
        instituicao = institu;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView TXT_SelecionaA = view.findViewById(R.id.TXT_SelecionaA);
        SpannableString AUX_Bold = new SpannableString(TXT_SelecionaA.getText() + " " + Nome);
        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        AUX_Bold.setSpan(boldSpan, TXT_SelecionaA.getText().length(), Nome.length() + TXT_SelecionaA.getText().length()+1 ,Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        TXT_SelecionaA.setText(AUX_Bold);

        TextView BTN_Interrogacao = view.findViewById(R.id.BTN_Interogacao);
        BTN_Interrogacao.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dismiss();
                Intent intent = new Intent(getContext(),ComoReportar.class);
                intent.putExtra("instituicao", instituicao);
                startActivity(intent);

            }
        });

        ImageView BTN_Voltar = view.findViewById(R.id.BTN_Voltar_Reportar);
        BTN_Voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LocaisInternosFragment locaisInternosFragment = LocaisInternosFragment.newInstance();
                locaisInternosFragment.setInstituicao(instituicao);
                dismiss();
                locaisInternosFragment.show(getFragmentManager(),locaisInternosFragment.TAG);
            }
        });


        RadioButton BTN_Radio_B = view.findViewById(R.id.BTN_Radio_B);
        RadioButton BTN_Radio_M = view.findViewById(R.id.BTN_Radio_M);
        RadioButton BTN_Radio_A = view.findViewById(R.id.BTN_Radio_A);
        Button BTN_Reportar = view.findViewById(R.id.BTN_Reportar2);


        BTN_Radio_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BTN_Reportar.setEnabled(true);
                AUX_Reporte_Value = "1";
            }
        });

        BTN_Radio_M.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BTN_Reportar.setEnabled(true);
                AUX_Reporte_Value = "2";
            }
        });

        BTN_Radio_A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BTN_Reportar.setEnabled(true);
                AUX_Reporte_Value = "3";
            }
        });

        int[] colors_1 = {
                Color.rgb(130, 209, 151)
        };

        int[] colors_2 = {
                Color.rgb(255, 152, 0)
        };

        int[] colors_3 = {
                Color.rgb(244, 67, 54)
        };

        float[] startPoints = {
                1f
        };

        BTN_Reportar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                ArrayList usuario = Login.Dados_usuario;
                Usuario AUX_Usuario = (Usuario) usuario.get(0);

                Map<String,String> AUX_Map = new HashMap<String, String>();
                AUX_Map.put("nivelReporte", AUX_Reporte_Value);
                AUX_Map.put("localId", ID);
                AUX_Map.put("associadoId",AUX_Usuario.getID().toString());

                Call<String> Server = ApiClient.getUserService().Reportar(AUX_Map);
                Server.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                    }
                });

                AUX_Usuario.setPontos(AUX_Usuario.getPontos() + 20);
                AUX_Usuario.setReportes(AUX_Usuario.getReportes() + 1);
                ConquitasManager conquitasManager = new ConquitasManager();
                conquitasManager.Set_AllConquistas(new ArrayList<>(ListaConquistas.LConquistas()));
                conquitasManager.Set_Context(getContext());
                conquitasManager.Reporte();

                Gradient gradient;

                switch (Integer.parseInt(AUX_Reporte_Value)){
                    case 1:
                        gradient= new Gradient(colors_1, startPoints);
                        break;
                    case 2:
                        gradient = new Gradient(colors_2, startPoints);
                        break;
                    case 3:
                        gradient = new Gradient(colors_3, startPoints);
                        break;
                    default:
                        gradient= new Gradient(colors_1, startPoints);
                }

                List<LatLng> latLngs_List = new ArrayList<LatLng>();
                LatLng AUX_latLng = new LatLng(MapView.Localizacao_Usuario.latitude,MapView.Localizacao_Usuario.longitude);
                latLngs_List.add(AUX_latLng);

                ((MapView)getActivity()).StyleHeatMap(gradient,latLngs_List);

                dismiss();
            }
        });


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sheet_reportar, container,false);
    }
}
