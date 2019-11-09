package com.example.saolonguinho.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saolonguinho.MainActivity;
import com.example.saolonguinho.R;
import com.example.saolonguinho.config.ConfiguracaoFirebase;
import com.example.saolonguinho.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    //VARIAVEIS QUE VAI RECEBER OS DADOS DA ACTIVITY
    private EditText campoEmail, campoSenha;
    private TextView textCadastrar;
    private Button btnLoginEntrar;

    //INSTANCIA DA CLASSE USUARIO
    private Usuario usuario;

    //INSTANCIA DO FIREBASE
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //PEGANDO OS DADOS DA ACTIVITY E OS COLOCANDO NAS VARIAVEIS
        campoEmail = findViewById(R.id.editTextLoginEmai);
        campoSenha = findViewById(R.id.editTextLoginSenha);
        textCadastrar = findViewById(R.id.textViewLoginCadastar);
        btnLoginEntrar = findViewById(R.id.buttonLoginEntar);

        //FAZER LOGIN
        btnLoginEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //VALIDAR CAMPOS
                String textoEmail = campoEmail.getText().toString();
                String textoSenha = campoSenha.getText().toString();
                if(!textoEmail.isEmpty()){
                    if(!textoSenha.isEmpty()){
                        usuario = new Usuario();
                        usuario.setEmail(textoEmail);
                        usuario.setSenha(textoSenha);
                        fazerLogin();
                    }else{
                        Toast.makeText(LoginActivity.this, "DIGITE A SENHA", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(LoginActivity.this, "DIGITE UM EMAIL", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //ADICIONANDO EVENTO DE CLICK PARA ABRIR A TELA DE CADASTRO DE USUARIO
        textCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, CadastroActivity.class));
                finish();
            }
        });
    }


    //METODO PARA FAZER LOGIN
    public void fazerLogin(){
        firebaseAuth = ConfiguracaoFirebase.getFirebaseAutenticacao();
        firebaseAuth.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //SE O LOGIN TIVER SUCESSO VAI ABRI A TELA PRINCIPAL
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, "ERRO AO FAZER LOGIN", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
