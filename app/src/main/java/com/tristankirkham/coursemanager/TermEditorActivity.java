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

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.tristankirkham.coursemanager.utilities.Constants.EDITING_KEY;
import static com.tristankirkham.coursemanager.utilities.Constants.TERM_ID_KEY;

public class TermEditorActivity extends AppCompatActivity {

    //Bind view
    @BindView(R.id.term_editor)

    //TODO: Add the rest of the fields here
    TextView tTextView;

    //Register ViewModel
    private TermEditorViewModel tViewModel;
    private boolean tNewTerm, tEditing;

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

            tEditing = savedInstanceState.getBoolean(EDITING_KEY);

        }

        //Initialize the ViewModel
        initViewModel();

    }

    //Initialize ViewModel
    private void initViewModel() {

        tViewModel = ViewModelProviders.of(this).get(TermEditorViewModel.class);
        tViewModel.tLiveTerm.observe(this, new Observer<TermEntity>() {
            @Override
            public void onChanged(@Nullable TermEntity termEntity) {

                if (termEntity != null && !tEditing)

                //TODO: Add the rest of the fields here
                tTextView.setText(termEntity.getTitle());

            }
        });

        Bundle extras = getIntent().getExtras();

        //Check if Term is new or not, and set title accordingly
        if (extras == null) {
            setTitle(R.string.new_term);
            tNewTerm = true;

        } else {
            setTitle(R.string.edit_term);

            //TODO: Add other fields here
            int termId = extras.getInt(TERM_ID_KEY);
            tViewModel.loadData(termId);



        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (!tNewTerm) {

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

        tViewModel.deleteTerm();
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
        tViewModel.saveTerm(tTextView.getText().toString());
        finish();


    }

    //Save date when device changes orientation
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(EDITING_KEY, true);
        super.onSaveInstanceState(outState);
    }
}
