package com.example.notetask.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.notetask.Model.Note;

import com.example.notetask.R;
import com.example.notetask.adapter.noteAdapter;
import com.example.notetask.Utils.firbaseUtils;
import com.example.notetask.viewModel.noteViewModel;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link notesFirbaseFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.

 * create an instance of this fragment.
 */
public class notesFirbaseFragment extends Fragment implements noteAdapter.clickLisenter {
    private noteViewModel viewModel;
    private FirebaseAuth mAuth;
    EditText title,subject;
    FloatingActionButton add;
    Note note;
    firbaseUtils firbase;
    noteAdapter adapter;
    RecyclerView recyclerView;
    LinearLayout noNotes;
    // TODO: Rename parameter arguments, choose names that match


    public notesFirbaseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     */
    // TODO: Rename and change types and number of parameters


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes_firbase, container, false);

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAuth=FirebaseAuth.getInstance();
        noNotes=getView().findViewById(R.id.nonotes);
        recyclerView=getView().findViewById(R.id.recycley);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        viewModel=ViewModelProviders.of(this).get(noteViewModel.class);
        firbase=new firbaseUtils(viewModel,mAuth.getCurrentUser().getDisplayName());
        viewModel.getAllNotes().observe(getActivity(), new Observer<PagedList<Note>>() {
            @Override
            public void onChanged(PagedList<Note> notes) {
                if(notes.size()>0)
                {
                    setAdapter(notes);
                    noNotes.setVisibility(View.GONE);

                }
                else
                {
                    noNotes.setVisibility(View.VISIBLE);
                }

            }
        });

        add =getView().findViewById(R.id.floatingActionButton);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDialog();
            }
        });


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }
    public void addDialog(){
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.add_update_note_dialog);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.width= WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height= WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(layoutParams);
        Button add = dialog.findViewById(R.id.add);
        Button cancel=dialog.findViewById(R.id.cancel);
        final EditText title =dialog.findViewById(R.id.edittext_title);
        final EditText subject =dialog.findViewById(R.id.edittext_subject);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(title.getText().toString().isEmpty()||subject.getText().toString().isEmpty())
                {
                    Toast.makeText(getActivity(),"الرجاء ادخال البيانات",Toast.LENGTH_LONG).show();
                }

                else {


                 firbase.insert(title.getText().toString(),subject.getText().toString());


                    dialog.cancel();
                }


            }
        });cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();

            }
        });
        dialog.show();

    }
    public void setAdapter(PagedList<Note> notes){
        adapter=new noteAdapter(this);
        adapter.submitList(notes);
        recyclerView.setAdapter(adapter);

    }
    public void editDialog(final Note note1){
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.add_update_note_dialog);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.width= WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height= WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(layoutParams);
        Button add = dialog.findViewById(R.id.add);
        Button cancel=dialog.findViewById(R.id.cancel);
        title =dialog.findViewById(R.id.edittext_title);
        subject =dialog.findViewById(R.id.edittext_subject);
        title.setText(note1.getTitle());
        subject.setText(note1.getSubject());
        add.setText("تعديل");


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(title.getText().toString().isEmpty()||subject.getText().toString().isEmpty())
                {
                    Toast.makeText(getActivity(),"الرجاء ادخال البيانات",Toast.LENGTH_LONG).show();
                }

                else {
                    note1.setTitle(title.getText().toString());
                    note1.setSubject(subject.getText().toString());

                    firbase.update(note1);
                    dialog.cancel();


                }


            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();

            }
        });
        dialog.show();
    }

    @Override
    public void clickEdit(Note note) {
        editDialog(note);

    }

    @Override
    public void clickDelete(Note note, int size) {
        firbase.delete(note);
        if(size==1){
            recyclerView.setAdapter(null);
        }
    }






    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
