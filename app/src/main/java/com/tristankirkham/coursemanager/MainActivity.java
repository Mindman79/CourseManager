package com.tristankirkham.coursemanager;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.tristankirkham.coursemanager.database.TermEntity;
import com.tristankirkham.coursemanager.recyclerview_adapters.TermAdapter;
import com.tristankirkham.coursemanager.viewmodel.TermViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    //Bind view
    @BindView(R.id.recycler_view)
    RecyclerView mainRecyclerView;

    //Floating action button click handler
    @OnClick(R.id.fab)
    void fabClickHandler() {
        Intent intent = new Intent(this, TermEditorActivity.class);
        startActivity(intent);
    }

    //Sample data section
    private List<TermEntity> termData = new ArrayList<>();

    //Declare adapter
    private TermAdapter tAdapter;

    //Declare ViewModel class in a field
    private TermViewModel termViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Bind view
        ButterKnife.bind(this);

        //Init RecyclerView
        initRecyclerView();

        //Init ViewModel
        initViewModel();


    }

    private void initViewModel() {


        final Observer<List<TermEntity>> termsObserver = new Observer<List<TermEntity>>() {
            @Override
            public void onChanged(@Nullable List<TermEntity> termEntities) {
                termData.clear();


                termData.addAll(termEntities);


                if (tAdapter == null) {

                    //Instantiate the adapter
                    tAdapter = new TermAdapter(termData, MainActivity.this);
                    mainRecyclerView.setAdapter(tAdapter);

                } else {
                    tAdapter.notifyDataSetChanged();
                }

            }
        };

        termViewModel = ViewModelProviders.of(this)
                .get(TermViewModel.class);

        termViewModel.termList.observe(this, termsObserver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_sample_data) {
            //TODO: Remove this sample data reference, but adding it here for now
            addSampleData();
            return true;

        } else if (id == R.id.action_delete_all) {

            deleteAllTerms();
            return true;
        }



        return super.onOptionsItemSelected(item);
    }

    private void deleteAllTerms() {

        termViewModel.deleteAllTerms();

    }

    private void addSampleData() {

        termViewModel.addSampleData();

    }


    //Sets each RecyclerView item to same height
    //Set list as linear layout
    private void initRecyclerView() {
        mainRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mainRecyclerView.setLayoutManager(layoutManager);

        //Set divider lines
        DividerItemDecoration divider = new DividerItemDecoration(mainRecyclerView.getContext(), layoutManager.getOrientation());
        mainRecyclerView.addItemDecoration(divider);



    }


}
