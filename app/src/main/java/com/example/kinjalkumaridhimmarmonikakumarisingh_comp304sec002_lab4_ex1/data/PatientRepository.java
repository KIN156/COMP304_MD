package com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

public class PatientRepository {
    public PatientDao patientDao;
    private List<Patient> allPatients;

    public PatientRepository(Application application) {
        PatientDatabase patientDatabase = PatientDatabase.getDatabase(application);
        patientDao = patientDatabase.patientDao();
    }

    public List<Patient> getAllPatients()
    {
        List<Patient> patients = new ArrayList<>();
        if(patientDao.getAllPatients() != null)
            patients = patientDao.getAllPatients();
        return patients;
    }

    public void insert(Patient patient) {
        PatientDatabase.databaseWriteExecutor.execute(() -> {
           patientDao.insert(patient);
        });
    }

    public LiveData<Patient> findbyPatientID(int patientID) {
        return patientDao.getByPatientID(patientID);
    }
}
