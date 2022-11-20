package com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.viewmodels.NurseViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {
    //UI Related
    TextInputEditText nurseIDEditTextField;
    TextInputEditText passwordEditTextField;
    Button loginButton;
    Button loginSignUpButton;

    //Nurse credentials
    int nurseID;
    String password;

    //Database
    NurseViewModel nurseViewModel;

    //Shared Preferences
    SharedPreferences nurseIdSharedPreferences;
    SharedPreferences loginSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);

        //Initialize UI
        nurseIDEditTextField = findViewById(R.id.nurse_login_id_field);
        passwordEditTextField =  findViewById(R.id.nurse_password_field_id);
        loginButton = findViewById(R.id.login_btn);
        loginSignUpButton = findViewById(R.id.login_signup_btn);

        //Initialize nurse view model
        nurseViewModel = new ViewModelProvider(this).get(NurseViewModel.class);

        //Initialize Shared Preferences
        nurseIdSharedPreferences = this.getSharedPreferences("shared_pref_nurse_id",
                Context.MODE_PRIVATE);
        loginSharedPreferences = this.getSharedPreferences("logged_in_shared_pref",
                Context.MODE_PRIVATE);

        //Get last logged in nurse id
        nurseID = nurseIdSharedPreferences.getInt("last_loggedIn_nurse_id", -1);
        if(nurseID == -1) {
            nurseIDEditTextField.setText("");
        }else{
            nurseIDEditTextField.setText(String.valueOf(nurseID));
        }

        //Click Listeners
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nurseIDEditTextField.getText().toString().length() != 0 &&
                        passwordEditTextField.getText().toString().length() != 0) {
                    try {
                        nurseID = Integer.parseInt(nurseIDEditTextField.getText().toString());
                        password = passwordEditTextField.getText().toString();
                        new AuthenticateNurseTask().execute();
                    }catch (NumberFormatException nfe) {
                        Toast.makeText(LoginActivity.this, "Nurse user ID must be number",
                                Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(LoginActivity.this,
                            "All fields must be filled", Toast.LENGTH_SHORT).show();
                }
            }
        });
        loginSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        //Clear password field
        passwordEditTextField.setText("");
    }

    private class AuthenticateNurseTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            return nurseViewModel.getPasswordForNurseID(nurseID);
        }

        @Override
        protected void onPostExecute(String password) {
            super.onPostExecute(password);

            if(password != null) {
                if(password.equals(LoginActivity.this.password)) {
                    //Login Successful
                    SharedPreferences.Editor isLoggedInEditor = loginSharedPreferences.edit();
                    isLoggedInEditor.putBoolean("is_user_logged_in", true);
                    isLoggedInEditor.apply();

                    //Save nurse ID
                    SharedPreferences.Editor nurseIdEditor = nurseIdSharedPreferences.edit();
                    nurseIdEditor.putInt("last_loggedIn_nurse_id", nurseID);
                    nurseIdEditor.apply();

                    //Sign in user
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(LoginActivity.this,
                            "Incorrect password. Please try again",
                            Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(LoginActivity.this,
                        "Unable to find your account. Check credentials",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

}