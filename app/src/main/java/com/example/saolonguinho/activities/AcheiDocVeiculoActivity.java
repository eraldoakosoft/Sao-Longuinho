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
import com.example.saolonguinho.model.DocumentoVeiculo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AcheiDocVeiculoActivity extends AppCompatActivity {

    //VARIAVEIS LOCAIS PARA RECEBER DADOS DAS ACTIVITY
    private EditText campoNome, campoCPF, campoPlaca, campoDataEncontrado, campoModelo;
    private Button btnAdicionar;

    //INSTACIA DO FIREBASE
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("DocumentoVeiculo");

    //INSTANCIA PARA AUTENTICAÇÃO DO FIREBASE
    FirebaseAuth firebaseAuth;
    //INSTANCIA CLASSE DOCUMENTO VEICULO
    private DocumentoVeiculo documentoVeiculo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achei_doc_veiculo);
        getSupportActionBar().hide();

        campoCPF = findViewById(R.id.textViewDocVeiCPF);
        campoNome = findViewById(R.id.textViewDocVeiNomeCompleto);
        campoPlaca = findViewById(R.id.textViewDocVeiPlaca);
        campoDataEncontrado = findViewById(R.id.textViewDocVeiDataEncontrado);
        campoModelo = findViewById(R.id.textViewDocVeiModelo);
        btnAdicionar = findViewById(R.id.buttonDocVeiAdicionar);

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvar();
            }
        });

    }

    public void salvar(){
        //INSTANCIANDO UM NOVO DOCUMENTO VEICULO
        documentoVeiculo = new DocumentoVeiculo();
        //PEGAR O USUARIO PARA SABER QUEM ESTA ADICIONANDO AO BANCO,
        firebaseAuth = ConfiguracaoFirebase.getFirebaseAutenticacao();
        //STRING ONDE VAI SER SLAVO O EMAIL DA PESSOA QUE ESTA ADICIONANDO AO FIREBASE
        String idUsuario = Base64Custon.codificarBase64( firebaseAuth.getCurrentUser().getEmail());


        //PASSAR OS DADOS PARA O OBJETO DOCUMENTO VEICULO
        documentoVeiculo.setCpf(campoCPF.getText().toString());
        documentoVeiculo.setDataEncontrado(campoDataEncontrado.getText().toString());
        documentoVeiculo.setIdPessoaAchou(idUsuario);
        documentoVeiculo.setNome(campoNome.getText().toString());
        documentoVeiculo.setPlaca(campoPlaca.getText().toString());
        documentoVeiculo.setModelo(campoModelo.getText().toString());
        documentoVeiculo.setDataEntradaNoBanco(getDateTime());

        reference.push().setValue(documentoVeiculo);
        Toast.makeText(AcheiDocVeiculoActivity.this, "Salvo com Secesso!", Toast.LENGTH_SHORT).show();
        finish();

    }

    //METODO PARA PEGAR DATA E HORA DO SISTEMA
    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
