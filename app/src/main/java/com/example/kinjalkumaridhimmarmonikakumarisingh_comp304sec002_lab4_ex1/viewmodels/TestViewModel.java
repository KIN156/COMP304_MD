package com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.data.Patient;
import com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.data.Test;
import com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.data.TestRepository;

import java.util.List;

public class TestViewModel extends AndroidViewModel {
    private TestRepository testRepository;

    public TestViewModel(@NonNull Application application) {
        super(application);
        testRepository = new TestRepository(application);
    }

    public void insert(Test test) {
        testRepository.insert(test);
    }

    public List<Test> getAllTestsForPatient(int patientID) {
        return testRepository.getAllTestsForPatient(patientID);
    }
}
