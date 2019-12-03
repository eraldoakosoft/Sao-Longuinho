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
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_cartao, parent,false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Cartao cartao = cartoes.get(position);
        holder.nome.setText(cartao.getNome());
        holder.nomeLonguinho.setText(cartao.getNomeLonguinho());
        holder.dataPublicacao.setText(cartao.getDataInseridoNoBanco());
        holder.dataEncontrado.setText(cartao.getDataEncontrado());
        holder.tipo.setText(cartao.getTipo());
        holder.emissor.setText(cartao.getBancoEmissor());
        holder.comentario.setText(cartao.getComentario());
        holder.dataSiada.setText(cartao.getDataSaida());
        holder.digitos.setText(cartao.getDigitos());
        holder.ultimaAtualizacao.setText(cartao.getUltimaAtualizacao());
        holder.status.setText(cartao.getStatus().toString());

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
        TextView emissor;
        TextView comentario;
        TextView dataSiada;
        TextView digitos;
        TextView nomeLonguinho;
        TextView status;
        TextView ultimaAtualizacao;
        TextView nome;
        TextView dataEncontrado;
        TextView dataPublicacao;
        ImageView foto;
        TextView ver;


        public MyViewHolder(View itemView){
            super(itemView);

            tipo = itemView.findViewById(R.id.textViewAdapterCartaoTipo);
            nome = itemView.findViewById(R.id.textViewAdapterCartaoNome);
            dataEncontrado = itemView.findViewById(R.id.textViewAdapterCartaoDataEncontrado);
            dataPublicacao = itemView.findViewById(R.id.textViewAdapterCartaoDataAdicionado);
            nomeLonguinho = itemView.findViewById(R.id.textViewAdapterCartaoNomeLonguinho);
            foto = itemView.findViewById(R.id.imageViewAdapterLista);
            emissor = itemView.findViewById(R.id.textViewAdapterCartaoEmissor);
            comentario = itemView.findViewById(R.id.textViewAdapterCartaoComentario);
            dataSiada = itemView.findViewById(R.id.textViewAdapterCartaoDataSaida);
            digitos = itemView.findViewById(R.id.textViewAdapterCartaoDigitos);
            status = itemView.findViewById(R.id.textViewAdapterCartaoStatus);
            ultimaAtualizacao = itemView.findViewById(R.id.textViewAdapterCartaoUltimaAtualizacao);


        }

    }

}
