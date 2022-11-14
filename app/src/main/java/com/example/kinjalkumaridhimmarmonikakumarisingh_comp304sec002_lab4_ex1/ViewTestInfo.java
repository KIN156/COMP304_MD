package com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ViewTestInfo extends Fragment {

    public ViewTestInfo() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.println("Tests Info");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_view_test_info, container, false);
        // Inflate the layout for this fragment

        return root;
    }
}