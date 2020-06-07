package com.example.vprojetos.Activity.ui.informacao;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.vprojetos.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InformacaoFragment extends Fragment {

    public InformacaoFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_informacao, container, false);
    }
}
