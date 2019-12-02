package com.example.notetask.adapter;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notetask.Model.Note;
import com.example.notetask.R;

public class noteAdapter extends PagedListAdapter<Note,noteAdapter.viewHolder> {

    clickLisenter lisenter;



    public noteAdapter(clickLisenter lisenter) {
        super(Note.DIFF_CALLBACK);
        this.lisenter=lisenter;
    }



    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_list_item,parent,false);
        return new noteAdapter.viewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
       Note note =getItem(position);
        holder.title.setText(note.getTitle());
        holder.subject.setText(note.getSubject());

    }



    public class viewHolder extends RecyclerView.ViewHolder{
        TextView title,subject;
        Button edit,delete;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            subject=itemView.findViewById(R.id.subject);
            edit=itemView.findViewById(R.id.edit);
            delete=itemView.findViewById(R.id.delete);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    lisenter.clickEdit(getItem(getAdapterPosition()));
                }
            });
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    lisenter.clickDelete(getItem(getAdapterPosition()),getItemCount());

                }
            });


        }
    }
    public interface clickLisenter{
        public void clickEdit(Note note);
        public void clickDelete(Note note,int size);



    }
}
