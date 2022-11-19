package com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NurseRepository {
    public NurseDao nurseDao;

    public NurseRepository(Application application) {
        NurseDatabase nurseDatabase = NurseDatabase.getDatabase(application);
        nurseDao = nurseDatabase.nurseDao();
    }

    public void insert(Nurse nurse) {
        NurseDatabase.databaseWriteExecutor.execute(() -> {
            nurseDao.insert(nurse);
        });
    }

    public List<Integer> getAllNurseIDs() {
        return nurseDao.getAllNurseIDs();
    }

    public String getPasswordForNurseID(int nurseID) {
        return nurseDao.getNursePasswordForNurseID(nurseID);
    }

    public Nurse findByNurseID(int nurseId) {
        return nurseDao.getByNurseID(nurseId);
    }
}
