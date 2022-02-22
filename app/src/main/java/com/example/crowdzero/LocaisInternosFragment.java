package com.example.crowdzero;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.crowdzero.models.Instituicao;
import com.example.crowdzero.models.Usuario;
import com.example.crowdzero.server.ApiClient;
import com.example.crowdzero.server.DataLocais;
import com.example.crowdzero.server.RespostaLocais;
import com.example.crowdzero.server.RespostaLogin;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocaisInternosFragment extends BottomSheetDialogFragment
{

    Instituicao instituicao;
    Context Contex;
    ArrayList L_Locais = new ArrayList<DataLocais>();
    DataLocais AUX_Local;

    public static final String TAG = "LocaisInternosFragment";

    public static  LocaisInternosFragment newInstance(){ return new LocaisInternosFragment(); }

    public void setInstituicao(Instituicao institu){
        instituicao = institu;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Contex = context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Location Usuario_Location = new Location("Usuario_Location");
        Usuario_Location.setLatitude(MapView.Localizacao_Usuario.latitude);
        Usuario_Location.setLongitude(MapView.Localizacao_Usuario.longitude);

        Location Local_Location = new Location("Local_Location");
        Local_Location.setLatitude(instituicao.getLatitude());
        Local_Location.setLongitude(instituicao.getLongitude());

        Button BTN_Reportar_Locais = view.findViewById(R.id.BTN_Reportar_Locais);

        if(Usuario_Location.distanceTo(Local_Location) > 100){////Distancia dos locais
            BTN_Reportar_Locais.setEnabled(false);
        }
        ArrayList usuario = Login.Dados_usuario;
        Usuario AUX_Usuario = (Usuario) usuario.get(0);

        Call<RespostaLocais> Server = ApiClient.getUserService().GetLocais("Bearer " + AUX_Usuario.getToken(),instituicao.getID().toString());
        Server.enqueue(new Callback<RespostaLocais>() {
            @Override
            public void onResponse(Call<RespostaLocais> call, Response<RespostaLocais> response) {
                if(response.isSuccessful()) {
                    RespostaLocais respostaLocais = response.body();
                    if(respostaLocais.getSuccess()){
                        if(respostaLocais.getData().size() != 0) {
                            for (int X = 0; X < respostaLocais.getData().size(); ++X) {
                                DataLocais AUX = new DataLocais();
                                AUX.setId(respostaLocais.getData().get(X).getId());
                                AUX.setNome(respostaLocais.getData().get(X).getNome());
                                AUX.setDescricao(respostaLocais.getData().get(X).getDescricao());
                                AUX.setStatus(respostaLocais.getData().get(X).getStatus());
                                L_Locais.add(AUX);
                            }
                            ChangeLayout(view);
                        }else {
                            DisebleLayout(view);
                        }
                    }else{
                        DisebleLayout(view);
                    }

                }else {
                    DisebleLayout(view);
                }
            }

            @Override
            public void onFailure(Call<RespostaLocais> call, Throwable t) {
                DisebleLayout(view);
            }
        });



        ImageView BTN_Voltar = view.findViewById(R.id.BTN_Voltar_LInternos);
        BTN_Voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BottomSheetFragment bottomSheetFragment = BottomSheetFragment.newInstance();
                bottomSheetFragment.setInstituicao(instituicao);
                bottomSheetFragment.show(getFragmentManager(),BottomSheetFragment.TAG);
                dismiss();
            }


        });


        BTN_Reportar_Locais.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dismiss();
                ConcordaFragment concordaFragment = ConcordaFragment.newInstance();
                concordaFragment.setLocal(AUX_Local);
                concordaFragment.setInstituicao(instituicao);
                concordaFragment.show(getFragmentManager(),BottomSheetFragment.TAG);
            }
        });
    }

    public void ChangeLayout(View view){

        TextView TXT_N_LocalInterno = view.findViewById(R.id.TXT_N_LocalInterno);
        TextView TXT_Descricao_LocalInterno = view.findViewById(R.id.TXT_Descricao_LocalInterno);
        TextView TXT_MarcadorOcupacaoInternos = view.findViewById(R.id.TXT_MarcadorOcupacaoInternos);

        TextView TXT_Info_LInternos = view.findViewById(R.id.TXT_Info_LInternos);
        SpannableString AUX_Bold = new SpannableString(TXT_Info_LInternos.getText() + " " + instituicao.getNome());
        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        AUX_Bold.setSpan(boldSpan, TXT_Info_LInternos.getText().length(), instituicao.getNome().length() + TXT_Info_LInternos.getText().length()+1 , Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        TXT_Info_LInternos.setText(AUX_Bold);

        ArrayList<String> NameList = new ArrayList<String>();
        for(int X = 0; X < L_Locais.size(); ++X){
            AUX_Local = (DataLocais) L_Locais.get(X);
            NameList.add(AUX_Local.getNome());
        }

        Spinner spinner = (Spinner) view.findViewById(R.id.Spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),R.layout.dropdown_item,NameList);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AUX_Local = (DataLocais) L_Locais.get(position);
                TXT_N_LocalInterno.setText(AUX_Local.getNome());
                TXT_Descricao_LocalInterno.setText(AUX_Local.getDescricao());

                switch(Integer.parseInt(AUX_Local.getStatus())){
                    case 1:
                        TXT_MarcadorOcupacaoInternos.setText(getResources().getString(R.string.Baixa));
                        TXT_MarcadorOcupacaoInternos.setBackground(getResources().getDrawable(R.drawable.background_ocupacao_baixa));
                        break;
                    case 2:
                        TXT_MarcadorOcupacaoInternos.setText(getResources().getString(R.string.Moderada));
                        TXT_MarcadorOcupacaoInternos.setBackground(getResources().getDrawable(R.drawable.background_ocupacao_moderada));
                        break;
                    case 3:
                        TXT_MarcadorOcupacaoInternos.setText(getResources().getString(R.string.Alta));
                        TXT_MarcadorOcupacaoInternos.setBackground(getResources().getDrawable(R.drawable.background_ocupacao_alta));
                        break;
                    case 4:
                        TXT_MarcadorOcupacaoInternos.setText(getResources().getString(R.string.Desenfeccao));
                        TXT_MarcadorOcupacaoInternos.setBackground(getResources().getDrawable(R.drawable.background_ocupacao_desenfeccao));
                        break;
                    default:
                        TXT_MarcadorOcupacaoInternos.setText("");
                        TXT_MarcadorOcupacaoInternos.setBackground(getResources().getDrawable(R.drawable.background_ocupacao_seminfo));

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void DisebleLayout(View view){
        Button BTN_Reportar_Locais = view.findViewById(R.id.BTN_Reportar_Locais);
        TextView TXT_N_LocalInterno = view.findViewById(R.id.TXT_N_LocalInterno);
        TextView TXT_Descricao_LocalInterno = view.findViewById(R.id.TXT_Descricao_LocalInterno);
        TextView TXT_MarcadorOcupacaoInternos = view.findViewById(R.id.TXT_MarcadorOcupacaoInternos);

        TXT_MarcadorOcupacaoInternos.setText("");
        TXT_Descricao_LocalInterno.setText(R.string.NaoLocais);
        TXT_N_LocalInterno.setText("");
        BTN_Reportar_Locais.setEnabled(false);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sheet_locais_internos, container, false);
    }






}
