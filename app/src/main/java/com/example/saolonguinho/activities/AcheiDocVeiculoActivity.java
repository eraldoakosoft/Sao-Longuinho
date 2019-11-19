package com.example.saolonguinho.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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
import java.util.Calendar;
import java.util.Date;

public class AcheiDocVeiculoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    //VARIAVEIS LOCAIS PARA RECEBER DADOS DAS ACTIVITY
    private EditText campoNome, campoCPF, campoPlaca, campoDataEncontrado, campoModelo, campoDescicao;
    private Button btnAdicionar;

    private Spinner spinnerAVei;

    //INSTACIA DO FIREBASE
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("DocumentoVeiculo");

    //INSTANCIA PARA AUTENTICAÇÃO DO FIREBASE
    FirebaseAuth firebaseAuth;
    //INSTANCIA CLASSE DOCUMENTO VEICULO
    private DocumentoVeiculo documentoVeiculo;

    //CONFIGURAÇÃO PARA O CALENDARIO
    Calendar calendar;
    android.app.DatePickerDialog datePickerDialog;


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
        campoDescicao = findViewById(R.id.editTextVeiDescricao);
        btnAdicionar = findViewById(R.id.buttonDocVeiAdicionar);





        campoDataEncontrado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
                calendar = Calendar.getInstance();
                int dia = calendar.get(Calendar.DAY_OF_MONTH);
                int mes = calendar.get(Calendar.MONTH);
                int ano = calendar.get(Calendar.YEAR);


                datePickerDialog = new android.app.DatePickerDialog(AcheiDocVeiculoActivity.this, new android.app.DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String data = "00-00-0000";
                        if ((month+1) < 10 ){
                            data = dayOfMonth + "/0" + (month+1) + "/" + year;
                            if(dayOfMonth <= 9){
                                data ="0"+ dayOfMonth + "/0" + (month+1) + "/" + year;
                            }
                        }else{
                            if(dayOfMonth <= 9){
                                data ="0"+ dayOfMonth + "/" + (month+1) + "/" + year;
                            }
                            data = dayOfMonth + "/" + (month+1) + "/" + year;
                        }


                        campoDataEncontrado.setText(data);
                    }
                }, dia, mes, ano);
                datePickerDialog.getDatePicker().updateDate(ano, mes,dia);

                datePickerDialog.show();
            }
        });


        spinnerAVei = findViewById(R.id.spinnerAcheiVei);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.menuAcheiVei, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerAVei.getBackground().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
        spinnerAVei.setAdapter(adapter);
        spinnerAVei.setOnItemSelectedListener( this );

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvar();
            }
        });

    }


    /**
     * Esconda o teclado
     */
    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
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
        documentoVeiculo.setDescricao(campoDescicao.getText().toString());
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
        ((TextView) parent.getChildAt(0)).setTextSize(18);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
