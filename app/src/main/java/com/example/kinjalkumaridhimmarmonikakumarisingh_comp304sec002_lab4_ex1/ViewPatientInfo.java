package com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.adapters.HomePageFragmentAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ViewPatientInfo extends Fragment {

    HomePageFragmentAdapter homePageFragmentAdapter;
    ViewPager2 viewPager2;

    public ViewPatientInfo() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.println("Patients Info");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_view_patient_info, container, false);
        return root;
    }
}