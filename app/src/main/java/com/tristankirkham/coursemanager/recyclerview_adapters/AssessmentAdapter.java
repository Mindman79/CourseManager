package com.tristankirkham.coursemanager.recyclerview_adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tristankirkham.coursemanager.AssessmentEditorActivity;
import com.tristankirkham.coursemanager.R;
import com.tristankirkham.coursemanager.database.AssessmentEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.tristankirkham.coursemanager.utilities.Constants.ASSESSMENT_ID_KEY;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.ViewHolder> {

    private final List<AssessmentEntity> assessmentList;
    private final Context assessmentContext;


    public AssessmentAdapter(List<AssessmentEntity> assessmentList, Context assessmentContext) {
        this.assessmentList = assessmentList;
        this.assessmentContext = assessmentContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.assessment_list_item, parent, false);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentAdapter.ViewHolder holder, int position) {

        final AssessmentEntity assessment = assessmentList.get(position);

        holder.assessment_title.setText(assessment.getAssessmentName());




        holder.assessment_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(assessmentContext, AssessmentEditorActivity.class);
                intent.putExtra(ASSESSMENT_ID_KEY, assessment.getAssessmentId());
                assessmentContext.startActivity(intent);
            }

        });

    }










    @Override
    public int getItemCount() {
        return assessmentList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.assessment_title)
        TextView assessment_title;

        @BindView(R.id.assessment_fab)
        FloatingActionButton assessment_fab;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }



}
