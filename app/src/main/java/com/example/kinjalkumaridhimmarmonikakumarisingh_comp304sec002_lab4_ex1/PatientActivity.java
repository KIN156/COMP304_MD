package com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.constants.Constants;
import com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.data.Patient;
import com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.viewmodels.NurseViewModel;
import com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.viewmodels.PatientViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class PatientActivity extends AppCompatActivity {

    private PatientViewModel patientViewModel;
    private TextInputEditText firstName;
    private TextInputEditText lastName;
    private Spinner departmentSpinner;
    private Spinner nurseIDSpinner;
    private Spinner roomSpinner;

    private ArrayAdapter<Integer> nurseIDsSpinnerAdapter;

    //Patient attribute
    String firstNameValue;
    String lastNameValue;
    String departmentValue;
    int nurseIDValue;
    String roomValue;

    ArrayList<Integer> availableNurseIDs;
    List<Integer> nurseIDs;

    //Database
    NurseViewModel nurseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        patientViewModel = new ViewModelProvider(this).get(PatientViewModel.class);
        nurseViewModel = new ViewModelProvider(this).get(NurseViewModel.class);

        departmentSpinner = findViewById(R.id.add_patient_department_spinner);
        nurseIDSpinner = findViewById(R.id.add_patientNurse_id_spinner);
        roomSpinner = findViewById(R.id.add_patient_room_spinner);

        //Spinners
        ArrayAdapter<CharSequence> departmentNameSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.dept_name_array, R.layout.spinner_list_white);
        departmentNameSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        departmentSpinner.setAdapter(departmentNameSpinnerAdapter);
        String[] deptNameArray = getResources().getStringArray(R.array.dept_name_array);
        departmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                departmentValue = deptNameArray[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        departmentValue = deptNameArray[0];
        departmentSpinner.setSelection(0);

        ArrayAdapter<CharSequence> roomNameSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.room_names_array, R.layout.spinner_list_white);
        roomNameSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roomSpinner.setAdapter(roomNameSpinnerAdapter);
        String[] roomNamesArray = getResources().getStringArray(R.array.room_names_array);
        roomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                roomValue = roomNamesArray[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        roomValue = roomNamesArray[0];
        roomSpinner.setSelection(0);

        new GetAvailableNurseIDs().execute();
    }

    public void createPatientButton(View view) {
        firstName = (TextInputEditText)findViewById(R.id.firstName);
        lastName = (TextInputEditText)findViewById(R.id.lastName);

        if (firstName.getText().toString().length() != 0 &&
                lastName.getText().toString().length() != 0){

            firstNameValue = firstName.getText().toString();
            lastNameValue = lastName.getText().toString();
            patientViewModel.insert(new
                    Patient(firstNameValue, lastNameValue,
                    departmentValue, nurseIDValue, roomValue));
            setResult(Constants.ADD_SUCCESSFUL);
            finish();
        }
        else {
            Toast.makeText(PatientActivity.this, "Please ensure there are no null values", Toast.LENGTH_SHORT).show();
        }
    }

    private class GetAvailableNurseIDs extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            nurseIDs = nurseViewModel.getAllNurseIDs();
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            availableNurseIDs = new ArrayList<>(nurseIDs);
            nurseIDsSpinnerAdapter = new ArrayAdapter(PatientActivity.this,
                    R.layout.spinner_list_white,
                    availableNurseIDs);
            nurseIDsSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            nurseIDSpinner.setAdapter(nurseIDsSpinnerAdapter);
            nurseIDValue = availableNurseIDs.get(0);
            nurseIDSpinner.setSelection(0);
            nurseIDSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    nurseIDValue = availableNurseIDs.get(i);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
    }
}