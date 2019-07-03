package com.tristankirkham.coursemanager.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.tristankirkham.coursemanager.database.AppRepository;
import com.tristankirkham.coursemanager.database.TermEntity;
import com.tristankirkham.coursemanager.utilities.SampleData;

import java.util.List;

public class TermViewModel  extends AndroidViewModel {

    public List<TermEntity> termList;
    private AppRepository repository;

    public TermViewModel(@NonNull Application application) {
        super(application);

        repository = AppRepository.getInstance();
        termList = repository.termList;

    }
}
