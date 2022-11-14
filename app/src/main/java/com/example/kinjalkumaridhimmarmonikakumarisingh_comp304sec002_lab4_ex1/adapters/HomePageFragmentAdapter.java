package com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.ViewPatientInfo;
import com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.ViewTestInfo;

import java.util.ArrayList;

public class HomePageFragmentAdapter extends FragmentStateAdapter {

    private final ArrayList<Fragment> fragmentArrayList = new ArrayList<>();

    public HomePageFragmentAdapter(Fragment fragment) {
        super(fragment);
    }

    public HomePageFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position == 1) {
            return new ViewTestInfo();
        }
        return new ViewPatientInfo();
    }

    @Override
    public int getItemCount() {
        //View TestInfo page and View Patient Info page
        return 2;
    }
}
