package com.example.saolonguinho.helper;


import androidx.annotation.NonNull;

import com.example.saolonguinho.config.ConfiguracaoFirebase;
import com.example.saolonguinho.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DadosDeUsuarios  {
    //PEGAR INSTANCIA DO FIREBASE
    private DatabaseReference referenceUsuario = FirebaseDatabase.getInstance().getReference().child("Usuarioss");
    private FirebaseAuth firebaseAuth = ConfiguracaoFirebase.getFirebaseAutenticacao();
    private Usuario usuario;
    private String nome;
    private String email;
    public List<Usuario> LisUsuarios = new ArrayList<>();

    public DadosDeUsuarios() {
        DatabaseReference pesquisaUsuario  = referenceUsuario.child(Base64Custon.codificarBase64(firebaseAuth.getCurrentUser().getEmail()));
        pesquisaUsuario.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usuario = dataSnapshot.getValue(Usuario.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    public String pegarNome(){
        nome = usuario.getNome();
        return nome;
    }

    public String pegarEmail(){
        email = firebaseAuth.getCurrentUser().getEmail();
        return email;

    }

}
