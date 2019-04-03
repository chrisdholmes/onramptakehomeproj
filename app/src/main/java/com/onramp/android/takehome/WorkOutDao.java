package com.onramp.android.takehome;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * The Repository interactions with this object and this is where
 * SQL is used via annotations to retrieve data from the SQLite database.
 */
@Dao
public interface WorkOutDao
{

    @Insert(onConflict = REPLACE)
    void insert(WorkOut workOut);

    @Delete
    void delete(WorkOut workOut);

    //delete all entries in the database
    @Query("DELETE FROM work_outs")
    void deleteAll();

    @Query("SELECT * from work_outs ORDER BY id ASC")
    LiveData<List<WorkOut>> getAllWorkOuts();

    // return the names of all workouts in the table
    @Query("Select name from work_outs ORDER BY name ASC")
    LiveData<List<String>> getNames();
}