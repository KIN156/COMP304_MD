package com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NurseDao {
    @Insert
    void insert (Nurse nurse);

    @Update
    void update(Nurse nurse);

    @Delete
    void delete(Nurse nurse);

    @Query("DELETE FROM nurse_table")
    void deleteAll();

    @Query("SELECT * FROM nurse_table WHERE nurseId = :nurseId")
    Nurse getByNurseID(int nurseId);

    @Query("SELECT nurseId from nurse_table")
    List<Integer> getAllNurseIDs();

    @Query("SELECT password FROM nurse_table WHERE nurseId = :nurseID")
    String getNursePasswordForNurseID(int nurseID);
}
