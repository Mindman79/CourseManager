package com.tristankirkham.coursemanager.database;

import com.tristankirkham.coursemanager.utilities.SampleData;

import java.util.List;

public class AppRepository {
    private static final AppRepository ourInstance = new AppRepository();

    //TODO: Add other entities?
    public List<TermEntity> termList;

    public static AppRepository getInstance() {
        return ourInstance;
    }

    private AppRepository() {
        termList = SampleData.getTerms();
    }
}
