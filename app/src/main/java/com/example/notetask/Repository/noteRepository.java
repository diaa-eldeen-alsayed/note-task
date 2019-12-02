package com.example.notetask.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.notetask.Model.Note;
import com.example.notetask.Model.noteDao;
import com.example.notetask.Model.noteDatabase;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class noteRepository {

private noteDao Dao;

    public noteRepository(Application application) {
        noteDatabase Database=noteDatabase.getInstance(application);
        Dao=Database.Dao();
    }
    public  LiveData<PagedList<Note>> getAllNotes()
    {
        return new LivePagedListBuilder<>(Dao.getAllNotes(),/* page Size*/15).build();

    }

    public void insertNote(final Note note){
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Dao.insert(note);

            }
        });

    }
    public void deleteNote(final Note note){
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Dao.noteDelete(note);

            }
        });

    }
    public void updateNote(final Note note){
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Dao.noteUpdate(note);

            }
        });

    }




}
