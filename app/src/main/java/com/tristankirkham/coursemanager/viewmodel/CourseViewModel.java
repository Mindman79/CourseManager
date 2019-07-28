package com.tristankirkham.coursemanager.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.tristankirkham.coursemanager.database.AppRepository;
import com.tristankirkham.coursemanager.database.CourseEntity;

import java.util.List;


public class CourseViewModel extends AndroidViewModel {

    public LiveData<List<CourseEntity>> courseList;
    private AppRepository repository;


    public CourseViewModel(@NonNull Application application) {
        super(application);


        repository = AppRepository.getInstance(application.getApplicationContext());
        courseList = repository.courseList;



    }
}
