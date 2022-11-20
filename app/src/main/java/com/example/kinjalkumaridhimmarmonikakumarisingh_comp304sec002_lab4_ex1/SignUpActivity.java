package com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.data.Nurse;
import com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.viewmodels.NurseViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class SignUpActivity extends AppCompatActivity {

    //UI Related
    TextInputEditText userNameEditText;
    TextInputEditText firstNameEditText;
    TextInputEditText lastNameEditText;
    TextInputEditText passwordEditText;
    TextInputEditText confirmPasswordEditText;
    Spinner nurseDeptNamesSpinner;
    Button signUpButton;

    //Nurse Attributes
    int nurseID = -1;
    String firstName = "";
    String lastName = "";
    String department = "";
    String password = "";
    String confirmPassword = "";

    //Database
    NurseViewModel nurseViewModel;
    List<Integer> allNurseIDs;

    //Shared Preferences
    SharedPreferences sharedPreferences;
    SharedPreferences loginSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Initialize UI
        userNameEditText = findViewById(R.id.signup_user_id);
        firstNameEditText = findViewById(R.id.signup_first_name);
        lastNameEditText = findViewById(R.id.signup_last_name);
        nurseDeptNamesSpinner = findViewById(R.id.signup_dept_name_spinner);
        passwordEditText = findViewById(R.id.signup_password);
        confirmPasswordEditText = findViewById(R.id.signup_conf_password);
        signUpButton = findViewById(R.id.sign_button);

        //Initialize repository
        nurseViewModel = new ViewModelProvider(this).get(NurseViewModel.class);

        //Initialize Shared Preferences
        sharedPreferences = this.getSharedPreferences("shared_pref_nurse_id",
                Context.MODE_PRIVATE);
        loginSharedPreferences = this.getSharedPreferences("logged_in_shared_pref",
                Context.MODE_PRIVATE);

        //Spinner
        ArrayAdapter<CharSequence> nurseDeptArray = ArrayAdapter.createFromResource(this,
                R.array.dept_name_array, R.layout.spinner_list_white);
        nurseDeptArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nurseDeptNamesSpinner.setAdapter(nurseDeptArray);
        String[] deptNames = getResources().getStringArray(R.array.dept_name_array);
        nurseDeptNamesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                department = deptNames[i];
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        department = deptNames[0];
        nurseDeptNamesSpinner.setSelection(0);

        //Click Listeners
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(userNameEditText.getText().toString().length() != 0 &&
                        firstNameEditText.getText().toString().length() != 0 &&
                        lastNameEditText.getText().toString().length() != 0 &&
                        passwordEditText.getText().toString().length() != 0 &&
                        confirmPasswordEditText.getText().toString().length() != 0
                ) {
                    if(passwordEditText.getText().toString()
                            .equals(confirmPasswordEditText.getText().toString())) {
                        try {
                            nurseID = Integer.parseInt(userNameEditText.getText().toString());
                            firstName = firstNameEditText.getText().toString();
                            lastName = lastNameEditText.getText().toString();
                            password = passwordEditText.getText().toString();
                            confirmPassword = confirmPasswordEditText.getText().toString();

                            //Begin Async Task to look if nurseID already exists
                            new GetNurseIDsAsyncTask().execute();
                        }catch (NumberFormatException nfe) {
                            Toast.makeText(SignUpActivity.this,
                                    "Nurse user id cannot be a number",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(SignUpActivity.this, "Passwords don't match",
                                Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(SignUpActivity.this, "Fields cannot be left empty",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Async Task
    private class GetNurseIDsAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            allNurseIDs = nurseViewModel.getAllNurseIDs();
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            if(allNurseIDs != null) {
                boolean found = false;
                int index = 0;
                while(index < allNurseIDs.size() && !found) {
                    if(allNurseIDs.get(index) == nurseID) {
                        found = true;
                    }
                    index += 1;
                }

                if(found) {
                    Toast.makeText(SignUpActivity.this, "Nurse ID already exists",
                            Toast.LENGTH_SHORT).show();
                }else{
                    new CreateNurseAccountAsyncTask().execute();
                }
            }else{
                Toast.makeText(SignUpActivity.this, "Unable to create nurse",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
    private class CreateNurseAccountAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            Nurse newNurse = new Nurse(
                    nurseID,
                    firstName,
                    lastName,
                    department,
                    password);
            nurseViewModel.insert(newNurse);
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);

            //Login Successful
            SharedPreferences.Editor isLoggedInEditor = loginSharedPreferences.edit();
            isLoggedInEditor.putBoolean("is_user_logged_in", true);
            isLoggedInEditor.apply();

            //Save nurse user id
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("last_loggedIn_nurse_id", nurseID);
            editor.apply();

            //Successfully Log user in
            Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
            startActivity(intent);
        }
    }
}