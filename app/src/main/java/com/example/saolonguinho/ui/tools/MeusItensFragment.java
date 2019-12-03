package com.example.saolonguinho.ui.tools;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saolonguinho.R;
import com.example.saolonguinho.adapter.AdapterCard;
import com.example.saolonguinho.config.ConfiguracaoFirebase;
import com.example.saolonguinho.helper.Base64Custon;
import com.example.saolonguinho.model.Cartao;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MeusItensFragment extends Fragment {

    private Button btnCartao;
    private Button btnDocVei;
    private Button btnDoc;

    private MeusItensViewModel toolsViewModel;
    private RecyclerView recyclerView;
    private List<Cartao> listaCartao = new ArrayList<>();
    private AdapterCard adapterCard;
    private FirebaseAuth firebaseAuth = ConfiguracaoFirebase.getFirebaseAutenticacao();

    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel = ViewModelProviders.of(this).get(MeusItensViewModel.class);
        View root = inflater.inflate(R.layout.fragment_meus_itens, container, false);

        btnCartao = root.findViewById(R.id.buttonMeusItensCartao);
        btnDoc = root.findViewById(R.id.buttonMeusItensDocumento);
        btnDocVei = root.findViewById(R.id.buttonMeusItensDocVei);

        btnCartao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recuperarCartao(2);
            }
        });

        btnDocVei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        //FAZENDO REFERENCIA PARA O FRAGMENTO
        recyclerView = root.findViewById(R.id.recyclerMeusItens);

        recuperarCartao(1);


        //CONFIGURAR RECYCLER VIEW
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);
        adapterCard = new AdapterCard(listaCartao, this.getContext());
        recyclerView.setAdapter(adapterCard);

        return root;
    }


    private void recuperarCartao(int i){
        String idLonguinho = Base64Custon.codificarBase64(firebaseAuth.getCurrentUser().getEmail());

        reference = ConfiguracaoFirebase.getFirebase().child("Cartao");

        if(i==2){
            Query pesquisa = reference.orderByChild("idLonguinho").equalTo(idLonguinho);
            pesquisa.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    listaCartao.clear();
                    for( DataSnapshot ds: dataSnapshot.getChildren() ){
                        listaCartao.add( ds.getValue(Cartao.class) );
                    }

                    Collections.reverse(listaCartao);
                    adapterCard.notifyDataSetChanged();
                }



                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }else if(i==1){

        }else if(i==3){

        }

    }
}