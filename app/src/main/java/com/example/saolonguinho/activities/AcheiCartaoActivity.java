package com.example.saolonguinho.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.saolonguinho.R;
import com.example.saolonguinho.config.ConfiguracaoFirebase;
import com.example.saolonguinho.helper.Base64Custon;
import com.example.saolonguinho.model.Cartao;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AcheiCartaoActivity extends AppCompatActivity {

    //VARIAVEIS LOCAIS PARA RECEBER DADOS DA ACTIVITY
    private EditText campoNome, campo4Digitos, campoBanco, campoDataAchou;
    private Button btnAdicionar;

    //PEGAR INSTANCIA DO FIREBASE
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Cartoes");
    //INSTACIAR A CLASSE CARTÃO
    private Cartao cartao;
    //PEGAR INISTACIA DO FIREBASE AUTH
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achei_cartao);
        getSupportActionBar().hide();

        //PASANDO DADOS PARA AS VARIAVEIS LOCAIS
        campo4Digitos = findViewById(R.id.editTextCar4Digitos);
        campoBanco = findViewById(R.id.editTextCarBanco);
        campoNome = findViewById(R.id.editTextCarNome);
        campoDataAchou = findViewById(R.id.editTextCarDataAchou);
        btnAdicionar = findViewById(R.id.buttonCarAdicionar);

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvar();
            }
        });

    }


    //METODO PARA SALVAR
    public void salvar(){
        //INSTACIAR UM NOVO OBJETO CARTAO
        cartao = new Cartao();

        //PEGAR O USUARIO PARA SABER QUEM ESTA ADICIONANDO AO BANCO,
        firebaseAuth = ConfiguracaoFirebase.getFirebaseAutenticacao();
        //STRING ONDE VAI SER SLAVO O EMAIL DA PESSOA QUE ESTA ADICIONANDO AO FOREBASE
        String idUsuario = Base64Custon.codificarBase64( firebaseAuth.getCurrentUser().getEmail());

        //PASSAR OS DADOS PARA O OBJETO CARTAO
        cartao.setBanco(campoBanco.getText().toString());
        cartao.setDataEncontrado(getDateTime());
        cartao.setDigitos(campo4Digitos.getText().toString());
        cartao.setNome(campoNome.getText().toString().toUpperCase());
        cartao.setDataEncontrado(campoDataAchou.getText().toString());
        cartao.setIdPessoaachou(idUsuario);

        reference.push().setValue(cartao);
        Toast.makeText(AcheiCartaoActivity.this, "Salvo com Sucesso!", Toast.LENGTH_SHORT).show();
        finish();
    }


    //METODO PARA PEGAR DATA E HORA DO SISTEMA
    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
