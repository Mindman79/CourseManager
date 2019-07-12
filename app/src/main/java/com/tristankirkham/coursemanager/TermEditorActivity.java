package com.tristankirkham.coursemanager;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.tristankirkham.coursemanager.database.TermEntity;
import com.tristankirkham.coursemanager.viewmodel.TermEditorViewModel;

import java.text.ParseException;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.tristankirkham.coursemanager.utilities.Constants.EDITING_KEY;
import static com.tristankirkham.coursemanager.utilities.Constants.TERM_ID_KEY;

public class TermEditorActivity extends AppCompatActivity {

    //TODO: Add the rest of the fields here
    //Bind views
    @BindView(R.id.term_title)
    TextView termTitle;

    @BindView(R.id.term_start_date)
    TextView termStartDate;

    @BindView(R.id.term_end_date)
    TextView termEndDate;


    //Register ViewModel
    private TermEditorViewModel termViewModel;
    private boolean isNewTerm, isEditing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_editor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //TODO: Create checkmark icon, and put it here
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        //Restore data if device orientation changes
        if (savedInstanceState != null) {

            isEditing = savedInstanceState.getBoolean(EDITING_KEY);

        }

        //Initialize the ViewModel
        initTermViewModel();

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
            setTitle(R.string.new_term);
            isNewTerm = true;

        } else {
            setTitle(R.string.edit_term);

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

        //TODO: Add other fields here?

        String title = termTitle.getText().toString();
        String startDate = termStartDate.getText().toString();
        String endDate = termEndDate.getText().toString();

        termViewModel.saveTerm(termTitle.getText().toString(), termStartDate.getText().toString(), termEndDate.getText().toString());


        finish();


    }






    //Save date when device changes orientation
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(EDITING_KEY, true);
        super.onSaveInstanceState(outState);
    }







}
