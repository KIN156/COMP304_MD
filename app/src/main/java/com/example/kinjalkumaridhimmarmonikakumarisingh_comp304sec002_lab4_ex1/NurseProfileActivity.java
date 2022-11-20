package com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.data.Nurse;
import com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.viewmodels.NurseViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class NurseProfileActivity extends AppCompatActivity {

    //UI Related
    TextInputEditText nurseUserIdTextField;
    TextInputEditText nurseFirstNameTextField;
    TextInputEditText nurseLastNameTextField;
    TextInputEditText nurseDepartmentTextField;

    //SharedPreferences;
    SharedPreferences sharedPreferences;

    //Nurse Attributes
    int nurseID;
    String firstName;
    String lastName;
    String departmentName;

    //Database
    NurseViewModel nurseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_profile);

        //Initialize UI
        nurseUserIdTextField = findViewById(R.id.profile_user_id);
        nurseFirstNameTextField = findViewById(R.id.profile_first_name);
        nurseLastNameTextField = findViewById(R.id.profile_last_name);
        nurseDepartmentTextField = findViewById(R.id.profile_dept_name);

        //Get loggedIn nurse user ID from SharedPreferences
        sharedPreferences = getSharedPreferences("shared_pref_nurse_id",
                Context.MODE_PRIVATE);
        nurseID = sharedPreferences.getInt("last_loggedIn_nurse_id", -1);

        //We were able to get nurse ID from shared pref
        if(nurseID == -1) {
            Toast.makeText(this, "Unable to get profile information",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        //Initialize View model
        nurseViewModel = new ViewModelProvider(this).get(NurseViewModel.class);

        //Get Nurse details with nurse ID
        new GetNurseWithNurseID().execute();
    }


    //Async Tasks
    private class GetNurseWithNurseID extends AsyncTask<Void, Void, Nurse> {

        @Override
        protected Nurse doInBackground(Void... voids) {
            return nurseViewModel.findByNurseID(nurseID);
        }

        @Override
        protected void onPostExecute(Nurse nurse) {
            super.onPostExecute(nurse);
            if(nurse != null) {
                firstName = nurse.getFirstName();
                lastName = nurse.getLastName();
                departmentName = nurse.getDepartment();

                nurseUserIdTextField.setText(String.valueOf(nurseID));
                nurseFirstNameTextField.setText(firstName);
                nurseLastNameTextField.setText(lastName);
                nurseDepartmentTextField.setText(departmentName);
            }else{
                Toast.makeText(NurseProfileActivity.this,
                        "Unable to get profile information", Toast.LENGTH_SHORT).show();
            }
        }
    }
}