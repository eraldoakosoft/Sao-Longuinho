package com.example.saolonguinho.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.saolonguinho.MainActivity;
import com.example.saolonguinho.R;
import com.example.saolonguinho.config.ConfiguracaoFirebase;
import com.example.saolonguinho.helper.Base64Custon;
import com.example.saolonguinho.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CadastroActivity extends AppCompatActivity {


    //VARIAVEIS PARA PEGAR INFORMÇÕES SOBRE O USUARIO
    private EditText campoCPF, campoRG, campoTelefone, campoDataNascimento;
    private EditText campoNome, campoEmail, campoSenha;
    private EditText campoConfirmarSenha, campoCEP, campoNomeMae;

    //BOTÃO PARA DAR O EVENTO DE CADASTRO
    private Button btnCastrar;

    //VARIAVEL PARA PEGAR AUTENTICAÇÃO COM O FIREBASE, PEGAR INSTNACIA
    private static FirebaseAuth firebaseAuth;

    //CLASSE USUARIO, OBJETO QUE VAI PARA O BANCO
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);



        //PEGAR OS DADOS DO CAMPO DA ACTIVITY E PASSAR PARA AS VARIAVEIS LOCAIS
        campoCPF = findViewById(R.id.editTextCPF);
        campoRG = findViewById(R.id.editTextRG);
        campoTelefone = findViewById(R.id.editTextTelefone);
        campoNome = findViewById(R.id.editTextNome);
        campoEmail = findViewById(R.id.editTextEmai);
        campoSenha = findViewById(R.id.editTextSenha);
        campoConfirmarSenha = findViewById(R.id.editTextConfirmaSenha);
        campoNomeMae = findViewById(R.id.editTextNomeMae);
        campoCEP = findViewById(R.id.editTextCEP);
        campoDataNascimento = findViewById(R.id.editTextDataNascimento);
        btnCastrar = findViewById(R.id.buttonCadastrar);


        btnCastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //PASSANDO TODOS OS DADOS PARA AS VARIAVEIS DO TIPO STRING
                String textoNome = campoNome.getText().toString();
                String textoCEP = campoCEP.getText().toString();
                String textoRG = campoRG.getText().toString();
                String textoTelefone = campoTelefone.getText().toString();
                String textoEmail = campoEmail.getText().toString();
                String textoSenha = campoSenha.getText().toString();
                String textoConfirmarSenha = campoConfirmarSenha.getText().toString();
                String textoNomeMae = campoNomeMae.getText().toString();
                String textoCPF = campoCPF.getText().toString();
                String textoDataNascimento = campoDataNascimento.getText().toString();


                //INSTANCIANDO UM NOVO USUARIO PARA RECEBER OS DADOS DA ACTIVITY
                usuario = new Usuario();

                //VERIFICAR SE OS CAMPOS MAIS IMPORTANTES ESTÃO PREENCHIDOS
                if(!textoNome.isEmpty()){
                    if(!textoCEP.isEmpty()){
                        if(!textoRG.isEmpty()){
                            if (!textoTelefone.isEmpty()){
                                if(!textoEmail.isEmpty()){
                                    if(!textoSenha.isEmpty()){
                                        if(!textoConfirmarSenha.isEmpty()){
                                            if(!textoNomeMae.isEmpty()){
                                                if(!textoCPF.isEmpty()){
                                                    //VERIFICAR SE AS SENHAS SÃO IGUAIS
                                                    //if(textoSenha == textoConfirmarSenha) {
                                                        //SE TODOS OS CAMPOS ESTIVEREM PREENCHIDOS
                                                        //VAMOS PASSAR OS DADOS PARA O OBJETO USUARIO
                                                        usuario.setNome(textoNome);
                                                        usuario.setCep(textoCEP);
                                                        usuario.setRg(textoRG);
                                                        usuario.setTelefone(textoTelefone);
                                                        usuario.setEmail(textoEmail);
                                                        usuario.setSenha(textoSenha);
                                                        usuario.setNomeDaMae(textoNomeMae);
                                                        usuario.setCpf(textoCPF);
                                                        usuario.setStatus(true);
                                                        usuario.setDataCadastroNoBanco(getDateTime());
                                                        usuario.setDataNascimento(textoDataNascimento);
                                                        cadastrarUsuario();
                                                    //}else{
                                                        //Toast.makeText(CadastroActivity.this, "AS SENHA ESTÃO DIFERENTES", Toast.LENGTH_SHORT).show();
                                                    //}
                                                }else {
                                                    Toast.makeText(CadastroActivity.this, "Preencha o CPF", Toast.LENGTH_SHORT).show();
                                                }
                                            }else {
                                                Toast.makeText(CadastroActivity.this, "Preencha o Nome da Mãe", Toast.LENGTH_SHORT).show();
                                            }
                                        }else{
                                            Toast.makeText(CadastroActivity.this, "Preencha a Confirmação da Senha", Toast.LENGTH_SHORT).show();
                                        }
                                    }else{
                                        Toast.makeText(CadastroActivity.this, "Preencha a Senha", Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    Toast.makeText(CadastroActivity.this, "Preencha o Email", Toast.LENGTH_SHORT).show();
                                }
                            }else
                                Toast.makeText(CadastroActivity.this, "Preencha o Telefone", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(CadastroActivity.this, "Preencha o RG", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(CadastroActivity.this, "Preencha o CEP", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(CadastroActivity.this, "Preencha o Nome", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }

    //METODO PARA CADASTRAR USUARIO
    public void cadastrarUsuario(){
        firebaseAuth = ConfiguracaoFirebase.getFirebaseAutenticacao();
        firebaseAuth.createUserWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()) {
                    // ESSA VARIAVEL VAI RECEBER O EMAIL CRIPTOGRAFADO PARA USAR COMO PRIMARY KRY NO FIREBASE
                    String idUsuario = Base64Custon.codificarBase64(usuario.getEmail());
                    usuario.setIdUsuario(idUsuario);
                    usuario.salvar();
                    abrirTelaPrincipal();
                }else{
                    //TRATAR ERROS
                    //VARIAVEL QUE VAI RECEBER A MENSAGEM DE EXCECAÇÃO
                    String excecao = "Null";
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException ex){
                        excecao = "DIGITE UMA SENHA MAIS FORTE";
                    } catch (FirebaseAuthInvalidCredentialsException ex){
                        excecao = "POR FAVOR, DIGITE UM EMAIL VÁLIDO";

                    } catch (FirebaseAuthUserCollisionException ex){
                        excecao = "ESTÁ CONTA JÁ EXISTE!";
                    } catch (Exception ex) {
                        excecao = "ERRO AO CADASTRAR USUARIO!" + ex.getMessage();
                        ex.printStackTrace();
                    }
                    Toast.makeText(CadastroActivity.this, excecao, Toast.LENGTH_SHORT).show();;
                }

            }
        });
    }

    //METODO PARA RETORNAR DATA E HORA DO SISTEMA
    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    //METODO PARA ABRIA A TELA PRINCIPAL
    public void abrirTelaPrincipal(){
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }


    //BOTÃO VOLTAR
    @Override
    public void onBackPressed(){

        startActivity(new Intent(CadastroActivity.this, LoginActivity.class));
        finish(); // Finaliza a Activity atual

        return;
    }

}
