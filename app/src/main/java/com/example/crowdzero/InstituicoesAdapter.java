package com.example.crowdzero;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.example.crowdzero.models.Instituicao;

 class InstituicoesAdapter extends RecyclerView.Adapter<InstituicoesAdapter.InstituicaoViewHolder> {
     private final List<Instituicao> instituicoes;
     private OnInstituicaoListener onInstituicaoListener;
     private Context context;


     public InstituicoesAdapter(ArrayList<Instituicao> instituicoes, OnInstituicaoListener onInstituicaoListener){
         this.instituicoes = instituicoes;
         this.onInstituicaoListener = onInstituicaoListener;

     }

     @SuppressLint("UseCompatLoadingForDrawables")
     public Drawable getDrable(Instituicao institu){
         switch (institu.getEstado()){
             case 1:
                 return context.getDrawable(R.drawable.reporte_button_baixa_e);
             case 2:
                 return context.getDrawable(R.drawable.reporte_button_moderada_e);
             case 3:
                 return context.getDrawable(R.drawable.reporte_button_alta_e);
             case 4:
                 return context.getDrawable(R.drawable.reporte_desinfeccao);
             default:
                 return context.getDrawable(R.drawable.reporte_sem_info);

         }

     }

     @NonNull
     @Override
     public InstituicaoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_list_instituicoes, parent,false);
         context = parent.getContext();
         return new InstituicaoViewHolder(view,onInstituicaoListener);
     }

     @Override
     public void onBindViewHolder(@NonNull InstituicaoViewHolder holder, int position) {
        Instituicao instituicao = instituicoes.get(position);
        holder.bind(instituicao);

     }

     @Override
     public int getItemCount() {
         return instituicoes.size();
     }

     class InstituicaoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView TXT_Nome;
        TextView TXT_Endereco;
        ImageView IMG;
        OnInstituicaoListener onInstituicaoListener;
        public InstituicaoViewHolder (@NonNull View itemView, OnInstituicaoListener onInstituicaoListener){
            super(itemView);
            TXT_Nome = itemView.findViewById(R.id.TXT_NomeInstituicao);
            TXT_Endereco = itemView.findViewById(R.id.TXT_Endereco);
            IMG = itemView.findViewById(R.id.IC_instituicao);

            this.onInstituicaoListener = onInstituicaoListener;
            itemView.setOnClickListener(this);
        }

        public void bind(Instituicao instituicao){
            TXT_Nome.setText(instituicao.getNome());
            TXT_Endereco.setText(instituicao.getEndereco());
            IMG.setBackground(getDrable(instituicao));
        }

         @Override
         public void onClick(View v) {
            onInstituicaoListener.onInstituicaoClick(getAdapterPosition());
         }

     }

    public interface OnInstituicaoListener{
         void onInstituicaoClick(int position);
    }

}
