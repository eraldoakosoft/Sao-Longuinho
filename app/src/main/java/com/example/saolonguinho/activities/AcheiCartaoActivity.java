package com.example.saolonguinho.activities;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.Manifest;
import android.app.AlertDialog;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import dmax.dialog.SpotsDialog;


public class AcheiCartaoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    //VARIAVEIS LOCAIS PARA RECEBER DADOS DA ACTIVITY
    private EditText campoNome, campo4Digitos, campoBanco, campoDescricao;
    private TextView campoDataAchou;
    private Button btnAdicionar;
    private ImageView imagem1, imagem2;
    private List<String> listaFotosRecuperadas = new ArrayList<>();
    private List<String> listaURLFotos = new ArrayList<>();

    //RECUPERAR O OBJETO STORAGE
    private StorageReference storageReference;
    private AlertDialog alertDialog;

    //SPINNER
    private Spinner spinnerAC;

    //PEGAR INSTANCIA DO FIREBASE
    //private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Item");

    //PEGAR INISTACIA DO FIREBASE AUTH
    private FirebaseAuth firebaseAuth  = ConfiguracaoFirebase.getFirebaseAutenticacao();

    private String[] permissoes = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA };


    //CONFIGURAÇÃO PARA O CALENDARIO
    Calendar calendar;
    android.app.DatePickerDialog datePickerDialog;

    //
    Cartao cartao = new Cartao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achei_cartao);
        getSupportActionBar().hide();

        //CONFIGURAÇÕES INICIAIS
        storageReference = ConfiguracaoFirebase.getFirebaseStorage();

        Permissoes.validarPermissoes(permissoes,this,1);

        //PASANDO DADOS PARA AS VARIAVEIS LOCAIS
        imagem1 = findViewById(R.id.imageViewCarImagem1);
        imagem2 = findViewById(R.id.imageViewCarImagem2);
        imagem2.setOnClickListener(this);
        imagem1.setOnClickListener(this);

        campo4Digitos = findViewById(R.id.editTextCar4Digitos);
        campoBanco = findViewById(R.id.editTextCarBanco);
        campoNome = findViewById(R.id.editTextCarNome);
        campoDataAchou = findViewById(R.id.textViewCarDataEncontrado1);
        campoDescricao = findViewById(R.id.editTextCarDescricao);
        btnAdicionar = findViewById(R.id.buttonCarAdicionar);



        campoDataAchou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int dia = calendar.get(Calendar.DAY_OF_MONTH);
                int mes = calendar.get(Calendar.MONTH);
                int ano = calendar.get(Calendar.YEAR);
                datePickerDialog = new android.app.DatePickerDialog(AcheiCartaoActivity.this, new android.app.DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) { campoDataAchou.setText(formatarData(dayOfMonth, month, year));
                    }
                }, dia, mes, ano);
                datePickerDialog.getDatePicker().updateDate(ano, mes,dia);
                datePickerDialog.show();
            }
        });


        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarDados();
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
    public void salvarCartao(){

        alertDialog = new SpotsDialog.Builder()
                .setContext(AcheiCartaoActivity.this)
                .setMessage("Salvando").build();
        alertDialog.show();

        /**SALVAR IMAGEM NO STORAGE*/
        for( int i = 0; i < listaFotosRecuperadas.size(); i++ ){
            String urlImagem = listaFotosRecuperadas.get(i);
            int tamanhoLista = listaFotosRecuperadas.size();
            salvarFotoStorage(urlImagem, tamanhoLista, i);
        }
    }




    /**CONFIGURAR O CARTÃO*/
    private void configurarCartao(){
        String nome = campoNome.getText().toString().toUpperCase();
        String banco = campoBanco.getText().toString();
        String digitos = campo4Digitos.getText().toString();
        String dataEncontrado = campoDataAchou.getText().toString();
        String comentario = campoDescricao.getText().toString();
        String tipo = spinnerAC.getSelectedItem().toString();
        String idLonguinho = Base64Custon.codificarBase64( firebaseAuth.getCurrentUser().getEmail());
        String ultimaAtualizacao = getDateTime();
        String dataInseridoNoBanco = getDateTime();
        cartao.setNome(nome);
        cartao.setBancoEmissor(banco);
        cartao.setDigitos(digitos);
        cartao.setDataEncontrado(dataEncontrado);
        cartao.setDescricao(comentario);
        cartao.setTipo(tipo);
        cartao.setDataInseridoNoBanco(dataInseridoNoBanco);
        cartao.setIdLonguinho(idLonguinho);
        cartao.setUltimaAtualizacao(ultimaAtualizacao);

      }


    /**VALIDAR CAMPOS*/
    public void validarDados(){
        String nome = campoNome.getText().toString().toUpperCase();
        String banco = campoBanco.getText().toString();
        String digitos = campo4Digitos.getText().toString();
        String dataEncontrado = campoDataAchou.getText().toString();
        String comentario = campoDescricao.getText().toString();

        if( listaFotosRecuperadas.size() != 0 ){
            if( !nome.isEmpty() ){
                if( !banco.isEmpty() ){
                    if( !digitos.isEmpty() ){
                        if( !dataEncontrado.isEmpty() ){
                            if( !comentario.isEmpty() ){
                                configurarCartao();
                                salvarCartao();

                            }else {
                                exibirMensagemDeErro("Preencha o Comentario!");
                            }
                        }else {
                            exibirMensagemDeErro("Preencha a Data Encontrado!");
                        }
                    }else {
                        exibirMensagemDeErro("Preencha os Ultimos Digitos!");
                    }
                }else {
                    exibirMensagemDeErro("Preencha o Banco/Emissor");
                }
            }else {
                exibirMensagemDeErro("Preencha o Nome!");
            }
        }else {
            exibirMensagemDeErro("Selecione uma foto!");
        }

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


        //if( requestCode == Activity.RESULT_OK){

            //RECUPERAR IMAGEM
            Uri imagemSelecionada = data.getData();
            String caminhoImagem = imagemSelecionada.toString();

            if( requestCode == 1 ){
                imagem1.setImageURI(imagemSelecionada);
            }else if ( requestCode == 2 ){
                imagem2.setImageURI(imagemSelecionada);

            }
            listaFotosRecuperadas.add( caminhoImagem );

       // }
    }


    /** METODO PARA SALVAR FOTOS NO FIREBASE*/
    public void salvarFotoStorage(String urlString, final int totalFotos, int contador){

        //CRIAR NÓ NO FIREBASE
        StorageReference imagem = storageReference.child("imagens").child(cartao.getIdItem()).child("imagem"+contador);

        /*FAZER UPLOAD DO DAS FOTOS*/
        UploadTask uploadTask = imagem.putFile(Uri.parse(urlString));
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> firebaseUrl = taskSnapshot.getStorage().getDownloadUrl();
                while (!firebaseUrl.isComplete());
                Uri firebase = firebaseUrl.getResult();
                String urlConvertida = firebase.toString();
                listaURLFotos.add( urlConvertida );

                if ( totalFotos == listaURLFotos.size() ){
                    cartao.setFotos(listaURLFotos);
                    cartao.salvar();
                    alertDialog.dismiss();
                    finish();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                exibirMensagemDeErro("FALHA AO FAZER UPLOAD!");
                //System.out.println("FIREBASE : FALHA AO SALVAR IMAGEM !! ");
            }
        });

    }

    public String formatarData(int dia, int mes, int ano){
        String data;
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

    /**METODO PARA MOSTRAR MENSAGENS DE ERRO*/
    public void exibirMensagemDeErro(String msm){
        Toast.makeText(AcheiCartaoActivity.this, msm, Toast.LENGTH_SHORT).show();
    }
}

