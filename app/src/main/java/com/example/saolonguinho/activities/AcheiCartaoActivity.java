package com.example.saolonguinho.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import com.example.saolonguinho.model.Cartao;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class AcheiCartaoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //VARIAVEIS LOCAIS PARA RECEBER DADOS DA ACTIVITY
    private EditText campoNome, campo4Digitos, campoBanco, campoDataAchou;
    private Button btnAdicionar;


    //SPINNER
    private Spinner spinnerAC;

    //PEGAR INSTANCIA DO FIREBASE
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Cartoes1");
    //INSTACIAR A CLASSE CARTÃO
    private Cartao cartao;
    //PEGAR INISTACIA DO FIREBASE AUTH
    private FirebaseAuth firebaseAuth;

    private int year, month, day, hour, minute;


    //CONFIGURAÇÃO PARA O CALENDARIO
    Calendar calendar;
    android.app.DatePickerDialog datePickerDialog;

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

        campoDataAchou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
                calendar = Calendar.getInstance();
                int dia = calendar.get(Calendar.DAY_OF_MONTH);
                int mes = calendar.get(Calendar.MONTH);
                int ano = calendar.get(Calendar.YEAR);


                datePickerDialog = new android.app.DatePickerDialog(AcheiCartaoActivity.this, new android.app.DatePickerDialog.OnDateSetListener() {
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


                        campoDataAchou.setText(data);
                    }
                }, dia, mes, ano);
                datePickerDialog.getDatePicker().updateDate(ano, mes,dia);

                datePickerDialog.show();



            }
        });

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvar();
            }
        });

        spinnerAC = findViewById(R.id.spinnerAcheiCartao);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.menuAcheiCartao, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerAC.getBackground().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
        spinnerAC.setAdapter(adapter);
        spinnerAC.setOnItemSelectedListener( this );

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
        cartao.setBancoEmissor(campoBanco.getText().toString());
        cartao.setDataInseridoNoBanco(getDateTime());
        cartao.setDigitos(campo4Digitos.getText().toString());
        cartao.setNome(campoNome.getText().toString().toUpperCase());
        cartao.setDataEncontrado(campoDataAchou.getText().toString());
        cartao.setIdPessoaachou(idUsuario);
        cartao.setTipo(spinnerAC.getSelectedItem().toString());

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

    //ALTERA COR E TAMANHO DO TEXTO DO SPINNER
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
        ((TextView) parent.getChildAt(0)).setTextSize(18);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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

}

