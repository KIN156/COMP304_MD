package com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.data.Nurse;
import com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.data.NurseRepository;
import com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.data.Patient;
import com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.data.PatientRepository;

import java.util.List;

public class NurseViewModel extends AndroidViewModel {
    private NurseRepository nurseRepository;

    public NurseViewModel(@NonNull Application application) {
        super(application);
        nurseRepository = new NurseRepository(application);
    }

    public Nurse findByNurseID(int nurseId) {
        return nurseRepository.findByNurseID(nurseId);
    }

    public void insert(Nurse nurse) {
        nurseRepository.insert(nurse);
    }

    public List<Integer> getAllNurseIDs() { return nurseRepository.getAllNurseIDs(); }

    public String getPasswordForNurseID(int nurseID) {
        return nurseRepository.getPasswordForNurseID(nurseID);
    }
}
