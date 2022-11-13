package com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NurseRepository {
    public NurseDao nurseDao;
    private LiveData<List<Nurse>> allNurse;

    public NurseRepository(Application application) {
        NurseDatabase nurseDatabase = NurseDatabase.getDatabase(application);
        nurseDao = nurseDatabase.nurseDao();
    }

    public void insert(Nurse nurse) {
        NurseDatabase.databaseWriteExecutor.execute(() -> {
            nurseDao.insert(nurse);
        });
    }

    public LiveData<Nurse> findbyNurseID(int nurseId) {
        return nurseDao.getByNurseID(nurseId);
    }
}
