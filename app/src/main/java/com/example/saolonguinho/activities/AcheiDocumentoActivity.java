package com.example.saolonguinho.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.saolonguinho.R;
import com.example.saolonguinho.config.ConfiguracaoFirebase;
import com.example.saolonguinho.helper.Base64Custon;
import com.example.saolonguinho.model.Documento;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AcheiDocumentoActivity extends AppCompatActivity {

    //VARIAVEIS LOCAIS PARA RECEBER O QUE VEM DA TELA
    private EditText campoNome, campoCPF, campoRG, campoDataNascimento;
    private EditText campoNomeMae, campoNaturalidade, campoDataEncontrado;
    private Button btnAdicionar;

    //PEGAR INSTANCIA DO FIREBASE
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Documentos");
    //INSTACIAR A CLASSE DOCUMENTO
    private Documento documento;
    //PEGAR INISTACIA DO FIREBASE AUTH
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achei_documento);
        getSupportActionBar().hide();

        //PASSANDO DADOS DA ACTIVITY PARA AS VARIAVEIS LOCAIS
        campoCPF = findViewById(R.id.editTextDocCpf);
        campoDataEncontrado = findViewById(R.id.editTextDocDataEncontrado);
        campoDataNascimento = findViewById(R.id.editTextDocDataNascimento);
        campoNaturalidade = findViewById(R.id.editTextDocNaturalidade);
        campoNome = findViewById(R.id.editTextDocNomeCompleto);
        campoNomeMae = findViewById(R.id.editTextDocNomeMae);
        campoRG = findViewById(R.id.editTextDocRg);
        btnAdicionar = findViewById(R.id.buttonDocAdicionar);



        //ADICIONANDO O EVENTO DE CLICK
        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvar();
            }
        });


    }


    //METODO PARA SALVAR
    public void salvar(){
        //INSTACIANDO UM NOVO OBJETO DOCUMENTO
        documento = new Documento();

        //PEGAR O USUARIO PARA SABER QUEM ESTA ADICIONANDO AO BANCO,
        firebaseAuth = ConfiguracaoFirebase.getFirebaseAutenticacao();
        //STRING ONDE VAI SER SLAVO O EMAIL DA PESSOA QUE ESTA ADICIONANDO AO FOREBASE
        String idUsuario = Base64Custon.codificarBase64( firebaseAuth.getCurrentUser().getEmail());

        //PASSANDO OS DADOS PARA O OBJETO DOCUMENTO
        documento.setCpf(campoCPF.getText().toString());
        documento.setDataAchado(campoDataEncontrado.getText().toString());
        documento.setDataNascimento(campoDataNascimento.getText().toString());
        documento.setNaturalidade(campoNaturalidade.getText().toString());
        documento.setRg(campoRG.getText().toString());
        documento.setNomeMae(campoNomeMae.getText().toString());
        documento.setNome(campoNome.getText().toString());
        documento.setDataCadastroNoBanco(getDateTime());
        documento.setIdQuemAchou(idUsuario);

        //MANDANDO PARA O FIREBASE
        reference.push().setValue(documento);
        Toast.makeText(AcheiDocumentoActivity.this, "Salvo com Sucesso!", Toast.LENGTH_SHORT).show();
        finish();


    }

    //METODO PARA PEGAR DATA E HORA DO SISTEMA
    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

}
