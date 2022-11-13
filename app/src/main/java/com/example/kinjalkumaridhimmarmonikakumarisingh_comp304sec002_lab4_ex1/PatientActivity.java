package com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.data.Patient;
import com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.viewmodels.PatientViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class PatientActivity extends AppCompatActivity {

    private PatientViewModel patientViewModel;
    private TextInputEditText firstName;
    private TextInputEditText lastName;
    private TextInputEditText department;
    private TextInputEditText nurseID;
    private TextInputEditText room;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        patientViewModel = new ViewModelProvider(this).get(PatientViewModel.class);
    }

    public void createPatientButton(View view) {
        firstName = (TextInputEditText)findViewById(R.id.firstName);
        lastName = (TextInputEditText)findViewById(R.id.lastName);
        department = (TextInputEditText)findViewById(R.id.department);
        nurseID = (TextInputEditText)findViewById(R.id.nurseID);
        room = (TextInputEditText)findViewById(R.id.room);

        if (firstName.getText().toString().length() != 0 &&
                lastName.getText().toString().length() != 0 &&
                department.getText().toString().length() != 0 &&
                nurseID.getText().toString().length() != 0 &&
                room.getText().toString().length() != 0) {

            String firstNameValue = firstName.getText().toString();
            String lastNameValue = lastName.getText().toString();
            String departmentValue = department.getText().toString();
            int nurseIDValue = Integer.parseInt(nurseID.getText().toString());
            String roomValue = room.getText().toString();

            patientViewModel.insert(new
                    Patient(firstNameValue, lastNameValue,
                    departmentValue, nurseIDValue, roomValue));

//            finish();
        }
        else {
            Toast.makeText(PatientActivity.this, "Please ensure there are no null values", Toast.LENGTH_SHORT).show();
        }

    }
}