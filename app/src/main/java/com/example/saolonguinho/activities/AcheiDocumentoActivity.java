package com.example.saolonguinho.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Camera;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
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
import com.example.saolonguinho.helper.DadosDeUsuarios;
import com.example.saolonguinho.helper.Permissoes;
import com.example.saolonguinho.model.Documento;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class AcheiDocumentoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener{

    //VARIAVEIS LOCAIS PARA RECEBER O QUE VEM DA TELA
    private EditText campoNome, campoCPF, campoRG, campoComentario;
    private EditText campoNomeMae;
    private TextView campoDataEncontrado, campoDataNascimento;
    private Button btnAdicionar;
    private ImageView imagem1, imagem2;
    private List<String> listaFotosRecuperadas = new ArrayList<>();
    private List<String> listaUrlFotos = new ArrayList<>();

    //RECUPERAR O OBJETO STORAGE
    private StorageReference storageReference;
    private AlertDialog alertDialog;

    //PEGAR INSTANCIA DO FIREBASE
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Item");


    //INSTACIAR A CLASSE DOCUMENTO
    private Documento documento = new Documento();
    DadosDeUsuarios dadosDeUsuarios = new DadosDeUsuarios();
    //PEGAR INISTACIA DO FIREBASE AUTH
    private FirebaseAuth firebaseAuth = ConfiguracaoFirebase.getFirebaseAutenticacao();

    private Spinner spinnerADoc;

    private String[] permissoes = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};

    //CONFIGURAÇÃO PARA O CALENDARIO
    Calendar calendar;
    android.app.DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achei_documento);
        getSupportActionBar().hide();


        /**CONFIGURACOES INICIAIS*/
        storageReference = ConfiguracaoFirebase.getFirebaseStorage();

        Permissoes.validarPermissoes(permissoes,this,1);

        //PASSANDO DADOS DA ACTIVITY PARA AS VARIAVEIS LOCAIS
        campoCPF = findViewById(R.id.editTextDocCpf);
        campoDataEncontrado = findViewById(R.id.textViewDocDataEncontrado);
        campoDataNascimento = findViewById(R.id.textViewDocDataNascimento);
        campoNome = findViewById(R.id.editTextDocNomeCompleto);
        campoNomeMae = findViewById(R.id.editTextDocNomeMae);
        campoRG = findViewById(R.id.editTextDocRg);
        campoComentario = findViewById(R.id.editTextDocComentario);
        btnAdicionar = findViewById(R.id.buttonDocAdicionar);


        imagem1 = findViewById(R.id.imageViewDocImagem1);
        imagem2 = findViewById(R.id.imageViewDocImagem2);
        imagem1.setOnClickListener(this);
        imagem2.setOnClickListener(this);

        //criando a mascara para campo cpf
        SimpleMaskFormatter smf = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher mtw = new MaskTextWatcher(campoCPF, smf);
        campoCPF.addTextChangedListener(mtw);
        //fim da mascara

        campoDataEncontrado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                esconderTeclado();
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
                esconderTeclado();
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
                configurarDocumento();
                salvarDocumento();
            }
        });


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
        switch ( v.getId() ){
            case R.id.imageViewDocImagem1 :
                esconderTeclado();
                escolherImagem(1);
                break;
            case R.id.imageViewDocImagem2 :
                esconderTeclado();
                escolherImagem(2);
                break;
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


    public void salvarFotoStorage(String urlString, final int totalFotos, int contador){
        //CRIAR NÓ NO FIREBASE
        StorageReference imagem = storageReference.child("imagens").child(documento.getIdItem()).child("imagem"+contador);

        /**FAZER UPLOAD DAS FOTOS*/
        UploadTask uploadTask = imagem.putFile(Uri.parse(urlString));
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> firebaseUrl = taskSnapshot.getStorage().getDownloadUrl();
                while (!firebaseUrl.isComplete());
                Uri firebase = firebaseUrl.getResult();
                String urlConvertida = firebase.toString();
                listaUrlFotos.add( urlConvertida );

                if( totalFotos == listaUrlFotos.size() ){
                    documento.setFotos( listaUrlFotos );
                    documento.salvar();
                    alertDialog.dismiss();
                    finish();
                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                exibirMensagemDeErro("FALHAR AO FAZER UPLOAD!");
            }
        });
    }

    /**ESCOLHER IMAGEM*/
    public void escolherImagem(int requestCode){
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, requestCode);
    }

    /**CONFIGURAR DOCUMETNO*/
    private void configurarDocumento(){
        String nome = campoNome.getText().toString();
        String cpf = campoCPF.getText().toString();
        String rg = campoRG.getText().toString();
        String dataNascimento = campoDataNascimento.getText().toString();
        String nomeMae = campoNomeMae.getText().toString();
        String dataEncontrado = campoDataEncontrado.getText().toString();
        String comentario = campoComentario.getText().toString();
        String tipo = spinnerADoc.getSelectedItem().toString();
        String idLonguinho = Base64Custon.codificarBase64(firebaseAuth.getCurrentUser().getEmail());
        String ultimaAtualizacao = getDateTime();
        String dataInseridoNoBanco = getDateTime();
        String nomeLonguinho = dadosDeUsuarios.pegarNome();
        documento.setNome(nome);
        documento.setCpf(cpf);
        documento.setRg(rg);
        documento.setDataNascimento(dataNascimento);
        documento.setNomeMae(nomeMae);
        documento.setDataEncontrado(dataEncontrado);
        documento.setComentario(comentario);
        documento.setTipo(tipo);
        documento.setIdLonguinho(idLonguinho);
        documento.setUltimaAtualizacao(ultimaAtualizacao);
        documento.setDataInseridoNoBanco(dataInseridoNoBanco);
        documento.setNomeLonguinho(nomeLonguinho);
    }

    /**EXIBIR MENSAGEM DE ERRO*/
    public void exibirMensagemDeErro(String erro){
        Toast.makeText(AcheiDocumentoActivity.this, erro, Toast.LENGTH_SHORT).show();
    }

    /**SALVAR DOCUMENTO*/
    public void salvarDocumento(){
        alertDialog = new SpotsDialog.Builder().setContext(AcheiDocumentoActivity.this).setMessage("Salvando").build();
        alertDialog.show();

        /**SALVAR IMAGEM NO STORAGE*/
        for( int i = 0; i < listaFotosRecuperadas.size();i++){
            String urlImagem = listaFotosRecuperadas.get(i);
            int tamanhoLista = listaFotosRecuperadas.size();
            salvarFotoStorage(urlImagem, tamanhoLista, i);
        }
    }

    /**Esconda o teclado*/
    public void esconderTeclado() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }


    /**MUDAR IMAGENS NA TELA*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri imagemSelecionada = data.getData();
        String caminhoImagem = imagemSelecionada.toString();

        if(requestCode == 1){
            imagem1.setImageURI(imagemSelecionada);
        }else{
            imagem2.setImageURI(imagemSelecionada);
        }
        listaFotosRecuperadas.add( caminhoImagem );
    }
}
