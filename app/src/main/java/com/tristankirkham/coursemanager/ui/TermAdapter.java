package com.tristankirkham.coursemanager.ui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tristankirkham.coursemanager.R;
import com.tristankirkham.coursemanager.TermEditorActivity;
import com.tristankirkham.coursemanager.database.TermEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.tristankirkham.coursemanager.utilities.Constants.TERM_ID_KEY;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.ViewHolder> {


    private final List<TermEntity> termList;
    private final Context termContext;


    public TermAdapter(List<TermEntity> termList, Context termContext) {
        this.termList = termList;
        this.termContext = termContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());


        //Call layout view file here
        View view = inflater.inflate(R.layout.term_list_item, parent,false);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final TermEntity term = termList.get(position);
        holder.termTitle.setText(term.getTitle());
        /*holder.termStartDate.setText(term.getStartDate().toString());
        holder.termEndDate.setText(term.getEndDate().toString());*/


        //Set edit FAB click listener
        holder.termFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(termContext, TermEditorActivity.class);
                intent.putExtra(TERM_ID_KEY, term.getTerm_id());
                termContext.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return termList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //Needs to match ID of TextView in layout file


        @BindView(R.id.term_title)
        TextView termTitle;


        /*@BindView(R.id.note_start_date)
        TextView termStartDate;


        @BindView(R.id.note_end_date)
        TextView termEndDate;*/

        @BindView(R.id.term_fab)
        FloatingActionButton termFab;



        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
