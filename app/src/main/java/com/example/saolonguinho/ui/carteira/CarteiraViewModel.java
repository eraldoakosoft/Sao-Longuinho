package com.example.saolonguinho.ui.carteira;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CarteiraViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CarteiraViewModel() {

    }

    public LiveData<String> getText() {
        return mText;
    }
}