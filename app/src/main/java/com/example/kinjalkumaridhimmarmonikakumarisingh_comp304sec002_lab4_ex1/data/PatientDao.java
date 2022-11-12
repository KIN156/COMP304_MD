package com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PatientDao {
    @Insert
    void insert (Patient patient);

    @Update
    void update(Patient patient);

    @Delete
    void delete(Patient patient);

    @Query("DELETE FROM patient_table")
    void deleteAll();

    @Query("SELECT * FROM patient_table WHERE patientID = :patientID")
    LiveData<Patient> getByPatientID(int patientID);

    @Query("SELECT * FROM patient_table")
    LiveData<List<Patient>> getAllPatients();
}
