package com.example.crowdzero;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;


public class navbar extends Fragment {

    public static com.example.crowdzero.navbar newInstance()
    {
        com.example.crowdzero.navbar fragment = new com.example.crowdzero.navbar();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navbar, container, false);
    }
}