package com.example.notetask.View;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;

import com.example.notetask.Fragments.MainFragment;
import com.example.notetask.Fragments.notesFirbaseFragment;
import com.example.notetask.Fragments.notesLocalFragment;
import com.example.notetask.R;

public class MainActivity extends AppCompatActivity  implements  notesLocalFragment.OnFragmentInteractionListener , notesFirbaseFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
