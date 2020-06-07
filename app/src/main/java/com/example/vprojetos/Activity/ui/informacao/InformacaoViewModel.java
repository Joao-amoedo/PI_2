package com.example.vprojetos.Activity.ui.informacao;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InformacaoViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public InformacaoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("INFORMAÇÃO");
    }

    public LiveData<String> getText() {
        return mText;
    }
}