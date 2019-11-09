package com.example.saolonguinho.ui.achei;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.saolonguinho.R;

public class AcheiFragment extends Fragment {

    private AcheiViewModel acheiViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        acheiViewModel =
                ViewModelProviders.of(this).get(AcheiViewModel.class);
        View root = inflater.inflate(R.layout.fragment_achei, container, false);
        //final TextView textView = root.findViewById(R.id.text_gallery);
        acheiViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        return root;
    }
}