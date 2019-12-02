package com.example.saolonguinho.model;

import androidx.annotation.NonNull;

import com.example.saolonguinho.config.ConfiguracaoFirebase;
import com.example.saolonguinho.helper.DadosDeUsuarios;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class VerificarIgual {
    private DatabaseReference referenceUsuario = FirebaseDatabase.getInstance().getReference().child("Usuarioss");
    private FirebaseAuth firebaseAuth = ConfiguracaoFirebase.getFirebaseAutenticacao();
    private String idUsuario;
    private Usuario usuario = new Usuario();

    public VerificarIgual(String nome) {
        Query pesquisaNome = referenceUsuario.child("nome").equalTo(nome);
        pesquisaNome.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usuario = dataSnapshot.getValue(Usuario.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        idUsuario = usuario.getIdUsuario();


    }

    public String buscarPorNome(){
        return idUsuario;
    }
}
