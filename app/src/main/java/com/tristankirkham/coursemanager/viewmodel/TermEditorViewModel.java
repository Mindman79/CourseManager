package com.tristankirkham.coursemanager.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.tristankirkham.coursemanager.database.AppRepository;
import com.tristankirkham.coursemanager.database.TermEntity;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TermEditorViewModel extends AndroidViewModel {

    public MutableLiveData<TermEntity> tLiveTerm = new MutableLiveData<>();
    private AppRepository tRepository;
    private Executor executor = Executors.newSingleThreadExecutor();

    public TermEditorViewModel(@NonNull Application application) {
        super(application);
        tRepository = AppRepository.getInstance(getApplication());
    }


    public void loadData(final int termId) {

        executor.execute(new Runnable() {
            @Override
            public void run() {
                TermEntity term = tRepository.getTermById(termId);
                tLiveTerm.postValue(term);
            }
        });


    }

    public void saveTerm(String termTitle, Date startDate, Date endDate) {
        TermEntity term = tLiveTerm.getValue();


        if (term == null) {
            if (TextUtils.isEmpty(termTitle.trim())) {

                return;

            }
            term = new TermEntity(termTitle.trim(), startDate, endDate);


        } else {
            term.setTitle(termTitle.trim());
            term.setStartDate(startDate);
            term.setEndDate(endDate);

        }
        tRepository.insertTerm(term);




    }

    public void deleteTerm() {

        tRepository.deleteTerm(tLiveTerm.getValue());

    }
}
