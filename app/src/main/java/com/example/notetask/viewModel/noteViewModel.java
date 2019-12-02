package com.example.notetask.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.example.notetask.Model.Note;
import com.example.notetask.Repository.noteRepository;

public class noteViewModel extends AndroidViewModel {
    private noteRepository repository;
    LiveData<PagedList<Note>> allNotes;
    public noteViewModel(@NonNull Application application) {
        super(application);
        repository=new noteRepository(application);

    }

    public  LiveData<PagedList<Note>> getAllNotes()
    {
        allNotes=repository.getAllNotes();
        return allNotes ;
    }


    public void insert(Note note){
        repository.insertNote(note);

    }
    public void update(Note note){
        repository.updateNote(note);

    }
    public void delete(Note note){
        repository.deleteNote(note);
    }
}
