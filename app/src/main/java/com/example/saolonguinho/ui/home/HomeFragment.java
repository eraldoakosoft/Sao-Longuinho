package com.example.saolonguinho.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saolonguinho.MainActivity;
import com.example.saolonguinho.R;
import com.example.saolonguinho.adapter.Adapter;
import com.example.saolonguinho.adapter.AdapterCard;
import com.example.saolonguinho.config.ConfiguracaoFirebase;
import com.example.saolonguinho.model.Cartao;
import com.example.saolonguinho.model.Documento;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeFragment extends Fragment {

    //CRIANDO ATRIBUTO PARA RECICLEVIEW
    private RecyclerView recyclerView;
    private List<Cartao> listaCartao = new ArrayList<>();
    private AdapterCard adapterCard;


    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Modelo");

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //FAZENDO REFERENCIA PARA O FRAGMENTO
        recyclerView = root.findViewById(R.id.recyclerHome);

        recuperarCartoes();


        //CONFIGURAR RECYCLER VIEW
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);
        //recyclerView.
        adapterCard = new AdapterCard(listaCartao, this.getContext());
        recyclerView.setAdapter(adapterCard);

        return root;
    }


    private void recuperarCartoes(){
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaCartao.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    listaCartao.add(ds.getValue(Cartao.class));
                    //System.out.println("--------------------------------");

                }

                Collections.reverse(listaCartao);
                adapterCard.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}