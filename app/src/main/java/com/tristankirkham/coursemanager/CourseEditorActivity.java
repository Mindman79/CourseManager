package com.tristankirkham.coursemanager;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tristankirkham.coursemanager.database.CourseEntity;
import com.tristankirkham.coursemanager.database.TermEntity;
import com.tristankirkham.coursemanager.utilities.TextFormatter;
import com.tristankirkham.coursemanager.viewmodel.CourseEditorViewModel;
import com.tristankirkham.coursemanager.viewmodel.TermEditorViewModel;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.tristankirkham.coursemanager.utilities.Constants.COURSE_ID_KEY;
import static com.tristankirkham.coursemanager.utilities.Constants.EDITING_KEY;
import static com.tristankirkham.coursemanager.utilities.Constants.TERM_ID_KEY;

public class CourseEditorActivity extends AppCompatActivity {

    private CourseEditorViewModel courseEditorViewModel;
    private boolean isNewCourse, isEditing;

    Object courseEntity = new Object();

    private ArrayAdapter<CharSequence> adapter;

    String termTitle;

    private CharSequence position;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_editor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        termTitle = getIntent().getStringExtra("term_title");


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ButterKnife.bind(this);

        //Restore data if device orientation changes
        if (savedInstanceState != null) {

            isEditing = savedInstanceState.getBoolean(EDITING_KEY);

        }

        //Initialize the ViewModel
        initCourseViewModel();

        //Initialize spinner
        initSpinner();


    }

    private void initCourseViewModel() {



        courseEditorViewModel = ViewModelProviders.of(this).get(CourseEditorViewModel.class);
        courseEditorViewModel.courseLiveData.observe(this, new Observer<CourseEntity>() {
            @Override
            public void onChanged(@NonNull CourseEntity courseEntity) {




                if (courseEntity != null && !isEditing)


                    courseTitleView.setText(courseEntity.getCourseName());
                courseStartDateView.setText(courseEntity.getStartDate().toString());
                courseEndDateView.setText(courseEntity.getEndDate().toString());
                int position = getSpinnerPosition();
                courseStatusView.setSelection(position);
                mentorNameView.setText(courseEntity.getMentorName());
                mentorPhoneView.setText(courseEntity.getMentorPhone());
                mentorEmailView.setText(courseEntity.getMentorEmail());


            }
        });

        Bundle extras = getIntent().getExtras();

        //Check if Course is new or not, and set title of edit page accordingly
        if (extras == null) {
            setTitle("New course");
            isNewCourse = true;
            Toast.makeText(this, "Course data blank", Toast.LENGTH_LONG).show();

        } else {
            setTitle("Edit course");
            Toast.makeText(this, "Course data is NOT blank", Toast.LENGTH_LONG).show();


       int courseId = extras.getInt(COURSE_ID_KEY);
            courseEditorViewModel.loadData(courseId);



        }

    }


    private void initSpinner() {


        List<CharSequence> courseStatusChoices = new ArrayList<>();

        courseStatusChoices.add("In Progress");
        courseStatusChoices.add("Complete");
        courseStatusChoices.add("Next");


        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, courseStatusChoices);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        courseStatusView.setAdapter(adapter);



    }

    private Object getSpinnerValue() {

        return courseStatusView.getSelectedItem();
    }

    private int getSpinnerPosition() {

        return adapter.getPosition(position);


    }





    private void saveAndReturn() {

        int i = (int) getSpinnerPosition();
        String courseTitle = courseTitleView.getText().toString();





       courseEditorViewModel.saveCourse(courseTitle, new Date(courseStartDateView.getText().toString()), new Date(courseEndDateView.getText().toString()), i, mentorNameView.getText().toString(), mentorPhoneView.getText().toString(), mentorEmailView.getText().toString(), termTitle);

        finish();



        /*try {
            Date startDate = TextFormatter.fullDateFormat.parse(courseStartDate.getText().toString());
            Date endDate = TextFormatter.fullDateFormat.parse(courseEndDate.getText().toString());

            courseEditorViewModel.saveCourse(courseTitle.getText().toString(), startDate, endDate, i, mentorName.getText().toString(), mentorPhone.getText().toString(), mentorEmail.getText().toString(), termTitle);

            finish();


        } catch (ParseException e) {
            e.printStackTrace();
        }
*/


    }


    //Handle saves with the checkmark button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            saveAndReturn();
            return true;
        } else if (item.getItemId() == R.id.action_delete) {

            courseEditorViewModel.deleteCourse();
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

    @OnClick(R.id.course_save_button)
    void saveButtonClickHandler() {
        saveAndReturn();
    }


    //Add delete menu option
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!isNewCourse) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_term_editor, menu);

        }
        return super.onCreateOptionsMenu(menu);

    }

}
