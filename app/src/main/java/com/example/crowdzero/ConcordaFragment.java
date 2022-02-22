package com.example.crowdzero;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.crowdzero.models.Instituicao;
import com.example.crowdzero.models.ListaConquistas;
import com.example.crowdzero.models.Usuario;
import com.example.crowdzero.server.ApiClient;
import com.example.crowdzero.server.DataLocais;
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

public class ConcordaFragment extends BottomSheetDialogFragment
{
    public static final String TAG = "ConcordaFragment";
    DataLocais dataLocal;
    Instituicao instituicao;
    public static ConcordaFragment newInstance(){ return new ConcordaFragment(); }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
    }
    public void setInstituicao(Instituicao institu){
        instituicao = institu;
    }

    public void setLocal(DataLocais Local){
        dataLocal = Local;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView IMG_Estado_Concoda = view.findViewById(R.id.IMG_Estado_Concoda);
        TextView TXT_Estado_Concordo = view.findViewById(R.id.TXT_Estado_Concordo);
        TextView TXT_Info_Concorda = view.findViewById(R.id.TXT_Info_Concorda);

        switch (Integer.parseInt(dataLocal.getStatus())){
            case 1:
                IMG_Estado_Concoda.setBackground(getResources().getDrawable(R.drawable.reporte_button_baixa_e));
                TXT_Estado_Concordo.setText(getResources().getString(R.string.Baixa));
                TXT_Info_Concorda.setText(TXT_Info_Concorda.getText() + " " + getResources().getString(R.string.Baixa) + " " + getResources().getString(R.string.ConcordaInfo2));

                break;
            case 2:
                IMG_Estado_Concoda.setBackground(getResources().getDrawable(R.drawable.reporte_button_moderada_e));
                TXT_Estado_Concordo.setText(getResources().getString(R.string.Moderada));
                TXT_Info_Concorda.setText(TXT_Info_Concorda.getText() + " " + getResources().getString(R.string.Moderada) + " " + getResources().getString(R.string.ConcordaInfo2));

                break;
            case 3:
                IMG_Estado_Concoda.setBackground(getResources().getDrawable(R.drawable.reporte_button_alta_e));
                TXT_Estado_Concordo.setText(getResources().getString(R.string.Alta));
                TXT_Info_Concorda.setText(TXT_Info_Concorda.getText() + " " + getResources().getString(R.string.Alta) + " " + getResources().getString(R.string.ConcordaInfo2));

                break;
        }

        ImageView BTN_Voltar = view.findViewById(R.id.BTN_Voltar_Concorda);
        BTN_Voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocaisInternosFragment locaisInternosFragment = LocaisInternosFragment.newInstance();
                locaisInternosFragment.setInstituicao(instituicao);
                dismiss();
                locaisInternosFragment.show(getFragmentManager(),locaisInternosFragment.TAG);
            }
        });

        TextView BTN_Interrogacao = view.findViewById(R.id.BTN_Interogacao_Concorda);
        BTN_Interrogacao.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dismiss();
                Intent intent = new Intent(getContext(),ComoReportar.class);
                intent.putExtra("instituicao", instituicao);
                startActivity(intent);
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

        Button BTN_Sim = view.findViewById(R.id.BTN_Sim);
        BTN_Sim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList usuario = Login.Dados_usuario;
                Usuario AUX_Usuario = (Usuario) usuario.get(0);

                Map<String,String> AUX_Map = new HashMap<String, String>();
                AUX_Map.put("nivelReporte", dataLocal.getStatus());
                AUX_Map.put("localId", dataLocal.getId());
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

                switch (Integer.parseInt(dataLocal.getStatus())){
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

        Button BTN_Nao = view.findViewById(R.id.BTN_Nao);
        BTN_Nao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReportarFragment reportarFragment = ReportarFragment.newInstance();
                reportarFragment.setDados(dataLocal.getNome(),dataLocal.getId());
                reportarFragment.setInstituicao(instituicao);
                dismiss();
                reportarFragment.show(getFragmentManager(),BottomSheetFragment.TAG);
            }
        });


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sheet_concorda_reporte,container,false);
    }
}
