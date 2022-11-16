package com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.constants.Constants;
import com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.data.Patient;
import com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.viewmodels.PatientViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class UpdateInfoActivity extends AppCompatActivity {

    Button editButton;
    TextInputEditText editTextFirstName;
    TextInputEditText editTextLastName;
    TextInputEditText editTextDepartment;
    TextInputEditText editTextRoom;
    TextInputEditText editTextNurseId;

    //Changed values boolean
    Boolean firstNameChanged = false;
    Boolean lastNameChanged = false;
    Boolean departmentChanged = false;
    Boolean roomChanged = false;
    Boolean nurseIdChanged = false;

    //Patient Attributes
    int patientId = 0;
    String patientFirstName = "";
    String patientLastName = "";
    String patientDepartment = "";
    String patientRoom = "";
    int patientNurseId = 0;

    //Changed Patient Attributes
    String changedPatientFirstName = "";
    String changedPatientLastName = "";
    String changedPatientDepartment = "";
    String changedPatientRoom = "";
    int changedPatientNurseId = 0;

    //Database related
    PatientViewModel patientViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info);

        editButton = findViewById(R.id.edit_patient_btn);
        editTextFirstName = findViewById(R.id.edit_firstName);
        editTextLastName = findViewById(R.id.edit_lastName);
        editTextDepartment = findViewById(R.id.edit_department);
        editTextRoom = findViewById(R.id.edit_room);
        editTextNurseId = findViewById(R.id.edit_nurseID);

        //Get Values for all the text fields
        getValuesForTextFields();

        //Set values to edit text fields
        editTextFirstName.setText(patientFirstName);
        editTextLastName.setText(patientLastName);
        editTextDepartment.setText(patientDepartment);
        editTextRoom.setText(patientRoom);
        editTextNurseId.setText(String.valueOf(patientNurseId));

        //Initialize Patient View Model
        patientViewModel = new ViewModelProvider(this).get(PatientViewModel.class);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Update database record
                String newFirstName = editTextFirstName.getText().toString();
                String newLastName = editTextLastName.getText().toString();
                String newDepartment = editTextDepartment.getText().toString();
                String newRoom = editTextRoom.getText().toString();
                String newNurseId = editTextNurseId.getText().toString();

                //See if any value changed
                if(newFirstName.length() != 0) {
                    changedPatientFirstName = newFirstName;
                }
                else{
                    Toast.makeText(UpdateInfoActivity.this,
                            "First name can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }


                if(newLastName.length() != 0) {
                    changedPatientLastName = newLastName;
                }
                else{
                    Toast.makeText(UpdateInfoActivity.this,
                            "Last name can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }


                if(newDepartment.length() != 0) {
                    changedPatientDepartment = newDepartment;
                }
                else{
                    Toast.makeText(UpdateInfoActivity.this,
                            "Department name can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(newRoom.length() != 0) {
                    changedPatientRoom = newRoom;
                }
                else{
                    Toast.makeText(UpdateInfoActivity.this,
                            "Room can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }


                if(newNurseId.length() != 0) {
                    changedPatientNurseId = Integer.parseInt(newNurseId);
                }
                else{
                    Toast.makeText(UpdateInfoActivity.this,
                            "Nurse ID can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                new EditPatientOnDatabaseAsyncTask().execute();
            }
        });

        final String currentFirstName = editTextFirstName.getText().toString();
        final String[] changedFirstName = {""};
        editTextFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                changedFirstName[0] = editable.toString();
                if(!currentFirstName.equals(changedFirstName[0])) {
                    firstNameChanged = true;
                }else{
                    firstNameChanged = false;
                }
            }
        });
    }

    private class EditPatientOnDatabaseAsyncTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            Patient patient = getChangedPatientData(
                    changedPatientFirstName,
                    changedPatientLastName,
                    changedPatientDepartment,
                    changedPatientRoom,
                    changedPatientNurseId
            );

            if(patient != null) {
                //Update the database
                patientViewModel.update(patient);
                return true;
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if(result) {
                setResult(Constants.EDIT_SUCCESSFUL);
            }
            finish();
        }
    }

    private Patient getChangedPatientData(
            String newFirstName,
            String newLastName,
            String newDepartment,
            String newPatientRoom,
            int newNurseId
    ) {
        if(patientViewModel != null) {
            Patient currentPatientData = patientViewModel.findByPatientID(patientId);
            if(currentPatientData != null) {

                //Check if value changed and update only if value changed
                if(!currentPatientData.getFirstName().equals(newFirstName)) {
                    currentPatientData.setFirstName(newFirstName);
                }
                if(!currentPatientData.getLastName().equals(newLastName)) {
                    currentPatientData.setLastName(newLastName);
                }
                if(!currentPatientData.getDepartment().equals(newDepartment)) {
                    currentPatientData.setDepartment(newDepartment);
                }
                if(!currentPatientData.getRoom().equals(newPatientRoom)) {
                    currentPatientData.setRoom(newPatientRoom);
                }
                if(currentPatientData.getNurseID() != newNurseId) {
                    currentPatientData.setNurseID(newNurseId);
                }
                return currentPatientData;
            }
            return null;
        }
        return null;
    }

    private void getValuesForTextFields() {
        Intent intent = getIntent();
        if(intent.hasExtra("patient_first_name")) {
            patientFirstName = intent.getStringExtra("patient_first_name");
        }
        if(intent.hasExtra("patient_last_name")) {
            patientLastName = intent.getStringExtra("patient_last_name");
        }
        if(intent.hasExtra("patient_department")) {
            patientDepartment = intent.getStringExtra("patient_department");
        }
        if(intent.hasExtra("patient_room")) {
            patientRoom = intent.getStringExtra("patient_room");
        }
        if(intent.hasExtra("patient_nurse_id")) {
            patientNurseId = intent.getIntExtra("patient_nurse_id", 0);
        }
        if(intent.hasExtra("patient_id")) {
            patientId = intent.getIntExtra("patient_id", 0);
        }
    }
}