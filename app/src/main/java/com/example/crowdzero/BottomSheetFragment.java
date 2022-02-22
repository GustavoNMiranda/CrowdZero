package com.example.crowdzero;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.crowdzero.models.Instituicao;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class BottomSheetFragment extends BottomSheetDialogFragment
{
    public static final String TAG = "BottomSheetFragment";
    Instituicao instituicao;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static BottomSheetFragment newInstance(){
        return new BottomSheetFragment();
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

        Location Usuario_Location = new Location("Usuario_Location");
        Usuario_Location.setLatitude(MapView.Localizacao_Usuario.latitude);
        Usuario_Location.setLongitude(MapView.Localizacao_Usuario.longitude);

        Location Instituicao_Location = new Location("Instituicao_Location");
        Instituicao_Location.setLatitude(instituicao.getLatitude());
        Instituicao_Location.setLongitude(instituicao.getLongitude());

        Button BTN_Reportar = view.findViewById(R.id.BTN_Reportar);

        Date Data_Atual = new Date();

        long diferenca = 0;

        try {
            if(!instituicao.getU_Reporte().isEmpty()) {
                Date AUX_Teste = simpleDateFormat.parse(instituicao.getU_Reporte());
                simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");
                diferenca = Data_Atual.getTime() - AUX_Teste.getTime();
                diferenca = TimeUnit.MILLISECONDS.toHours(diferenca);
            }else{
                diferenca = -10;
            }
        } catch (ParseException e) {

        }


        if(Usuario_Location.distanceTo(Instituicao_Location) > 100){//Distancia dos locais
            BTN_Reportar.setEnabled(false);
        }

        TextView TXT_Nome = view.findViewById(R.id.TXT_SheetNome);
        TXT_Nome.setText(instituicao.getNome());

        TextView TXT_Descricao_Nome = view.findViewById(R.id.TXT_CliquePara);

        TextView TXT_Endereco = view.findViewById(R.id.TXT_SheetEndereco2);
        TXT_Endereco.setText(instituicao.getEndereco());

        TextView TXT_Ocupacao = view.findViewById(R.id.TXT_MarcadorOcupacao);
        switch(instituicao.getEstado()){
            case 1:
                TXT_Ocupacao.setText(getResources().getString(R.string.Baixa));
                TXT_Ocupacao.setBackground(getResources().getDrawable(R.drawable.background_ocupacao_baixa));
                break;
            case 2:
                TXT_Ocupacao.setText(getResources().getString(R.string.Moderada));
                TXT_Ocupacao.setBackground(getResources().getDrawable(R.drawable.background_ocupacao_moderada));
                break;
            case 3:
                TXT_Ocupacao.setText(getResources().getString(R.string.Alta));
                TXT_Ocupacao.setBackground(getResources().getDrawable(R.drawable.background_ocupacao_alta));
                break;
            case 4:
                TXT_Ocupacao.setText(getResources().getString(R.string.Desenfeccao));
                TXT_Ocupacao.setBackground(getResources().getDrawable(R.drawable.background_ocupacao_desenfeccao));
                break;
            default:
                TXT_Ocupacao.setText("");
                TXT_Ocupacao.setBackground(getResources().getDrawable(R.drawable.background_ocupacao_seminfo));

                if(diferenca >= 24 && diferenca < 0) {
                    diferenca = TimeUnit.HOURS.toDays(diferenca);
                    TXT_Descricao_Nome.setText(getResources().getString(R.string.SemInfo) + " " + diferenca + " " + getResources().getString(R.string.Dias));
                }else if(diferenca > 0){
                    TXT_Descricao_Nome.setText(getResources().getString(R.string.SemInfo) + " " + diferenca + " " + getResources().getString(R.string.Horas));
                }else if(diferenca <= 0){
                    TXT_Descricao_Nome.setText(getResources().getString(R.string.AindaNao));
                }
        }


        BTN_Reportar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocaisInternosFragment locaisInternosFragment = LocaisInternosFragment.newInstance();
                locaisInternosFragment.setInstituicao(instituicao);
                dismiss();
                locaisInternosFragment.show(getFragmentManager(),locaisInternosFragment.TAG);
            }
        });

        ImageView BTN_LocaisInternos = view.findViewById(R.id.BTN_LocaisInternos);
        BTN_LocaisInternos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocaisInternosFragment locaisInternosFragment = LocaisInternosFragment.newInstance();
                locaisInternosFragment.setInstituicao(instituicao);
                dismiss();
                locaisInternosFragment.show(getFragmentManager(),locaisInternosFragment.TAG);
            }
        });


        ImageView BTN_Voltar = view.findViewById(R.id.BTN_Voltar_IInfo);
        BTN_Voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.sheet_instituicao_info,container,false);
    }
}
