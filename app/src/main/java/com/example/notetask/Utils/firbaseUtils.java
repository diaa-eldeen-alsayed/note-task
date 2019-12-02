package com.example.notetask.Utils;

import android.util.Log;
import com.example.notetask.Model.Note;
import com.example.notetask.viewModel.noteViewModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;

public class firbaseUtils  {

    private DatabaseReference fNotesDatabase;
    noteViewModel viewModel ;


    public firbaseUtils(final noteViewModel viewModel,String userName) {

        fNotesDatabase = FirebaseDatabase.getInstance().getReference().child("Notes").child(userName);
        this.viewModel= viewModel;

    }





    public void insert(String title ,String subject){
        Map addMap = new HashMap();

        addMap.put("title", title);
        addMap.put("subject", subject);
        String key =  fNotesDatabase.push().getKey();
        fNotesDatabase.child(key).setValue(addMap);
        viewModel.insert(new Note(key,title,subject));



    }
    public void delete(Note note){
        Log.i("keyyyyyyyy",note.getKeyNote());
        fNotesDatabase.child(note.getKeyNote()).removeValue();
        viewModel.delete(note);

    }
    public void update(Note note){
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("title",note.getTitle());
        childUpdates.put("subject",note.getSubject());
        fNotesDatabase.child(note.getKeyNote()).updateChildren(childUpdates);
        viewModel.update(note);

    }



}
