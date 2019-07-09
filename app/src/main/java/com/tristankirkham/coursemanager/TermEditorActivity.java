package com.tristankirkham.coursemanager;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.tristankirkham.coursemanager.database.TermEntity;
import com.tristankirkham.coursemanager.viewmodel.TermEditorViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.tristankirkham.coursemanager.utilities.Constants.TERM_ID_KEY;

public class TermEditorActivity extends AppCompatActivity {

    //Bind view
    @BindView(R.id.term_editor)

    //TODO: Add the rest of the fields here
    TextView tTextView;

    //Register ViewModel
    private TermEditorViewModel tViewModel;
    private boolean tNewTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_editor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        //Initialize the ViewModel
        initViewModel();

    }

    //Initialize ViewModel
    private void initViewModel() {

        tViewModel = ViewModelProviders.of(this).get(TermEditorViewModel.class);
        tViewModel.tLiveTerm.observe(this, new Observer<TermEntity>() {
            @Override
            public void onChanged(@Nullable TermEntity termEntity) {

                //TODO: Add the rest of the fields here
                tTextView.setText(termEntity.getTitle());

            }
        });

        Bundle extras = getIntent().getExtras();

        //Check if Term is new or not, and set title accordingly
        if (extras == null) {
            setTitle("New term");
            tNewTerm = true;

        } else {
            setTitle("Edit note");

            //TODO: Add other fields here
            int termId = extras.getInt(TERM_ID_KEY);
            tViewModel.loadData(termId);



        }

    }
}
