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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tristankirkham.coursemanager.database.CourseEntity;
import com.tristankirkham.coursemanager.database.TermEntity;
import com.tristankirkham.coursemanager.recyclerview_adapters.CourseAdapter;
import com.tristankirkham.coursemanager.viewmodel.CourseViewModel;
import com.tristankirkham.coursemanager.viewmodel.TermEditorViewModel;
import com.tristankirkham.coursemanager.viewmodel.TermViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;

import static com.tristankirkham.coursemanager.utilities.Constants.EDITING_KEY;
import static com.tristankirkham.coursemanager.utilities.Constants.TERM_ID_KEY;

public class TermEditorActivity extends AppCompatActivity {




    //Bind views
    @BindView(R.id.term_title)
    TextView termTitleView;

    @BindView(R.id.term_start_date)
    TextView termStartDateView;

    @BindView(R.id.term_end_date)
    TextView termEndDateView;

    //Course RecyclerView
    @BindView(R.id.course_recyclerview)
    RecyclerView courseRecyclerView;

    @BindView(R.id.courseSelector)
    Spinner courseSelector;


    //Register ViewModel
    private TermEditorViewModel termEditorViewModel;
    private boolean isNewTerm, isEditing;

    //New instance of CourseAdapter
    private CourseAdapter courseAdapter;

    private List<CourseEntity> courseData = new ArrayList<>();
    private List<TermEntity> termData = new ArrayList<>();
    private CourseViewModel courseViewModel;

    private ArrayAdapter<CourseEntity> dataAdapter;
    private ArrayAdapter<CharSequence> adapter;






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


        initRecyclerView();
        initTermViewModel();
        initCourseObserver();
        initSpinner();


    }



    @OnItemSelected(R.id.courseSelector)
    public void initCourseSelectorSpinner(int position) {

        courseSelector.getItemAtPosition(position);




    }

    private Object getSpinnerObject() {


        return courseSelector.getSelectedItem().toString();
    }

    private void initSpinner() {


        /*List<CharSequence> courseStatusChoices = new ArrayList<>();

        courseStatusChoices.add("In Progress");
        courseStatusChoices.add("Complete");
        courseStatusChoices.add("Next");


        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, courseStatusChoices);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        courseSelector.setAdapter(adapter);
*/

        ArrayList<CourseEntity> availableCourses = new ArrayList<>();
        for (CourseEntity e : courseData) {


            availableCourses.add(e);


            dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, availableCourses);


            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


            courseSelector.setAdapter(dataAdapter);


        }
    }






    private void initCourseObserver() {

        final Observer<List<CourseEntity>> courseObserver = new Observer<List<CourseEntity>>() {
            @Override
            public void onChanged(@Nullable List<CourseEntity> courseEntities) {
                courseData.clear();

                //TODO: finish this
                /*for (CourseEntity c : courseEntities)
                    if (c.getTerm_id() == TERM_ID_KEY)
                        courseData.add(t);*/

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

        termEditorViewModel = ViewModelProviders.of(this).get(TermEditorViewModel.class);
        termEditorViewModel.tLiveTerm.observe(this, new Observer<TermEntity>() {
            @Override
            public void onChanged(@Nullable TermEntity termEntity) {

                if (termEntity != null && !isEditing)


                    termTitleView.setText(termEntity.getTitle());
                termStartDateView.setText(termEntity.getStartDate().toString());
                termEndDateView.setText(termEntity.getEndDate().toString());


            }
        });

        Bundle extras = getIntent().getExtras();

        //Check if Term is new or not, and set title of edit page accordingly
        if (extras == null) {
            setTitle("New term");
            isNewTerm = true;

        } else {
            setTitle("Edit term");

            int termId = extras.getInt(TERM_ID_KEY);
            termEditorViewModel.loadData(termId);




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

            termEditorViewModel.deleteTerm();
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


        String termTitle = termTitleView.getText().toString();
        if (termTitle != null && !termTitle.isEmpty()) {

            termEditorViewModel.saveTerm(termTitle, new Date(termStartDateView.getText().toString()), new Date(termEndDateView.getText().toString()));


            finish();

        } else {

            Toast.makeText(this, "Data has NOT been entered, returning to previous screen", Toast.LENGTH_SHORT).show();

            finish();
        }


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


    @OnClick(R.id.add_course_button)
    void fabClickHandler() {


        Intent intent = new Intent(this, CourseEditorActivity.class);
        //TODO: Show instructor this

        if(isNewTerm == false) {


            //int termId = termEditorViewModel.getAssociatedTerm();


            //int termId = test.getInt(TERM_ID_KEY);

            //intent.putExtra(TERM_ID_KEY, termId);

        }

        startActivity(intent);
    }


}
