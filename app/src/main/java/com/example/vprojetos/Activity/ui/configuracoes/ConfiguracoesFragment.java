package com.example.vprojetos.Activity.ui.configuracoes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vprojetos.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfiguracoesFragment extends Fragment {

    public ConfiguracoesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_configuracoes, container, false);
    }
}
