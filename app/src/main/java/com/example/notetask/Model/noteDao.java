package com.example.notetask.Model;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface noteDao {
    @Insert
    void insert(Note note);
    @Query("select * from Note_table")
    DataSource.Factory<Integer, Note> getAllNotes();
    @Update
    void noteUpdate(Note note);
    @Delete
    void noteDelete(Note note);
}
