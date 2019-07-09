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


    private final List<TermEntity> tTerms;
    private final Context tContext;


    public TermAdapter(List<TermEntity> tTerms, Context tContext) {
        this.tTerms = tTerms;
        this.tContext = tContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());


        //Call layout view file here
        View view = inflater.inflate(R.layout.note_list_item, parent,false);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final TermEntity term = tTerms.get(position);
        holder.tTextView.setText(term.getTitle());

        //Set edit FAB click listener
        holder.tFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(tContext, TermEditorActivity.class);
                intent.putExtra(TERM_ID_KEY, term.getId());
                tContext.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return tTerms.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textView)
        //Needs to match ID of TextView in layout file
        TextView tTextView;

        @BindView(R.id.fab)
        FloatingActionButton tFab;



        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
