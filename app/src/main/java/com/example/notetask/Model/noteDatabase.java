package com.example.notetask.Model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities = {Note.class},version = 2)
public abstract class noteDatabase extends RoomDatabase {
    private static noteDatabase instance;

    public abstract noteDao Dao();
    public static synchronized noteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    noteDatabase.class, "Note_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}

