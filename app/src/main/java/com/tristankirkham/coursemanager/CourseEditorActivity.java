package com.tristankirkham.coursemanager;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tristankirkham.coursemanager.database.AssessmentEntity;
import com.tristankirkham.coursemanager.database.CourseEntity;
import com.tristankirkham.coursemanager.recyclerview_adapters.AssessmentAdapter;
import com.tristankirkham.coursemanager.recyclerview_adapters.CourseAdapter;
import com.tristankirkham.coursemanager.utilities.TextFormatter;
import com.tristankirkham.coursemanager.viewmodel.AssessmentViewModel;
import com.tristankirkham.coursemanager.viewmodel.CourseEditorViewModel;
import com.tristankirkham.coursemanager.viewmodel.CourseViewModel;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

import static com.tristankirkham.coursemanager.utilities.Constants.COURSE_ID_KEY;
import static com.tristankirkham.coursemanager.utilities.Constants.EDITING_KEY;
import static com.tristankirkham.coursemanager.utilities.Constants.TERM_ID_KEY;

public class CourseEditorActivity extends AppCompatActivity {

    private CourseEditorViewModel courseEditorViewModel;
    private boolean isNewCourse, isEditing;
    private ArrayAdapter<CharSequence> adapter;
    int term_id = -1;
    private int courseId;
    private List<AssessmentEntity> assessmentData = new ArrayList<>();
    private AssessmentAdapter assessmentAdapter;
    private AssessmentViewModel assessmentViewModel;
    private ShareActionProvider shareActionProvider;
    private String note;

    @BindView(R.id.course_title)
    TextView courseTitleView;

    @BindView(R.id.course_start_date)
    TextView courseStartDateView;

    @BindView(R.id.course_end_date)
    TextView courseEndDateView;

    @BindView(R.id.course_status)
    Spinner courseStatusView;

    @BindView(R.id.course_mentor_name)
    TextView mentorNameView;

    @BindView(R.id.course_mentor_phone)
    TextView mentorPhoneView;

    @BindView(R.id.course_mentor_email)
    TextView mentorEmailView;

    @BindView(R.id.associated_assessments_text)
    TextView associatedAssessmentsText;

    @BindView(R.id.add_assessment_button)
    Button addAssessmentButton;

    @BindView(R.id.course_note)
    TextView noteView;

    @BindView(R.id.assessment_recyclerview)
    RecyclerView assessmentRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_editor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ButterKnife.bind(this);

        //Restore data if device orientation changes
        if (savedInstanceState != null) {

            isEditing = savedInstanceState.getBoolean(EDITING_KEY);

        }

        initRecyclerView();
        initCourseEditorViewModel();
        initSpinner();
        initAssessmentObserver();



