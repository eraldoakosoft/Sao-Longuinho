package com.example.saolonguinho.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
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
import com.example.saolonguinho.model.Documento;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AcheiDocumentoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener{

    //VARIAVEIS LOCAIS PARA RECEBER O QUE VEM DA TELA
    private EditText campoNome, campoCPF, campoRG, campoDataNascimento;
    private EditText campoNomeMae, campoNaturalidade, campoDataEncontrado;
    private Button btnAdicionar;

    //PEGAR INSTANCIA DO FIREBASE
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Item");


    //INSTACIAR A CLASSE DOCUMENTO
    private Documento documento;
    //PEGAR INISTACIA DO FIREBASE AUTH
    private FirebaseAuth firebaseAuth;

    private Spinner spinnerADoc;

    //CONFIGURAÇÃO PARA O CALENDARIO
    Calendar calendar;
    android.app.DatePickerDialog datePickerDialog;

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

        //criando a mascara para campo cpf
        SimpleMaskFormatter smf = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher mtw = new MaskTextWatcher(campoCPF, smf);
        campoCPF.addTextChangedListener(mtw);
        //fim da mascara

        campoDataEncontrado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
                calendar = Calendar.getInstance();
                int dia = calendar.get(Calendar.DAY_OF_MONTH);
                int mes = calendar.get(Calendar.MONTH);
                int ano = calendar.get(Calendar.YEAR);


                datePickerDialog = new android.app.DatePickerDialog(AcheiDocumentoActivity.this, new android.app.DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        campoDataEncontrado.setText(formatarData(dayOfMonth, month,year));
                    }
                }, dia, mes, ano);
                datePickerDialog.getDatePicker().updateDate(ano, mes,dia);
                datePickerDialog.show();

            }
        });

        campoDataNascimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
                calendar = Calendar.getInstance();
                int dia = calendar.get(Calendar.DAY_OF_MONTH);
                int mes = calendar.get(Calendar.MONTH);
                int ano = calendar.get(Calendar.YEAR);


                datePickerDialog = new android.app.DatePickerDialog(AcheiDocumentoActivity.this, new android.app.DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        campoDataNascimento.setText(formatarData(dayOfMonth, month, year));
                    }
                }, dia, mes, ano);
                datePickerDialog.getDatePicker().updateDate(ano, mes,dia);
                datePickerDialog.show();

            }
        });


        spinnerADoc = findViewById(R.id.spinnerAcheiDoc);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.menuAcheiDoc, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerADoc.getBackground().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
        spinnerADoc.setAdapter(adapter);
        spinnerADoc.setOnItemSelectedListener( this );



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
        documento.setDataEncontrado(campoDataEncontrado.getText().toString());
        documento.setDataNascimento(campoDataNascimento.getText().toString());
        documento.setNaturalidade(campoNaturalidade.getText().toString());
        documento.setRg(campoRG.getText().toString());
        documento.setNomeMae(campoNomeMae.getText().toString());
        documento.setNome(campoNome.getText().toString());
        documento.setDataInseridoNoBanco(getDateTime());
        documento.setUltimaAtualizacao(getDateTime());
        documento.setIdLonguinho(idUsuario);
        documento.setTipo(spinnerADoc.getSelectedItem().toString());

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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
        ((TextView) parent.getChildAt(0)).setTextSize(20);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {

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


    public String formatarData(int dia, int mes, int ano){
        String data = "00/00/0000";
        if ( (mes+1) < 10 && dia < 10 ){
            data = "0" + dia + "/0" + (mes+1) + "/" + ano;
        }else if( (mes+1) < 10 ){
            data = dia + "/0" + (mes+1) + "/" + ano;
        }else if( dia < 10 ){
            data = "0" + dia + "/" + (mes+1) + "/" + ano;
        }else {
            data = dia + "/" + (mes+1) + "/" + ano;
        }

        return data;
    }
}
