package com.tristankirkham.coursemanager;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.tristankirkham.coursemanager.database.CourseEntity;
import com.tristankirkham.coursemanager.database.TermEntity;
import com.tristankirkham.coursemanager.ui.CourseAdapter;
import com.tristankirkham.coursemanager.viewmodel.CourseEditorViewModel;
import com.tristankirkham.coursemanager.viewmodel.CourseViewModel;
import com.tristankirkham.coursemanager.viewmodel.TermEditorViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.tristankirkham.coursemanager.utilities.Constants.EDITING_KEY;
import static com.tristankirkham.coursemanager.utilities.Constants.TERM_ID_KEY;

public class TermEditorActivity extends AppCompatActivity {


    //Bind views
    @BindView(R.id.term_title)
    TextView termTitle;

    @BindView(R.id.term_start_date)
    TextView termStartDate;

    @BindView(R.id.term_end_date)
    TextView termEndDate;

    //Course RecyclerView
    @BindView(R.id.course_recyclerview)
    RecyclerView courseRecyclerView;


    //Register ViewModel
    private TermEditorViewModel termViewModel;
    private boolean isNewTerm, isEditing;

    //New instance of CourseAdapter
    private CourseAdapter courseAdapter;

    private List<CourseEntity> courseData = new ArrayList<>();
    private CourseViewModel courseViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_editor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //TODO: Create checkmark icon, and put it here
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Bind Butterknife views
        ButterKnife.bind(this);

        //Restore data if device orientation changes
        if (savedInstanceState != null) {

            isEditing = savedInstanceState.getBoolean(EDITING_KEY);

        }

        //Initialize the ViewModels
        initTermViewModel();
        initCourseViewModel();

        //Initialize the course RecyclerView
        initRecyclerView();

    }

    private void initCourseViewModel() {

        final Observer<List<CourseEntity>> courseObserver = new Observer<List<CourseEntity>>() {
            @Override
            public void onChanged(@Nullable List<CourseEntity> courseEntities) {
                courseData.clear();
                courseData.addAll(courseEntities);

                if (courseAdapter == null) {
                    courseAdapter = new CourseAdapter(courseData, TermEditorActivity.this);

                    courseRecyclerView.setAdapter(courseAdapter);

                } else {

                    courseAdapter.notifyDataSetChanged();
                }
            }

        };

        courseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);
        courseViewModel.courseList.observe(this, courseObserver);



    }









    private void initRecyclerView() {

        //Each item will be the same height
        courseRecyclerView.setHasFixedSize(true);

        //Create layoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        //Pass in layoutmanager
        courseRecyclerView.setLayoutManager(layoutManager);



    }

    //Initialize ViewModel
    private void initTermViewModel() {

        termViewModel = ViewModelProviders.of(this).get(TermEditorViewModel.class);
        termViewModel.tLiveTerm.observe(this, new Observer<TermEntity>() {
            @Override
            public void onChanged(@Nullable TermEntity termEntity) {

                if (termEntity != null && !isEditing)



                    //TODO: Add the rest of the fields here
                    termTitle.setText(termEntity.getTitle());
                    termStartDate.setText(termEntity.getStartDate().toString());
                    termEndDate.setText(termEntity.getEndDate().toString());


            }
        });

        Bundle extras = getIntent().getExtras();

        //Check if Term is new or not, and set title of edit page accordingly
        if (extras == null) {
            setTitle("New term");
            isNewTerm = true;

        } else {
            setTitle("Edit term");

            //TODO: Add other fields here (maybe)
            int termId = extras.getInt(TERM_ID_KEY);
            termViewModel.loadData(termId);


        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (!isNewTerm) {

            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_term_editor, menu);
        }

        return super.onCreateOptionsMenu(menu);
    }

    //Handle saves with the checkmark button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            saveAndReturn();
            return true;
        } else if (item.getItemId() == R.id.action_delete) {

            termViewModel.deleteTerm();
            finish();


        }

        return super.onOptionsItemSelected(item);

    }

    //Handles saves from the device back button
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        saveAndReturn();
    }

    private void saveAndReturn() {




        termViewModel.saveTerm(termTitle.getText().toString(), new Date(termStartDate.getText().toString()), new Date(termEndDate.getText().toString()));


        finish();





    }






    //Save date when device changes orientation
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(EDITING_KEY, true);
        super.onSaveInstanceState(outState);
    }



    @OnClick(R.id.save_button)
    void saveButtonClickHandler() {
        saveAndReturn();
    }


    //TODO: Change this to course editor activity
    @OnClick(R.id.add_course_button)
    void fabClickHandler() {
        Intent intent = new Intent(this, CourseEditorActivity.class);
        intent.putExtra("term_title", String.valueOf(termTitle));
        startActivity(intent);
    }




}