        if (isNewCourse == true) {
            addAssessmentButton.setEnabled(false);
            associatedAssessmentsText.setText("Assessments in Course (Save Course to Add Assessments)");

        }


    }

    private void initRecyclerView() {

        //Each item will be the same height
        assessmentRecyclerView.setHasFixedSize(true);

        //Create layoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        //Pass in layoutmanager
        assessmentRecyclerView.setLayoutManager(layoutManager);


    }

    private void initAssessmentObserver() {

        final Observer<List<AssessmentEntity>> assessmentObserver = new Observer<List<AssessmentEntity>>() {
            @Override
            public void onChanged(@Nullable List<AssessmentEntity> assessmentEntities) {
                assessmentData.clear();

                //assessmentData.addAll(assessmentEntities);

               for (AssessmentEntity a : assessmentEntities)
                    if (a.getCourseId() == courseId)
                        assessmentData.add(a);



                if (assessmentAdapter == null) {
                    assessmentAdapter = new AssessmentAdapter(assessmentData, CourseEditorActivity.this);

                    assessmentRecyclerView.setAdapter(assessmentAdapter);

                } else {

                    assessmentAdapter.notifyDataSetChanged();
                }
            }

        };

        assessmentViewModel = ViewModelProviders.of(this).get(AssessmentViewModel.class);
        assessmentViewModel.assessmentList.observe(this, assessmentObserver);




    }

    private void initCourseEditorViewModel() {



        courseEditorViewModel = ViewModelProviders.of(this).get(CourseEditorViewModel.class);
        courseEditorViewModel.courseLiveData.observe(this, new Observer<CourseEntity>() {
            @Override
            public void onChanged(@NonNull CourseEntity courseEntity) {




                if (courseEntity != null && !isEditing) {


                    courseTitleView.setText(courseEntity.getCourseName());
                    courseStartDateView.setText(TextFormatter.fullDateFormat.format(courseEntity.getStartDate()));
                    courseEndDateView.setText(TextFormatter.fullDateFormat.format(courseEntity.getEndDate()));
                    courseStatusView.setSelection(courseEntity.getStatus());
                    mentorNameView.setText(courseEntity.getMentorName());
                    mentorPhoneView.setText(courseEntity.getMentorPhone());
                    mentorEmailView.setText(courseEntity.getMentorEmail());
                    noteView.setText(courseEntity.getNote());



                }


            }
        });

        Bundle extras = getIntent().getExtras();

        //Check if Course is new or not, and set title of edit page accordingly
        if (extras == null) {
            setTitle("New course");
            isNewCourse = true;
            Toast.makeText(this, "Course data blank", Toast.LENGTH_LONG).show();

        } else if ( extras.containsKey(TERM_ID_KEY)) {
            term_id = extras.getInt(TERM_ID_KEY);
            setTitle("New course");
            isNewCourse = true;
            Toast.makeText(this, "Course data contains Term key", Toast.LENGTH_LONG).show();





        } else {
            setTitle("Edit course");
            Toast.makeText(this, "Editing existing course", Toast.LENGTH_LONG).show();
            courseId = extras.getInt(COURSE_ID_KEY);


            courseEditorViewModel.loadData(courseId);






        }

    }


    private void initSpinner() {


        List<CharSequence> courseStatusChoices = new ArrayList<>();

        courseStatusChoices.add("In Progress");
        courseStatusChoices.add("Complete");
        courseStatusChoices.add("Dropped");
        courseStatusChoices.add("Plan on taking next");


        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, courseStatusChoices);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        courseStatusView.setAdapter(adapter);



    }



    private void saveAndReturn() {

        String courseTitle = null;
        int status = 0;
        Date startDate = null;
        Date endDate = null;
        String mentorName = null;
        String mentorPhone = null;
        String mentorEmail = null;
        String note = null;

        try {
            courseTitle = courseTitleView.getText().toString();
            status = courseStatusView.getSelectedItemPosition();
            startDate = TextFormatter.fullDateFormat.parse(courseStartDateView.getText().toString());
            endDate = TextFormatter.fullDateFormat.parse(courseEndDateView.getText().toString());
            mentorName = mentorNameView.getText().toString();
            mentorPhone = mentorPhoneView.getText().toString();
            mentorEmail = mentorEmailView.getText().toString();
            note = noteView.getText().toString();

        } catch (ParseException e) {
            e.printStackTrace();
        }


        courseEditorViewModel.courseLiveData.observe(this, new Observer<CourseEntity>() {
            @Override
            public void onChanged(@NonNull CourseEntity courseEntity) {

                int existingTermID = courseEntity.getTerm_id();




                if (existingTermID >= 0) {
                    term_id = existingTermID;


                }
            }

        });



        if (courseTitle != null && !courseTitle.isEmpty() && startDate != null && !startDate.toString().isEmpty() && !endDate.toString().isEmpty() && !mentorName.isEmpty() && !mentorPhone.isEmpty() && !mentorEmail.isEmpty()) {

            courseEditorViewModel.saveCourse(courseTitle, startDate, endDate, status, mentorName, mentorPhone, mentorEmail, note, term_id);


            finish();


        } else {

            Toast.makeText(this, "Data has NOT been completely entered, please fill out form completely or use device back button to return without saving", Toast.LENGTH_LONG).show();

            //finish();



        }

    }






    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        courseEditorViewModel.courseLiveData.observe(this, new Observer<CourseEntity>() {
            @Override
            public void onChanged(@NonNull CourseEntity courseEntity) {

                note = courseEntity.getNote();


            }

        });



        if (item.getItemId() == android.R.id.home) {

            saveAndReturn();
            return true;

        } else if (item.getItemId() == R.id.action_delete) {

            courseEditorViewModel.deleteCourse();
            finish();

        } else if (item.getItemId() == R.id.menu_item_share) {

            if(!note.isEmpty()) {

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                shareActionProvider.setShareIntent(sharingIntent);
                sharingIntent.setType("text/plain");
                String shareBody = note;
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Shared Course Note");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
                return true;

            } else {
                Toast.makeText(this, "Please save a course note first, and then try again", Toast.LENGTH_LONG).show();
            }

            }




        return super.onOptionsItemSelected(item);

    }


    //Handles saves from the device back button
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        saveAndReturn();
    }


    //Save date when device changes orientation
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(EDITING_KEY, true);
        super.onSaveInstanceState(outState);
    }

    @OnClick(R.id.course_save_button)
    void saveButtonClickHandler() {
        saveAndReturn();
    }




    @Optional
    @OnClick(R.id.add_assessment_button)
    void fabClickHandler() {




        Intent intent = new Intent(this, AssessmentEditorActivity.class);


        if(isNewCourse == false) {



            intent.putExtra(COURSE_ID_KEY, courseId);

        }

        startActivity(intent);
    }



    //Add delete menu option
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!isNewCourse) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_course_editor, menu);

            MenuItem item = menu.findItem(R.id.menu_item_share);

            shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

            return true;

        }
        return super.onCreateOptionsMenu(menu);

    }


    private void setShareIntent(Intent shareIntent) {
        if (shareActionProvider != null) {
            shareActionProvider.setShareIntent(shareIntent);
        }
    }


}
