package com.example.saolonguinho.ui.notificacao;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NotificacaoViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NotificacaoViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText(){
        return mText;
    }
}
