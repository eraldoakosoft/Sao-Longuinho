package com.example.saolonguinho.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saolonguinho.R;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_lista, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tipo.setText("RG");
        holder.nomeAchou.setText("Marcos da Silva");
        holder.nome.setText("Eraldo Simão Pereira");
        holder.dataPublicacao.setText("11/11/2019");
        holder.dataEncontrado.setText("08/10/2019");

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tipo;
        TextView dataPublicacao;
        TextView nome;
        TextView dataEncontrado;
        TextView nomeAchou;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tipo = itemView.findViewById(R.id.textViewTipo);
            dataEncontrado = itemView.findViewById(R.id.textViewDataEncontrado);
            dataPublicacao = itemView.findViewById(R.id.textViewDataPublicacao);
            nome = itemView.findViewById(R.id.textViewNomePerdeu);
            nomeAchou = itemView.findViewById(R.id.textViewNomeAchou);



        }
    }

}
