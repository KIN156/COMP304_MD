package com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TestDao {
        @Insert
        void insert (Test test);

        @Delete
        void delete(Test test);

        @Query("DELETE FROM test_table")
        void deleteAll();

        @Query("SELECT * from test_table WHERE patientID = :patientID")
        List<Test> getAllTestsForPatientID(int patientID);
}
