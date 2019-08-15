package com.tristankirkham.coursemanager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tristankirkham.coursemanager.database.AssessmentEntity;
import com.tristankirkham.coursemanager.database.CourseEntity;
import com.tristankirkham.coursemanager.utilities.MyReceiver;
import com.tristankirkham.coursemanager.viewmodel.AssessmentEditorViewModel;
import com.tristankirkham.coursemanager.viewmodel.CourseEditorViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.tristankirkham.coursemanager.utilities.Constants.ASSESSMENT_ID_KEY;
import static com.tristankirkham.coursemanager.utilities.Constants.COURSE_ID_KEY;
import static com.tristankirkham.coursemanager.utilities.Constants.EDITING_KEY;
import static com.tristankirkham.coursemanager.utilities.Constants.TERM_ID_KEY;

public class AssessmentEditorActivity extends AppCompatActivity {


    private AssessmentEditorViewModel assessmentEditorViewModel;
    private boolean isNewAssessment, isEditing;
    private ArrayAdapter<CharSequence> adapter;
    int course_id = -1;
    private Date assessmentDate;
    private String assessmentTitle;


    @BindView(R.id.assessment_title)
    TextView assessmentTitleView;

    @BindView(R.id.assessment_date)
    TextView assessmentDateView;

    @BindView(R.id.assessment_type_selector)
    Spinner assessmentTypeView;

    @BindView(R.id.set_notification_button)
    Button setNotificationButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_editor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        if (savedInstanceState != null) {

            isEditing = savedInstanceState.getBoolean(EDITING_KEY);

        }


        initAssessmentViewModel();

        initSpinner();




    }

    private void initSpinner() {

        List<CharSequence> assessmentTypes = new ArrayList<>();

        assessmentTypes.add("Performance");
        assessmentTypes.add("Objective");


        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, assessmentTypes);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        assessmentTypeView.setAdapter(adapter);

    }

    private void initAssessmentViewModel() {

        assessmentEditorViewModel = ViewModelProviders.of(this).get(AssessmentEditorViewModel.class);
        assessmentEditorViewModel.assessmentLiveData.observe(this, new Observer<AssessmentEntity>() {
            @Override
            public void onChanged(@NonNull AssessmentEntity assessmentEntity) {


                if (assessmentEntity != null && !isEditing) {


                    assessmentTitleView.setText(assessmentEntity.getAssessmentName());
                    assessmentDateView.setText(assessmentEntity.getAssessmentDate().toString());
                    assessmentTypeView.setSelection(assessmentEntity.getAssessmentType());


                }


            }
        });

        Bundle extras = getIntent().getExtras();


        if (extras == null) {
            setTitle("New assessment");
            isNewAssessment = true;
            Toast.makeText(this, "Assessment data blank", Toast.LENGTH_LONG).show();

        } else if (extras.containsKey(COURSE_ID_KEY)) {
            course_id = extras.getInt(COURSE_ID_KEY);
            setTitle("New assessment");
            isNewAssessment = true;
            Toast.makeText(this, "Assessment data contains Course key", Toast.LENGTH_LONG).show();


        } else {
            setTitle("Edit assessment");
            Toast.makeText(this, "Editing existing assessment", Toast.LENGTH_LONG).show();
            int assessmentId = extras.getInt(ASSESSMENT_ID_KEY);


            assessmentEditorViewModel.loadData(assessmentId);


        }

    }



    private void saveAndReturn() {

        String assessmentTitle = assessmentTitleView.getText().toString();
        int assessmentType = assessmentTypeView.getSelectedItemPosition();


        assessmentEditorViewModel.assessmentLiveData.observe(this, new Observer<AssessmentEntity>() {
            @Override
            public void onChanged(@NonNull AssessmentEntity assessmentEntity) {

                int existingCourseId = assessmentEntity.getCourseId();




                if (existingCourseId >= 0) {
                    course_id = existingCourseId;


                }
            }

        });



        if (assessmentTitle != null && !assessmentTitle.isEmpty()) {

            assessmentEditorViewModel.saveAssessment(assessmentTitle, assessmentType, new Date(assessmentDateView.getText().toString()), course_id);


            finish();


        } else {

            Toast.makeText(this, "Data has NOT been entered, returning to previous screen", Toast.LENGTH_SHORT).show();

            finish();


        }

    }






    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            saveAndReturn();
            return true;
        } else if (item.getItemId() == R.id.action_delete) {

            assessmentEditorViewModel.deleteAssessment();
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


    //Save date when device changes orientation
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(EDITING_KEY, true);
        super.onSaveInstanceState(outState);
    }

    @OnClick(R.id.save_assessment_button)
    void saveButtonClickHandler() {
        saveAndReturn();
    }


    @OnClick(R.id.set_notification_button)
    void notificationButtonHandler() {



        assessmentEditorViewModel.assessmentLiveData.observe(this, new Observer<AssessmentEntity>() {
            @Override
            public void onChanged(@NonNull AssessmentEntity assessmentEntity) {

                assessmentDate = assessmentEntity.getAssessmentDate();
                assessmentTitle = assessmentEntity.getAssessmentName();


            }

        });


        Intent intent = new Intent(AssessmentEditorActivity.this, MyReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(AssessmentEditorActivity.this, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, assessmentDate.getTime() + 1000, sender);

    }









//Add delete menu option
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!isNewAssessment) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_assessment_editor, menu);

        }
        return super.onCreateOptionsMenu(menu);

    }







}
