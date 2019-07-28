package com.tristankirkham.coursemanager.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface TermDao {

    //Insert a term
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTerm(TermEntity termEntity);

    //Insert all terms from an ArrayList
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllTerms(List<TermEntity> terms);

    //Delete a term
    @Delete
    void deleteTerm(TermEntity termEntity);

    //Delete term by ID
    @Query("SELECT * FROM term WHERE term_id = :term_id")
    TermEntity getTermById(int term_id);

    //Retrieve term by date
    @Query("SELECT * FROM term ORDER BY startDate DESC")
    LiveData<List<TermEntity>> getAll();

    //Delete all terms
    @Query("DELETE FROM term")
    int deleteAll();

    //Count all term
    @Query("SELECT COUNT(*) FROM term")
    int getCount();

}
