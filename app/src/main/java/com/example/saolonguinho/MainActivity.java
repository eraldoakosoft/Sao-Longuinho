package com.example.saolonguinho;

import android.os.Bundle;

import com.example.saolonguinho.config.ConfiguracaoFirebase;
import com.example.saolonguinho.helper.Base64Custon;
import com.example.saolonguinho.model.Usuario;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //PEGAR INSTANCIA DO FIREBASE
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth firebaseAuth;

    //OBJETO USUARIO PARA RECEBER DADOS DO BANCO
    private Usuario usuarioDados;

    private  TextView txtNome;

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        View haederView = navigationView.getHeaderView(0);
        txtNome = (TextView) haederView.findViewById(R.id.nomeNavHaedar);
        TextView txtEmail = (TextView) haederView.findViewById(R.id.textViewEmaill);

        //PEGAR O USUARIO PARA SABER QUEM ESTA ADICIONANDO AO BANCO,
        firebaseAuth = ConfiguracaoFirebase.getFirebaseAutenticacao();

        //STRING ONDE VAI SER SALVO O EMAIL DA PESSOA QUE ESTA ADICIONANDO AO FOREBASE
        String idUsuario = Base64Custon.codificarBase64( firebaseAuth.getCurrentUser().getEmail());
        //CONFIGURAR O FIREBASE
        final DatabaseReference usuarios = reference.child("Usuarioss");
        DatabaseReference usuarioss = usuarios.child(idUsuario);
        usuarioss.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usuarioDados = dataSnapshot.getValue(Usuario.class);
                txtNome.setText(usuarioDados.getNome());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






        //txtNome.setText(usuarioDados.getNome()+"|");
        txtEmail.setText(firebaseAuth.getCurrentUser().getEmail());
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
