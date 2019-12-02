package com.example.notetask.Model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Note_table")
public class Note {
    @PrimaryKey(autoGenerate = true)
    int id;
    String title;
    String subject;
    String keyNote;

    public String getKeyNote() {
        return keyNote;
    }

    public void setKeyNote(String keyNote) {
        this.keyNote = keyNote;
    }

    public Note(String keyNote , String title, String subject) {
        this.keyNote=keyNote;
        this.title = title;
        this.subject = subject;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
   public static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK= new DiffUtil.ItemCallback<Note>() {
       @Override
       public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
           return oldItem.getId()==newItem.getId();
       }

       @Override
       public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
           return oldItem.equals(newItem);
       }
   };
    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        Note  note = (Note) obj;

        return note.id == this.id && note.title == this.title && note.subject==this.subject ;
    }
}
