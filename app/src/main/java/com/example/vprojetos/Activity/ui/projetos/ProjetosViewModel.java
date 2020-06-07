package com.example.vprojetos.Activity.ui.projetos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProjetosViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ProjetosViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("CONFIG");
    }

    public LiveData<String> getText() {
        return mText;
    }
}