package com.tristankirkham.coursemanager.utilities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.tristankirkham.coursemanager.R;

import butterknife.BindView;

public class Spinner extends AppCompatActivity implements AdapterView.OnItemSelectedListener {



    private Spinner courseSelector;

    /*private void initSpinner() {


       courseSelector = findViewById(R.id.course_status);



    }
*/








    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
    }


    public void onNothingSelected(AdapterView<?> parent) {
    }
}
