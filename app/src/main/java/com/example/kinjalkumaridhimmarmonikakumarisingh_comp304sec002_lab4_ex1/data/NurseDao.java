package com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

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
    LiveData<Nurse> getByNurseID(int nurseId);
}
