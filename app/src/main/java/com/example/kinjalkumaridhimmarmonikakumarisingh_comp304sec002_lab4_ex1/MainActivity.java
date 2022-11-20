package com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    boolean isLoggedIn;
    int nurseId;

    SharedPreferences loggedInSharedPreferences;
    SharedPreferences nurseIdSharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize SharePreferences
        loggedInSharedPreferences = getSharedPreferences("logged_in_shared_pref",
                Context.MODE_PRIVATE);
        nurseIdSharedPreferences = getSharedPreferences("shared_pref_nurse_id",
                Context.MODE_PRIVATE);

        //Get last logged in boolean
        isLoggedIn = loggedInSharedPreferences.getBoolean("is_user_logged_in", false);
        //Get last logged in nurse id
        nurseId = nurseIdSharedPreferences.getInt("last_loggedIn_nurse_id", -1);

        Intent intent;
        //Login only if you have last logged in nurse id, and the boolean for is logged in
        //is turned on
        if(isLoggedIn && nurseId != -1) {
            //Send user to HomeActivity
            intent = new Intent(MainActivity.this, HomeActivity.class);
        }else{
            //Send user to LoginActivity
            intent = new Intent(MainActivity.this, LoginActivity.class);
        }
        startActivity(intent);
        finish();
    }
}