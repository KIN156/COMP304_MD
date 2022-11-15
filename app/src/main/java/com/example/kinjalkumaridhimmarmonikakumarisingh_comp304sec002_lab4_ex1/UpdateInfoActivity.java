package com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

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
    String patientFirstName = "";
    String patientLastName = "";
    String patientDepartment = "";
    String patientRoom = "";
    int patientNurseId = 0;

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

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Update database record
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
    }
}