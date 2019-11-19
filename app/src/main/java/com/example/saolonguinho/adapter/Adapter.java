package com.example.saolonguinho.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saolonguinho.R;
import com.example.saolonguinho.model.Cartao;


import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private List<Cartao> listaCartao;

    public Adapter(List<Cartao> list) {
        this.listaCartao = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_lista, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Cartao cartao = listaCartao.get(position);
        holder.tipo.setText(cartao.getTipo());
        holder.nomeAchou.setText(cartao.getIdPessoaachou());
        holder.nome.setText(cartao.getNome());
        holder.dataPublicacao.setText(cartao.getDataInseridoNoBanco());
        holder.dataEncontrado.setText(cartao.getDataEncontrado());

    }

    @Override
    public int getItemCount() {
        return listaCartao.size();

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
