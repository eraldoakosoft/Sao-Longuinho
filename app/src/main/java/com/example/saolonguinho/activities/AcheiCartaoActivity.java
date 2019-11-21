package com.example.saolonguinho.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.saolonguinho.R;
import com.example.saolonguinho.config.ConfiguracaoFirebase;
import com.example.saolonguinho.helper.Base64Custon;
import com.example.saolonguinho.helper.Permissoes;
import com.example.saolonguinho.model.Cartao;
import com.example.saolonguinho.model.Usuario;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class AcheiCartaoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    //VARIAVEIS LOCAIS PARA RECEBER DADOS DA ACTIVITY
    private EditText campoNome, campo4Digitos, campoBanco, campoDataAchou, campoDescricao;
    private Button btnAdicionar;
    private ImageView imagem1, imagem2;
    private List<String> listaFotosRecuperadas = new ArrayList<>();

    //RECUPERAR O OBJETO STORAGE
    private StorageReference storageReference;


    //SPINNER
    private Spinner spinnerAC;

    //PEGAR INSTANCIA DO FIREBASE
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Modelo");
    //INSTACIAR A CLASSE CARTÃO
    private Cartao cartao;
    //PEGAR INISTACIA DO FIREBASE AUTH
    private FirebaseAuth firebaseAuth;

    private int year, month, day, hour, minute;

    private String[] permissoes = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA

    };

    //OBJETO USUARIO PARA RECEBER DADOS DO BANCO
    private Usuario usuarioDados;


    //CONFIGURAÇÃO PARA O CALENDARIO
    Calendar calendar;
    android.app.DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achei_cartao);
        getSupportActionBar().hide();

        Permissoes.validarPermissoes(permissoes,this,1);

        //CONFIGURAÇÕES INICIAIS
        storageReference = FirebaseStorage.getInstance().getReference();

        //PASANDO DADOS PARA AS VARIAVEIS LOCAIS
        imagem1 = findViewById(R.id.imageViewCarImagem1);
        imagem2 = findViewById(R.id.imageViewCarImagem2);
        imagem2.setOnClickListener(this);
        imagem1.setOnClickListener(this);

        campo4Digitos = findViewById(R.id.editTextCar4Digitos);
        campoBanco = findViewById(R.id.editTextCarBanco);
        campoNome = findViewById(R.id.editTextCarNome);
        campoDataAchou = findViewById(R.id.editTextCarDataAchou);
        campoDescricao = findViewById(R.id.editTextCarDescricao);
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
                        campoDataAchou.setText(formatarData(dayOfMonth, month, year));
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
        //salvarFotoStorage();
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
        cartao.setIdLonguinho(idUsuario);
        cartao.setDescricao(campoDescricao.getText().toString());
        cartao.setTipo(spinnerAC.getSelectedItem().toString());
        cartao.setUltimaAtualizacao(getDateTime());
        cartao.setNomeLonguinho("null");


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
        ((TextView) parent.getChildAt(0)).setTextSize(20);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageViewCarImagem1 :
                escolherImagem(1);
                break;

            case R.id.imageViewCarImagem2 :
                escolherImagem(2);
                break;
        }
    }

    //METODO ESCOLHER IMAGEM
    public void escolherImagem(int requestCode){
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i,requestCode);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("************************* FORA: ");

        //if( requestCode == Activity.RESULT_OK){

            //RECUPERAR IMAGEM
            Uri imagemSelecionada = data.getData();
            String caminhoImagem = imagemSelecionada.toString();
            System.out.println("*************************");
            System.out.println("Caminho " + caminhoImagem);

            if( requestCode == 1 ){
                imagem1.setImageURI(imagemSelecionada);
            }else if ( requestCode == 2 ){
                imagem2.setImageURI(imagemSelecionada);

            }
            listaFotosRecuperadas.add(caminhoImagem);

       // }
    }


    /**
     * METODO PARA SALVAR FOTOS NO FIREBASE
     * */
    public void salvarFotoStorage(String url, int totalFotos, int contador){

        //CRIAR A REFERENCIA STORAGE
        StorageReference imagem = storageReference.child("imagens").child(cartao.getIdCartao()).child("imagem"+contador);

        UploadTask uploadTask = imagem.putFile(Uri.parse(url));
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

            }
        });

    }


    public String formatarData(int dia, int mes, int ano){
        String data = "00/00/0000";
        if ( (mes+1) < 10 && dia < 10 ){
            data = "0" + dia + "/0" + (mes+1) + "/" + ano;
        }else if( (mes+1) < 10 ){
            data = dia + "/0" + (mes+1) + "/" + ano;
        }else if( dia < 10 ){
            data = "0" + dia + "/" + (mes+1) + "/" + ano;
        }

        return data;
    }
}

