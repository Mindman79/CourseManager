package com.tristankirkham.coursemanager.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.tristankirkham.coursemanager.database.AppRepository;
import com.tristankirkham.coursemanager.database.TermEntity;

import java.util.List;

public class TermViewModel  extends AndroidViewModel {

    public LiveData<List<TermEntity>> termList;
    private AppRepository repository;

    public TermViewModel(@NonNull Application application) {
        super(application);

        repository = AppRepository.getInstance(application.getApplicationContext());
        termList = repository.termList;

    }

    public void addSampleData() {
        repository.addSampleData();

    }

    public void deleteAllData() {
        repository.deleteAllData();

    }
}
