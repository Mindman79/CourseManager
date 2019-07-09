package com.tristankirkham.coursemanager.database;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.tristankirkham.coursemanager.utilities.SampleData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppRepository {
    private static AppRepository ourInstance;

    //TODO: Add other entities?
    public LiveData<List<TermEntity>> termList;
    private AppDatabase Db;
    private Executor executor = Executors.newSingleThreadExecutor();


    public static AppRepository getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new AppRepository(context);
        }
        return ourInstance;
    }

    private AppRepository(Context context) {
        Db = AppDatabase.getInstance(context);
        termList = getAllTerms();


    }

    public void addSampleData() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Db.termDao().insertAll(SampleData.getTerms());
            }
        });



    }

    //Method where we determine where the data is coming from
    private LiveData<List<TermEntity>> getAllTerms() {
        return Db.termDao().getAll();
    }

    public void deleteAllTerms() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Db.termDao().deleteAll();
            }
        });

    }

    public TermEntity getTermById(int termId) {
        return Db.termDao().getTermById(termId);
    }
}
