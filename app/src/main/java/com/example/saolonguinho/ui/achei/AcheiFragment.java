package com.example.saolonguinho.ui.achei;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.saolonguinho.R;
import com.example.saolonguinho.activities.AcheiCartaoActivity;
import com.example.saolonguinho.activities.AcheiDocVeiculoActivity;
import com.example.saolonguinho.activities.AcheiDocumentoActivity;

public class AcheiFragment extends Fragment {

    private AcheiViewModel acheiViewModel;

    private Button btnDocumento, btnDocVeiculo, btnCartao;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        acheiViewModel =
                ViewModelProviders.of(this).get(AcheiViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_achei, container, false);

        btnCartao = root.findViewById(R.id.buttonCartao);
        btnDocumento = root.findViewById(R.id.buttonDocumento);
        btnDocVeiculo = root.findViewById(R.id.buttonDocVeiculo);

        //EVENTO DE CLICK PARA O BOTÃO CARTÃO
        btnCartao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(root.getContext(), AcheiCartaoActivity.class));
            }
        });

        //EVENTO DE CLICK PARA O BOTÃO DOCUMENTO
        btnDocumento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(root.getContext(), AcheiDocumentoActivity.class));
            }
        });
        //EVENTO DE CLICK PARA O BOTÃO DOCUMENTO VEICULO
        btnDocVeiculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(root.getContext(), AcheiDocVeiculoActivity.class));
            }
        });

        return root;
    }
}