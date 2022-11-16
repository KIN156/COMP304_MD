package com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.adapters.HomePageFragmentAdapter;
import com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.adapters.PatientRecyclerViewAdapter;
import com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.constants.Constants;
import com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.data.Patient;
import com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.interfaces.OnItemClickListener;
import com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.viewmodels.PatientViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class ViewPatientInfo extends Fragment implements OnItemClickListener {

    PatientRecyclerViewAdapter patientRecyclerViewAdapter;
    ViewPager2 viewPager2;
    RecyclerView patientsRecyclerView;
    FloatingActionButton addPatientFab;
    private PatientViewModel patientViewModel;
    View root;
    List<Patient> allPatients = new ArrayList<>();
    //Activity Result Launchers
    ActivityResultLauncher<Intent> mStartAddPatientForResult;
    ActivityResultLauncher<Intent> mStartEditPatientForResult;

    int currentResultCode = -1;
    int lastChosenPatientPosition = -1;

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
                Toast.makeText(root.getContext(),
                        "Patient database is empty", Toast.LENGTH_SHORT).show();
            }else if(patients.isEmpty()) {
                Toast.makeText(root.getContext(),
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
                            new PatientRecyclerViewAdapter(root.getContext(), allPatients,
                                    ViewPatientInfo.this);
                    patientsRecyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
                    patientsRecyclerView.setAdapter(patientRecyclerViewAdapter);
                }else{
                    //Dont need to create adapter, we use the existing adapter and notify
                    //changes in data
                    if(currentResultCode == Constants.ADD_SUCCESSFUL) {
                        patientRecyclerViewAdapter.notifyItemInserted(allPatients.size()-1);
                        //reset code
                        currentResultCode = -1;
                        Toast.makeText(root.getContext(),
                                        "Patient Added Successfully", Toast.LENGTH_SHORT)
                                .show();
                    }else if(currentResultCode == Constants.EDIT_SUCCESSFUL) {
                        patientRecyclerViewAdapter.notifyItemChanged(lastChosenPatientPosition);
                        //reset position
                        lastChosenPatientPosition = -1;
                        //reset code
                        currentResultCode = -1;
                        Toast.makeText(root.getContext(), "Patient Edited Successfully",
                                Toast.LENGTH_SHORT).show();
                    }else{
                        //Executes when switch between tabs happen as layout and adapter for
                        // recyclerview becomes null when switching tabs
                        patientsRecyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
                        patientsRecyclerView.setAdapter(patientRecyclerViewAdapter);
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
        Intent intent = new Intent(root.getContext(), UpdateInfoActivity.class);
        intent.putExtra("patient_id", patient.getPatientID());
        intent.putExtra("patient_first_name", patient.getFirstName());
        intent.putExtra("patient_last_name", patient.getLastName());
        intent.putExtra("patient_department", patient.getDepartment());
        intent.putExtra("patient_room", patient.getRoom());
        intent.putExtra("patient_nurse_id", patient.getNurseID());
        mStartEditPatientForResult.launch(intent);
    }

    private List<Patient> getPatientsData(PatientViewModel patientViewModel) {
        if(patientViewModel == null) {
            return new ArrayList<>();
        }

        List<Patient> patientsLiveData = patientViewModel.getAllPatients();

        List<Patient> patientsData;

        if(patientsLiveData != null) {
            patientsData = patientsLiveData;
        }else{
            patientsData = new ArrayList<>();
        }
        return patientsData;
    }

    public ViewPatientInfo() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Initialize codes
        currentResultCode = -1;

        //Initialize chosen position
        lastChosenPatientPosition = -1;

        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_view_patient_info, container, false);

        //Initialize UI elements
        addPatientFab = root.findViewById(R.id.fab_add_patient);
        patientsRecyclerView = root.findViewById(R.id.patient_recycler_view);

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
                Intent intent = new Intent(root.getContext(), PatientActivity.class);
                mStartAddPatientForResult.launch(intent);
            }
        });

        new GetPatientsDataAsyncClass().execute(patientViewModel);
        return root;
    }
}