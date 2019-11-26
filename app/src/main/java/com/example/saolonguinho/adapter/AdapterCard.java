package com.example.saolonguinho.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saolonguinho.R;
import com.example.saolonguinho.model.Cartao;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterCard extends RecyclerView.Adapter<AdapterCard.MyViewHolder> {

    private List<Cartao> cartoes;
    private Context context;

    public AdapterCard(List<Cartao> cartoes, Context context) {
        this.cartoes = cartoes;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_home_card, parent,false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Cartao cartao = cartoes.get(position);
        holder.nome.setText(cartao.getNome());
        holder.nomeQuemAchou.setText(cartao.getIdLonguinho());
        holder.dataPublicacao.setText(cartao.getDataInseridoNoBanco());
        holder.dataEncontrado.setText(cartao.getDataEncontrado());
        holder.tipo.setText(cartao.getTipo());

        //PEGAR A PRIMEIRA IMAGEM DA LISTA
        List<String> urlFotos = cartao.getFotos();
        String urlCapa = urlFotos.get(0);

        Picasso.get().load(urlCapa).into(holder.foto);


    }

    @Override
    public int getItemCount() {
        return cartoes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tipo;
        TextView nome;
        TextView dataEncontrado;
        TextView nomeQuemAchou;
        TextView dataPublicacao;
        ImageView foto;
        TextView ver;


        public MyViewHolder(View itemView){
            super(itemView);

            tipo = itemView.findViewById(R.id.textViewAdapterCardTipo);
            nome = itemView.findViewById(R.id.textViewAdapterCardNome);
            dataEncontrado = itemView.findViewById(R.id.textViewAdapterCardEncotrado);
            dataPublicacao = itemView.findViewById(R.id.textViewAdapterCardDataPublicacao);
            nomeQuemAchou = itemView.findViewById(R.id.textViewAdapterCardNomeAchou);
            foto = itemView.findViewById(R.id.imageViewFoto);
            ver = itemView.findViewById(R.id.textViewVer);


        }

    }

}
