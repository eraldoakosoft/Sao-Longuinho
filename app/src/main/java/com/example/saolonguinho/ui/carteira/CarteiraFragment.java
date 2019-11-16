package com.example.saolonguinho.ui.carteira;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.saolonguinho.R;
import com.example.saolonguinho.activities.CarteiraActivity;

public class CarteiraFragment extends Fragment {

    //VARIAVEL LOCAL PARA RECEBER DADOS DO BOTÃO
    private Button btnNovo;

    private CarteiraViewModel carteiraViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        carteiraViewModel =
                ViewModelProviders.of(this).get(CarteiraViewModel.class);
        View root = inflater.inflate(R.layout.fragment_carteira, container, false);

        //PASSANDO DADOS PARA VARIAVEL LOCAL
        btnNovo = root.findViewById(R.id.buttonNovo);

        //ADICIONADO EVENTO DE CLICK PARA O BOTAO "NOVO", VAI ABRI A TELA DE CADASTRAR UM NOVO DOCUMENTO
        btnNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), CarteiraActivity.class));
            }
        });


        return root;
    }
}