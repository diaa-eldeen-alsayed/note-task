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
import com.example.notetask.viewModel.noteViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link notesLocalFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.

 * create an instance of this fragment.
 */
public class notesLocalFragment extends Fragment implements noteAdapter.clickLisenter {

    private noteViewModel viewModel;
    Note note;
    noteAdapter adapter;
    RecyclerView recyclerView;
    FloatingActionButton add;
    EditText title,subject;
    private FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;
    LinearLayout noNotes;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public notesLocalFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.notes_fragment, container, false);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


       noNotes=getView().findViewById(R.id.nonotes);
      recyclerView=getView().findViewById(R.id.recycley);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        viewModel=ViewModelProviders.of(this).get(noteViewModel.class);
        add =getView().findViewById(R.id.floatingActionButton);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDialog();
            }
        });


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


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
                note=new Note("0",title.getText().toString(),subject.getText().toString());
                viewModel.insert(note);
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

                    viewModel.update(note1);
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
    public void clickDelete(Note note,int size) {
        viewModel.delete(note);
       if(size==1){
           recyclerView.setAdapter(null);
       }



    }



    public void setAdapter(PagedList<Note> notes){
        adapter=new noteAdapter(this);
        adapter.submitList(notes);
        recyclerView.setAdapter(adapter);

    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
