package com.example.saolonguinho.ui.notificacao;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.saolonguinho.R;

public class NotificacaoFragmet extends Fragment {
    private NotificacaoViewModel notificacaoViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        notificacaoViewModel = ViewModelProviders.of(this).get(NotificacaoViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_notificacao, container, false);

        return root;

    }

}
