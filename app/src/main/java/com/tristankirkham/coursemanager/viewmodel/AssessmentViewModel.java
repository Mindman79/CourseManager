package com.tristankirkham.coursemanager.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.tristankirkham.coursemanager.database.AppRepository;
import com.tristankirkham.coursemanager.database.AssessmentEntity;
import com.tristankirkham.coursemanager.database.CourseEntity;

import java.util.List;

public class AssessmentViewModel extends AndroidViewModel {

    public LiveData<List<AssessmentEntity>> assessmentList;
    private AppRepository repository;


    public AssessmentViewModel(@NonNull Application application) {
        super(application);

        repository = AppRepository.getInstance(application.getApplicationContext());
        assessmentList = repository.assessmentList;

    }
}
