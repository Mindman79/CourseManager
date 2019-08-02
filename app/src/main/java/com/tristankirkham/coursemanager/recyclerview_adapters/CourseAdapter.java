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

import com.tristankirkham.coursemanager.CourseEditorActivity;
import com.tristankirkham.coursemanager.R;
import com.tristankirkham.coursemanager.database.CourseEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.tristankirkham.coursemanager.utilities.Constants.COURSE_ID_KEY;
import static com.tristankirkham.coursemanager.utilities.Constants.TERM_ID_KEY;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    private final List<CourseEntity> coursesList;
    private final Context coursesContext;

    public CourseAdapter(List<CourseEntity> coursesList, Context coursesContext) {
        this.coursesList = coursesList;
        this.coursesContext = coursesContext;
    }

    //1. Will be called each time a view holder has to be created
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());


        //Call layout view file here
        View view = inflater.inflate(R.layout.course_list_item, parent,false);
        return new ViewHolder(view);



    }

    //Will be each time we want to update the display of a list item (row refresh)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final CourseEntity course = coursesList.get(position);


        //TODO: Add any other fields that needs to be visible in the recyclerview columns
        holder.course_title.setText(course.getCourseName());




        //Set edit FAB click listener
        holder.course_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(coursesContext, CourseEditorActivity.class);
                intent.putExtra(COURSE_ID_KEY, course.getCourse_id());


                coursesContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return coursesList.size();
    }

    //2. Generates instance of this class, must contain view references for any components
    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.course_title)
        TextView course_title;

        @BindView(R.id.course_fab)
        FloatingActionButton course_fab;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
