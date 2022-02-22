package com.example.crowdzero;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.example.crowdzero.models.Conquista;

import static android.content.Context.MODE_PRIVATE;

class ConquistasAdapter extends  RecyclerView.Adapter<ConquistasAdapter.ConquistaViewHolder> {

    private final List<Conquista> conquistas;
    private Context context;

    public ConquistasAdapter(ArrayList<Conquista> conquistas) {
        this.conquistas = conquistas;
    }

    @NonNull
    @Override
    public ConquistaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_list_conquistas, parent,false);
        context = parent.getContext();
        return new ConquistaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConquistaViewHolder holder, int position) {
        Conquista conquista =  conquistas.get(position);
        holder.bind(conquista);
    }

    @Override
    public int getItemCount() {
        return conquistas.size();
    }

    class ConquistaViewHolder extends RecyclerView.ViewHolder {

        TextView TXT_Nome;
        TextView TXT_Pontos;


        public ConquistaViewHolder(@NonNull View itemView){
            super(itemView);
            TXT_Nome = itemView.findViewById(R.id.TXT_NomeConquista);
            TXT_Pontos = itemView.findViewById(R.id.TXT_NPontosConquista);

        }
        @SuppressLint("SetTextI18n")
        public void bind(Conquista conquista){
            TXT_Nome.setText(itemView.getResources().getString(R.string.Conquista_1) + " " + conquista.getNome()+ " " + itemView.getResources().getString(R.string.Conquista_2));
            TXT_Pontos.setText(itemView.getResources().getString(R.string.C_Pontos_1) + " " +conquista.getNPontos() + " " + itemView.getResources().getString(R.string.C_Pontos_2));
        }
    }

}



/*
 SharedPreferences preferences = getSharedPreferences("LembrarUser",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("Lembrar",true);
        editor.putString("Email",Email);
        editor.putString("Senha",Senha);
        editor.apply();

*/

/*
 SharedPreferences preferences = getSharedPreferences("LembrarUser",MODE_PRIVATE);
        if(preferences.getBoolean("Lembrar",false)){
            String AUX_E = preferences.getString("Email","");
            String AUX_S = preferences.getString("Senha","");
            FazerLogin(AUX_E, AUX_S);
        }
*/
