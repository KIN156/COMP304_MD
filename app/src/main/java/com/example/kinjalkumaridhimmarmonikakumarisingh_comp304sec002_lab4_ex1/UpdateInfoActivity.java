package com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.adapters.TestRecyclerViewAdapter;
import com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.constants.Constants;
import com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.data.Patient;
import com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.data.Test;
import com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.interfaces.OnItemClickListener;
import com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.viewmodels.NurseViewModel;
import com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.viewmodels.PatientViewModel;
import com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.viewmodels.TestViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UpdateInfoActivity extends AppCompatActivity implements OnItemClickListener {

    Button editButton;
    Button addTestButton;
    TextInputEditText editTextFirstName;
    TextInputEditText editTextLastName;
    Spinner patientDepartmentSpinner;
    Spinner patientRoomSpinner;
    Spinner nurseIDsSpinner;

    //Patient Attributes
    int patientId = -1;
    String patientFirstName = "";
    String patientLastName = "";
    String patientDepartment = "";
    String patientRoom = "";
    int patientNurseId = -1;

    //Spinner Datas
    ArrayList<Integer> availableNurseIDs;
    ArrayAdapter<Integer> nurseIDsSpinnerAdapter;


    //Changed Patient Attributes
    String changedPatientFirstName = "";
    String changedPatientLastName = "";
    String changedPatientDepartment = "";
    String changedPatientRoom = "";
    int changedPatientNurseId = 0;

    //Activity Result Launchers
    ActivityResultLauncher<Intent> mStartAddTestForResult;

    //Database related
    PatientViewModel patientViewModel;
    TestViewModel testViewModel;
    NurseViewModel nurseViewModel;

    //Recycler View related
    TestRecyclerViewAdapter testRecyclerViewAdapter;
    RecyclerView testRecyclerView;

    //Data
    List<Test> allTests = new ArrayList<>();
    List<Integer> nurseIDs = new ArrayList<>();

    int currentResultCode = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info);

        editButton = findViewById(R.id.edit_patient_btn);
        addTestButton = findViewById(R.id.add_test_btn);
        editTextFirstName = findViewById(R.id.edit_firstName);
        editTextLastName = findViewById(R.id.edit_lastName);
        patientDepartmentSpinner = findViewById(R.id.patient_department_spinner);
        patientRoomSpinner = findViewById(R.id.patient_room_spinner);
        nurseIDsSpinner = findViewById(R.id.nurse_id_spinner);
        testRecyclerView = findViewById(R.id.test_recycler_view);

        //Initialize Patient View Model
        patientViewModel = new ViewModelProvider(this).get(PatientViewModel.class);
        testViewModel = new ViewModelProvider(this).get(TestViewModel.class);
        nurseViewModel = new ViewModelProvider(this).get(NurseViewModel.class);

        //Initialize nurseIDs
        availableNurseIDs = new ArrayList<>();

        //Get Values for all the text fields
        getValuesForTextFields();

        //Set values to edit text fields
        editTextFirstName.setText(patientFirstName);
        editTextLastName.setText(patientLastName);

        //Set Spinners
        ArrayAdapter<CharSequence> patientDeptArray = ArrayAdapter.createFromResource(this,
                R.array.dept_name_array, android.R.layout.simple_spinner_item);
        patientDeptArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        String[] deptNames = getResources().getStringArray(R.array.dept_name_array);
        patientDepartmentSpinner.setAdapter(patientDeptArray);
        patientDepartmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                changedPatientDepartment = deptNames[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        changedPatientDepartment = patientDepartment;
        patientDepartmentSpinner.setSelection(Arrays.asList(deptNames).indexOf(patientDepartment));

        ArrayAdapter<CharSequence> patientRoomArray = ArrayAdapter.createFromResource(this,
                R.array.room_names_array, android.R.layout.simple_spinner_item);
        patientRoomArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        patientRoomSpinner.setAdapter(patientRoomArray);
        String[] roomNames = getResources().getStringArray(R.array.room_names_array);
        patientRoomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                changedPatientRoom = roomNames[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        changedPatientRoom = patientRoom;
        patientRoomSpinner.setSelection(Arrays.asList(roomNames).indexOf(patientRoom));


        new GetAvailableNurseIDs().execute();

        //Initially users wont be able to edit text field
        toggleFields(false);

        //Register Activity results
        mStartAddTestForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == Constants.ADD_SUCCESSFUL) {
                            currentResultCode = Constants.ADD_SUCCESSFUL;
                            new GetTestsForPatientAsyncTask().execute();
                        }
                    }
                });

        //Initialize test recycler view and adapter
        testRecyclerView.setLayoutManager(new LinearLayoutManager(UpdateInfoActivity.this));
        testRecyclerView.setAdapter(testRecyclerViewAdapter);

        addTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateInfoActivity.this, TestActivity.class);
                intent.putExtra("patient_id", patientId);
                intent.putExtra("nurse_id", patientNurseId);
                //Register Activity result launcher
                mStartAddTestForResult.launch(intent);
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //User wishes to edit..
                //Enable all text fields
                if(editButton.getText().toString().equals(getString(R.string.btn_edit_patient))) {
                    toggleFields(true);
                    //Change button name to save
                    editButton.setText(getString(R.string.btn_save_patient));
                    return;
                }

                //Update database record
                String newFirstName = editTextFirstName.getText().toString();
                String newLastName = editTextLastName.getText().toString();

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

                new EditPatientOnDatabaseAsyncTask().execute();
            }
        });

        //Get All Tests
        new GetTestsForPatientAsyncTask().execute();
    }

    @Override
    public void onItemClick(View view, int position) {
        //Executes when test item is clicked
        Test testToSend = allTests.get(position);
        Intent intent = new Intent(UpdateInfoActivity.this, TestActivity.class);
        startActivity(intent);
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

            //Update the patient attributes to the changed ones
            patientFirstName = changedPatientFirstName;
            patientLastName = changedPatientLastName;
            patientDepartment = changedPatientDepartment;
            patientRoom = changedPatientRoom;
            patientNurseId = changedPatientNurseId;

            editButton.setText(getString(R.string.btn_edit_patient));
            toggleFields(false);
//            finish();
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
            nurseIDsSpinnerAdapter = new ArrayAdapter(UpdateInfoActivity.this,
                    android.R.layout.simple_spinner_item,
                    availableNurseIDs);
            nurseIDsSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            nurseIDsSpinner.setAdapter(nurseIDsSpinnerAdapter);
            changedPatientNurseId = availableNurseIDs.indexOf(patientNurseId);
            nurseIDsSpinner.setSelection(availableNurseIDs.indexOf(patientNurseId));
            nurseIDsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    changedPatientNurseId = availableNurseIDs.get(i);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
    }
    private class GetTestsForPatientAsyncTask extends AsyncTask<Void, Void, List<Test>> {

        @Override
        protected List<Test> doInBackground(Void... voids) {
            List<Test> tests = getAllTestsForPatient();
            if(tests != null) {
                return tests;
            }else{
                return new ArrayList<>();
            }
        }

        @Override
        protected void onPostExecute(List<Test> tests) {
            super.onPostExecute(tests);

            if(allTests.isEmpty()) {
                allTests = tests;
                //Tests are empty so we would need to create adapter here
                testRecyclerViewAdapter =
                        new TestRecyclerViewAdapter(UpdateInfoActivity.this, allTests,
                                UpdateInfoActivity.this);
                testRecyclerView.setAdapter(testRecyclerViewAdapter);
                testRecyclerViewAdapter.notifyItemRangeChanged(0, allTests.size());

            }else if(UpdateInfoActivity.this.currentResultCode == Constants.ADD_SUCCESSFUL){
                //Get recently added test and add it to list
                allTests.add(tests.get(tests.size()-1));
            }

            if(UpdateInfoActivity.this.currentResultCode == Constants.ADD_SUCCESSFUL) {
                //Notify adapter about change
                //New test was inserted at the last position
                UpdateInfoActivity.this.testRecyclerViewAdapter
                        .notifyItemInserted(allTests.size()-1);
                Toast.makeText(UpdateInfoActivity.this,
                        "New test added for patient", Toast.LENGTH_SHORT).show();
            }

            UpdateInfoActivity.this.currentResultCode = -1;
        }
    }
    private List<Test> getAllTestsForPatient() {
        List<Test> tests = new ArrayList<>();
        if(patientId != -1) {
            tests = testViewModel.getAllTestsForPatient(patientId);
        }
        return tests;
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

    private void toggleFields(Boolean enabled) {
        editTextFirstName.setEnabled(enabled);
        editTextLastName.setEnabled(enabled);
        patientDepartmentSpinner.setEnabled(enabled);
        patientRoomSpinner.setEnabled(enabled);
        nurseIDsSpinner.setEnabled(enabled);
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