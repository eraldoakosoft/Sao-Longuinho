package com.example.saolonguinho.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saolonguinho.R;
import com.example.saolonguinho.model.Modelo;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterModelo extends RecyclerView.Adapter<AdapterModelo.MyViewHolder> {


    private List<Modelo> modelos;
    private Context context;

    public AdapterModelo(List<Modelo> modelos, Context context) {
        this.modelos = modelos;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_home_modelo, parent, false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Modelo modelo = modelos.get(position);
        holder.nome.setText(modelo.getNome());
        holder.nomeQuemAchou.setText(modelo.getNomeLonguinho());
        holder.dataPublicacao.setText(modelo.getDataInseridoNoBanco());
        holder.dataEncontrado.setText(modelo.getDataEncontrado());
        holder.tipo.setText(modelo.getTipo());

        //PEGAR A PRIMEIRA FOTO DA LISTA
        List<String> urlFotos = modelo.getFotos();
        String urlCapa = urlFotos.get(0);

        Picasso.get().load(urlCapa).into(holder.foto);

    }

    @Override
    public int getItemCount() {
        return modelos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tipo;
        TextView nome;
        TextView dataEncontrado;
        TextView nomeQuemAchou;
        TextView dataPublicacao;
        ImageView foto;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tipo = itemView.findViewById(R.id.textViewAdapterCardTipo);
            nome = itemView.findViewById(R.id.textViewAdapterCardNome);
            dataEncontrado = itemView.findViewById(R.id.textViewAdapterCardEncotrado);
            dataPublicacao = itemView.findViewById(R.id.textViewAdapterCardDataPublicacao);
            nomeQuemAchou = itemView.findViewById(R.id.textViewAdapterCardNomeAchou);
            foto = itemView.findViewById(R.id.imageViewFoto);
        }
    }
}
