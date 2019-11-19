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

import com.example.saolonguinho.R;
import com.example.saolonguinho.adapter.Adapter;
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
import java.util.List;

public class HomeFragment extends Fragment {

    //CRIANDO ATRIBUTO PARA RECICLEVIEW
    private RecyclerView recyclerView;
    private List<Cartao> listaCartao = new ArrayList<>();


    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Cartoes1");

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //FAZENDO REFERENCIA PARA O FRAGMENTO
        recyclerView = root.findViewById(R.id.recyclerHome);
        buscarDados();

        return root;
    }



    //RECUPERAR DADOS DO FIREBASE
    public void buscarDados(){

        DatabaseReference cartoes = reference;

        Query cartoesPesquisa = reference.orderByChild("status").equalTo(true);

        cartoesPesquisa.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Cartao cartao = dataSnapshot.getValue(Cartao.class);
                listaCartao.add(cartao);
                //CONFIGURAR O ADAPTER
                Adapter adapter = new Adapter( listaCartao );
                //CONFIGURAR RECYCLEVIEW
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setHasFixedSize(true);
                recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
                recyclerView.setAdapter( adapter );

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Cartao cartao = dataSnapshot.getValue(Cartao.class);
                listaCartao.add(cartao);
                //CONFIGURAR O ADAPTER
                Adapter adapter = new Adapter( listaCartao );
                //CONFIGURAR RECYCLEVIEW
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setHasFixedSize(true);
                recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
                recyclerView.setAdapter( adapter );

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });



    }

}