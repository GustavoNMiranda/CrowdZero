package com.example.crowdzero;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.example.crowdzero.server.DataRank;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.RankViewHolder> {

    private final List<DataRank> ranking;

    public RankingAdapter(ArrayList<DataRank> ranking){this.ranking = ranking;}

    @NonNull
    @Override
    public RankViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_list_ranking,parent,false);
        return new RankViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RankViewHolder holder, int position) {
        DataRank dataRank = ranking.get(position);
        holder.bind(dataRank);
    }

    @Override
    public int getItemCount() {
        return ranking.size();
    }

    class RankViewHolder extends RecyclerView.ViewHolder{
        TextView TXT_Nome;
        TextView TXT_Pontos;
        TextView TXT_Divisao;
        ImageView IMG_FotoRank;

        public RankViewHolder (@NonNull View itemView){
            super(itemView);
            TXT_Nome = itemView.findViewById(R.id.TXT_NomeUsuario);
            TXT_Pontos = itemView.findViewById(R.id.TXT_PontosRank);
            TXT_Divisao = itemView.findViewById(R.id.TXT_DivisaoRank);
            IMG_FotoRank = itemView.findViewById(R.id.IMG_FotoRank);
        }
        public void bind(DataRank dataRank){
            TXT_Nome.setText(dataRank.getNome_user());
            TXT_Pontos.setText(dataRank.getPontuacao_user());

            switch(Integer.parseInt(dataRank.getDivisao())){
                case 1:
                    IMG_FotoRank.setImageDrawable(IMG_FotoRank.getResources().getDrawable(R.drawable.avatar_bronze));
                    TXT_Divisao.setText(IMG_FotoRank.getResources().getString(R.string.Bronze));
                    TXT_Divisao.setBackgroundTintList((IMG_FotoRank.getResources().getColorStateList(R.color.Bronze)));
                    break;
                case 2:
                    IMG_FotoRank.setImageDrawable(IMG_FotoRank.getResources().getDrawable(R.drawable.avatar_silver));
                    TXT_Divisao.setText(IMG_FotoRank.getResources().getString(R.string.Prata));
                    TXT_Divisao.setBackgroundTintList((IMG_FotoRank.getResources().getColorStateList(R.color.Prata)));
                    break;
                case 3:
                    IMG_FotoRank.setImageDrawable(IMG_FotoRank.getResources().getDrawable(R.drawable.avatar_gold));
                    TXT_Divisao.setText(IMG_FotoRank.getResources().getString(R.string.Ouro));
                    TXT_Divisao.setBackgroundTintList((IMG_FotoRank.getResources().getColorStateList(R.color.Ouro)));
                    break;
                default:
                    TXT_Divisao.setText("");
                    TXT_Divisao.setBackgroundTintList((IMG_FotoRank.getResources().getColorStateList(R.color.Trasparente)));
            }

        }



    }




}
