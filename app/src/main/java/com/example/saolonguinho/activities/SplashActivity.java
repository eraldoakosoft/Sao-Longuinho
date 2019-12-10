package com.example.saolonguinho.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.example.saolonguinho.MainActivity;
import com.example.saolonguinho.R;
import com.example.saolonguinho.config.ConfiguracaoFirebase;
import com.example.saolonguinho.helper.DadosDeUsuarios;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {

    //PEGAR INSTANCIA DO FIREBASE AUTH
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                verificarLogado();
                finish();
            }
        }, 500);

    }


    //METODO PARA VERIFICAR SE O USUARIO ESTA LOGADO;
    //SE O OSUARIO ESTIVER LOGADO ELE É DIRECIONADO PARA TELA PRINCIPAL, CASO CONTRARIO ELE É DIRECIONADO PRA TELA DE LOGIN
    public void verificarLogado(){
        firebaseAuth = ConfiguracaoFirebase.getFirebaseAutenticacao();
        if(firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(this, MainActivity.class));
        }else{
            startActivity(new Intent(getBaseContext(), LoginActivity.class));
        }

    }
}
