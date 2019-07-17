package com.tristankirkham.coursemanager.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tristankirkham.coursemanager.R;
import com.tristankirkham.coursemanager.database.CourseEntity;
import com.tristankirkham.coursemanager.database.TermEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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


        //TODO: Add the other fields
        holder.course_title.setText(course.getCourseName());

        //TODO: Add FAB click listener


    }

    @Override
    public int getItemCount() {
        return coursesList.size();
    }

    //2. Generates instance of this class, must contain view references for any components
    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.course_title)
        TextView course_title;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
