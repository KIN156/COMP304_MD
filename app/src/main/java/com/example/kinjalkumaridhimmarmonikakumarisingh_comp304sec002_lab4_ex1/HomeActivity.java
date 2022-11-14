package com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.adapters.HomePageFragmentAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;
    private HomePageFragmentAdapter homePageFragmentAdapter;

    private ViewPatientInfo viewPatientInfoFragment = new ViewPatientInfo();
    private ViewTestInfo viewTestInfoFragment = new ViewTestInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottom_nav_view);
        frameLayout = findViewById(R.id.home_frame_layout);

        bottomNavigationView.setOnItemSelectedListener(this);
        //By Default we have selected patient list
        bottomNavigationView.setSelectedItemId(R.id.patient_list_menu);

//        FragmentManager fragmentManager = getSupportFragmentManager();
//        homePageFragmentAdapter = new HomePageFragmentAdapter(fragmentManager, getLifecycle());
//        homeViewPager2.setAdapter(homePageFragmentAdapter);
//
//        new TabLayoutMediator(tabLayout, homeViewPager2,
//                ((tab, position) -> {
//                    if(position == 0) {
//                        tab.setText(R.string.patients_list);
//                    }else {
//                        tab.setText(R.string.tests_list);
//                    }
//                })
//                ).attach();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.patient_list_menu:
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.home_frame_layout, viewPatientInfoFragment).commit();
                return true;
            case R.id.test_list_menu:
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.home_frame_layout, viewTestInfoFragment).commit();
                return true;
        }
        return false;
    }
}