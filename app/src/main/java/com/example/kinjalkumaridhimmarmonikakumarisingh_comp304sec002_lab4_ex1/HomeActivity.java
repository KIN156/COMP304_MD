package com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.adapters.PatientRecyclerViewAdapter;
import com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.constants.Constants;
import com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.data.Patient;
import com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.data.Test;
import com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.interfaces.OnItemClickListener;
import com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.viewmodels.PatientViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements OnItemClickListener {

    PatientRecyclerViewAdapter patientRecyclerViewAdapter;
    RecyclerView patientsRecyclerView;
    FloatingActionButton addPatientFab;
    private PatientViewModel patientViewModel;
    List<Patient> allPatients = new ArrayList<>();
    //Activity Result Launchers
    ActivityResultLauncher<Intent> mStartAddPatientForResult;
    ActivityResultLauncher<Intent> mStartEditPatientForResult;

    int currentResultCode = -1;
    int lastChosenPatientPosition = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Initialize codes
        currentResultCode = -1;

        //Initialize chosen position
        lastChosenPatientPosition = -1;

        //Initialize UI elements
        addPatientFab = findViewById(R.id.fab_add_patient);
        patientsRecyclerView = findViewById(R.id.patient_recycler_view);

        //Initialize Patient View Model
        patientViewModel = new ViewModelProvider(this).get(PatientViewModel.class);

        //Register Activity Result
        mStartEditPatientForResult = registerForActivityResult(new
                        ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == Constants.EDIT_SUCCESSFUL) {
                            //Fetch new Data if user has edited data
                            currentResultCode = Constants.EDIT_SUCCESSFUL;
                            new GetPatientsDataAsyncClass().execute(patientViewModel);
                        }
                    }
                });
        mStartAddPatientForResult =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                        new ActivityResultCallback<ActivityResult>() {
                            @Override
                            public void onActivityResult(ActivityResult result) {
                                if(result.getResultCode() == Constants.ADD_SUCCESSFUL) {
                                    //Fetch new Data if user has set new data
                                    currentResultCode = Constants.ADD_SUCCESSFUL;
                                    new GetPatientsDataAsyncClass().execute(patientViewModel);
                                }
                            }
                        });

        addPatientFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, PatientActivity.class);
                mStartAddPatientForResult.launch(intent);
            }
        });

        new GetPatientsDataAsyncClass().execute(patientViewModel);
    }


    //Receive Patients data
    private List<Patient> getPatientsData(PatientViewModel patientViewModel) {
        if(patientViewModel == null) {
            return new ArrayList<>();
        }

        List<Patient> patientsData = patientViewModel.getAllPatients();

        if(patientsData != null ) {
            return patientsData;
        }else{
            return new ArrayList<>();
        }
    }

    //Async Tasks
    private class GetPatientsDataAsyncClass extends AsyncTask<PatientViewModel, Void, List<Patient>> {

        @Override
        protected List<Patient> doInBackground(PatientViewModel... patientViewModels) {
            PatientViewModel patientViewModel = patientViewModels[0];
            if(patientViewModel == null) {
                return new ArrayList<>();
            }
            return getPatientsData(patientViewModel);
        }

        @Override
        protected void onPostExecute(List<Patient> patients) {
            super.onPostExecute(patients);

            if(patients == null){
                Toast.makeText(HomeActivity.this,
                        "Patient database is empty", Toast.LENGTH_SHORT).show();
            }else if(patients.isEmpty()) {
                Toast.makeText(HomeActivity.this,
                        "Patient database is empty", Toast.LENGTH_SHORT).show();
            }else{
                //We only proceed to create recycler adapter if patients data is not empty

                //Source data for recycler view is empty we copy all data from patients
                // to source data aka allPatients
                if(allPatients.isEmpty()){
                    allPatients = patients;
                }else if(currentResultCode == Constants.ADD_SUCCESSFUL){
                    //We only add the newly added patient who is at the end of the list
                    allPatients.add(patients.get(patients.size()-1));
                }else if(currentResultCode == Constants.EDIT_SUCCESSFUL) {
                    //Update the changed patient
                    allPatients.set(lastChosenPatientPosition,
                            patients.get(lastChosenPatientPosition));
                }

                if(patientRecyclerViewAdapter == null){
                    patientRecyclerViewAdapter =
                            new PatientRecyclerViewAdapter(HomeActivity.this, allPatients,
                                    HomeActivity.this);
                    patientsRecyclerView
                            .setLayoutManager(new LinearLayoutManager(HomeActivity.this));
                    patientsRecyclerView.setAdapter(patientRecyclerViewAdapter);
                }else{
                    //Dont need to create adapter, we use the existing adapter and notify
                    //changes in data
                    if(currentResultCode == Constants.ADD_SUCCESSFUL) {
                        patientRecyclerViewAdapter.notifyItemInserted(allPatients.size()-1);
                        //reset code
                        currentResultCode = -1;
                        Toast.makeText(HomeActivity.this,
                                        "Patient Added Successfully", Toast.LENGTH_SHORT)
                                .show();
                    }else if(currentResultCode == Constants.EDIT_SUCCESSFUL) {
                        patientRecyclerViewAdapter.notifyItemChanged(lastChosenPatientPosition);
                        //reset position
                        lastChosenPatientPosition = -1;
                        //reset code
                        currentResultCode = -1;
                        Toast.makeText(HomeActivity.this, "Patient Edited Successfully",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    /**
     * Executes when a patient item is clicked
     * @param view
     * @param position
     */
    @Override
    public void onItemClick(View view, int position) {
        Patient patient = allPatients.get(position);
        lastChosenPatientPosition = position;
        Intent intent = new Intent(HomeActivity.this, UpdateInfoActivity.class);
        intent.putExtra("patient_id", patient.getPatientID());
        intent.putExtra("patient_first_name", patient.getFirstName());
        intent.putExtra("patient_last_name", patient.getLastName());
        intent.putExtra("patient_department", patient.getDepartment());
        intent.putExtra("patient_room", patient.getRoom());
        intent.putExtra("patient_nurse_id", patient.getNurseID());
        mStartEditPatientForResult.launch(intent);
    }

}