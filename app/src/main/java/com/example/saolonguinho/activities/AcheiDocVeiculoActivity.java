package com.example.saolonguinho.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import com.example.saolonguinho.helper.DadosDeUsuarios;
import com.example.saolonguinho.model.DocumentoVeiculo;
import com.example.saolonguinho.model.Modelo;
import com.example.saolonguinho.model.Usuario;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class AcheiDocVeiculoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener{

    //VARIAVEIS LOCAIS PARA RECEBER DADOS DAS ACTIVITY
    private EditText campoNome, campoCPF, campoPlaca, campoModelo, campoComentario;
    private TextView  campoDataEncontrado;
    private ImageView imagem1, imagem2;
    private Button btnAdicionar;

    private Spinner spinnerAVei;

    //INSTACIA DO FIREBASE
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Usuarioss");

    //INSTANCIA PARA AUTENTICAÇÃO DO FIREBASE
    FirebaseAuth firebaseAuth = ConfiguracaoFirebase.getFirebaseAutenticacao();
    //INSTANCIA CLASSE DOCUMENTO VEICULO
    DocumentoVeiculo documentoVei = new DocumentoVeiculo();
    DadosDeUsuarios dadosDeUsuarios = new DadosDeUsuarios();

    //CONFIGURAÇÃO PARA O CALENDARIO
    Calendar calendar;
    android.app.DatePickerDialog datePickerDialog;

    private List<String> listaFotosRecuperadas = new ArrayList<>();
    private List<String> listaURLFotos = new ArrayList<>();
    private List<Usuario> listaUsuarios = new ArrayList<>();

    private StorageReference storageReference1;

    private AlertDialog alertDialog;
    private AlertDialog alertDialog2;

    private Modelo modelo = new Modelo();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achei_doc_veiculo);
        getSupportActionBar().hide();

        storageReference1 = ConfiguracaoFirebase.getFirebaseStorage();

        campoCPF = findViewById(R.id.textViewDocVeiCPF);
        campoNome = findViewById(R.id.textViewDocVeiNomeCompleto);
        campoPlaca = findViewById(R.id.textViewDocVeiPlaca);
        campoDataEncontrado = findViewById(R.id.textViewDocVeiDataEncontrado);
        campoModelo = findViewById(R.id.textViewDocVeiModelo);
        campoComentario = findViewById(R.id.editTextVeiDescricao);
        btnAdicionar = findViewById(R.id.buttonDocVeiAdicionar);

        imagem1 = findViewById(R.id.imageViewVeiImagem1);
        imagem2 = findViewById(R.id.imageViewVeiImagem2);
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


                datePickerDialog = new android.app.DatePickerDialog(AcheiDocVeiculoActivity.this, new android.app.DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        formatarData(dayOfMonth, month, year);
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
                configurarDocVei();
                salvarDocVei();
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
        ((TextView) parent.getChildAt(0)).setTextSize(18);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    public void formatarData(int dia, int mes, int ano){
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

        campoDataEncontrado.setText(data);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageViewVeiImagem1 :
                esconderTeclado();
                escolherImgagem(1);
                break;

            case R.id.imageViewVeiImagem2 :
                esconderTeclado();
                escolherImgagem(2);
                break;
        }

    }

    /**Esconda o teclado*/
    public void esconderTeclado() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**MOSTRAR MENSAGEM DE ERRO*/
    public void exibirMensagemDeErro(String erro){
        Toast.makeText(AcheiDocVeiculoActivity.this, erro,Toast.LENGTH_SHORT).show();
    }

    /**CONFIGURAR O DOCUMENTO DE VEICULO*/
    public void configurarDocVei(){
        String nome = campoNome.getText().toString();
        String cpf = campoCPF.getText().toString();
        String placa = campoPlaca.getText().toString();
        String marca = campoModelo.getText().toString();
        String dataEncontrado = campoDataEncontrado.getText().toString();
        String comentario = campoComentario.getText().toString();
        String idLonguinho = Base64Custon.codificarBase64( firebaseAuth.getCurrentUser().getEmail() );
        String tipo = spinnerAVei.getSelectedItem().toString();
        String nomeLonguinho = dadosDeUsuarios.pegarNome();
        documentoVei.setNome(nome);
        documentoVei.setCpf(cpf);
        documentoVei.setPlaca(placa);
        documentoVei.setModelo(marca);
        documentoVei.setDataEncontrado(dataEncontrado);
        documentoVei.setComentario(comentario);
        documentoVei.setDataInseridoNoBanco(getDateTime());
        documentoVei.setUltimaAtualizacao(getDateTime());
        documentoVei.setIdLonguinho(idLonguinho);
        documentoVei.setTipo(tipo);
        documentoVei.setNomeLonguinho(nomeLonguinho);

        modelo.setComentario(comentario);
        modelo.setDataEncontrado(dataEncontrado);
        modelo.setDataInseridoNoBanco(getDateTime());
        modelo.setUltimaAtualizacao(getDateTime());
        modelo.setNome(nome);
        modelo.setNomeLonguinho(nomeLonguinho);
        modelo.setTipo("Documento Veiculo: "+tipo);
        modelo.setIdItem(documentoVei.getIdItem());
        modelo.setStatus(true);


        buscarCPF(cpf);

    }

    /**ESCOLHER UMA IMAGEM*/
    public void escolherImgagem(int requestCode){
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri imagemSelecionada = data.getData();
        String caminhoImagem = imagemSelecionada.toString();

        if ( requestCode == 1 ){
            imagem1.setImageURI( imagemSelecionada );
        }else if ( requestCode == 2 ){
            imagem2.setImageURI( imagemSelecionada );
        }
        listaFotosRecuperadas.add( caminhoImagem );
    }

    /** METODO PARA SALVAR FOTOS NO FIREBASE*/
    public void salvarFotoStorage(String urlString, final int totalFotos, int contador){

        StorageReference imagem = storageReference1.child("imagens").child(documentoVei.getIdItem()).child("imagem"+contador);


        /** FAZER UPLOAD DAS FOTOS */
        UploadTask uploadTask = imagem.putFile(Uri.parse(urlString));
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> firebaseUrl = taskSnapshot.getStorage().getDownloadUrl();
                while (!firebaseUrl.isComplete());
                Uri firebase = firebaseUrl.getResult();
                String urlConvertida = firebase.toString();
                listaURLFotos.add( urlConvertida );

                if( totalFotos == listaURLFotos.size() ){
                    documentoVei.setFotos( listaURLFotos );
                    documentoVei.salvar();
                    modelo.setFotos( listaURLFotos );
                    modelo.salvarModelo();
                    alertDialog.dismiss();
                    notificarBanco();
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                exibirMensagemDeErro("FALHA AO FAZER UPLOAD!");
            }
        });
    }

    /**METODO PARA SALVAR*/
    public void salvarDocVei(){

        alertDialog = new SpotsDialog.Builder().setContext(AcheiDocVeiculoActivity.this).setMessage("Salvando").build();
        alertDialog.show();

        /**SALVAR NO STORAGE*/
        for( int i=0; i < listaFotosRecuperadas.size(); i++ ){
            String urlImagem = listaFotosRecuperadas.get(i);
            int tamenhoLista = listaFotosRecuperadas.size();
            salvarFotoStorage(urlImagem, tamenhoLista, i);
        }
    }

    /**MEDOTO PARA VERIFICAR SE TEM ALGUEM IGUAL AO QUE ESTA SENDO ADICIONADO USANDO CPF*/
    public void buscarCPF(String cpf){
        Query pesquisaCpf = reference.orderByChild("cpf").equalTo(cpf);

            pesquisaCpf.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    listaUsuarios.clear();
                    for( DataSnapshot ds: dataSnapshot.getChildren() ){
                        listaUsuarios.add( ds.getValue(Usuario.class) );
                    }
                    System.out.println("++++++++++++++++++++++++++++++++++++++++++++");
                    System.out.println("Tamano da Lista: " + listaUsuarios.size());
                    //notificarBanco();
                    //notify();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }

            });



    }

    /**METODO PARA NOTIFICAR O USUARIO DONO DO OBJETO ENCONTRADO*/
    public void notificarBanco(){
        Usuario user = new Usuario();

        if (!listaUsuarios.isEmpty()) {
            for (int i = 0; i < listaUsuarios.size(); i++) {
                user = listaUsuarios.get(i);
            }
            user.setNotificacao("Achei o seu CRVL");
            reference.child(user.getIdUsuario()).setValue(user);
            System.out.println("------------------------");
            System.out.println("CPF: " + user.getCpf());

        }else{
            System.out.println("------------------------");
            System.out.println("Lista Vazia");

        }



    }

}
