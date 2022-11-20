package com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.data;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

public class TestRepository {
    public TestDao testDao;

    public TestRepository(Application application) {
        TestDatabase testDatabase = TestDatabase.getDatabase(application);
        testDao = testDatabase.testDao();
    }

    public List<Test> getAllTestsForPatient(int patientID)
    {
        List<Test> tests = new ArrayList<>();
        if(testDao.getAllTestsForPatientID(patientID) != null)
            tests = testDao.getAllTestsForPatientID(patientID);
        return tests;
    }

    public void insert(Test test) {
        PatientDatabase.databaseWriteExecutor.execute(() -> {
            testDao.insert(test);
        });
    }

}
