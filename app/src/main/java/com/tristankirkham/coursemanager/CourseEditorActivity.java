package com.tristankirkham.coursemanager;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.tristankirkham.coursemanager.R;
import com.tristankirkham.coursemanager.viewmodel.CourseEditorViewModel;

import java.util.Date;

import butterknife.BindView;

public class CourseEditorActivity extends AppCompatActivity {

    String termTitle;

    private CourseEditorViewModel courseEditorViewModel;

    @BindView(R.id.course_title)
    TextView courseTitle;

    @BindView(R.id.course_start_date)
    TextView courseStartDate;

    @BindView(R.id.course_end_date)
    TextView courseEndDate;

    @BindView(R.id.course_status)
    int courseStatus;

    @BindView(R.id.course_mentor_name)
    TextView mentorName;

    @BindView(R.id.course_mentor_phone)
    TextView mentorPhone;

    @BindView(R.id.course_mentor_email)
    TextView mentorEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_editor2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        termTitle = getIntent().getStringExtra("term_title");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void saveAndReturn() {

        courseEditorViewModel.saveCourse(courseTitle.getText().toString(), new Date(courseStartDate.getText().toString()), new Date(courseEndDate.getText().toString()), courseStatus, mentorName.getText().toString(), mentorPhone.getText().toString(), mentorEmail.getText().toString(), termTitle);
    }

}
