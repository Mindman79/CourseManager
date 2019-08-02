package com.tristankirkham.coursemanager.database;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.tristankirkham.coursemanager.utilities.SampleData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppRepository {
    private static AppRepository ourInstance;


    public LiveData<List<TermEntity>> termList;
    public LiveData<List<CourseEntity>> courseList;
    public LiveData<List<AssessmentEntity>> assessmentList;
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
        courseList = getAllCourses();
        assessmentList = getAllAssessments();


    }



    public void addSampleData() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Db.courseDao().InsertAllCourses(SampleData.getCourses());
                Db.termDao().insertAllTerms(SampleData.getTerms());
            }
        });



    }


    //Term
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

    public void insertTerm(final TermEntity term) {
       executor.execute(new Runnable() {
           @Override
           public void run() {
               Db.termDao().insertTerm(term);

           }
       });


    }

    public void deleteTerm(final TermEntity term) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Db.termDao().deleteTerm(term);
            }
        });



    }

    //Course

    private LiveData<List<CourseEntity>> getAllCourses() {
        return Db.courseDao().getAllCourses();


    }

    public CourseEntity getCourseByID(int course_id) {

        return Db.courseDao().getCourseById(course_id);

    }


    public LiveData<List<CourseEntity>> getAssociatedTerm(int term_id) {

        return Db.courseDao().getAssociatedTerm(term_id);

    }

    public void insertCourse(final CourseEntity course) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Db.courseDao().insertCourse(course);
            }
        });

    }

    public void deleteCourse(final CourseEntity value) {

        executor.execute(new Runnable() {
            @Override
            public void run() {
                Db.courseDao().deleteCourse(value);
            }
        });

    }


    //Assessment

    private LiveData<List<AssessmentEntity>> getAllAssessments() {
        return Db.assessmentDao().getAllAssessments();
    }

    public AssessmentEntity getAssessmentById(int assessmentId) {

        return Db.assessmentDao().getAssessmentById(assessmentId);

    }


    public void insertAssessment(final AssessmentEntity assessment) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Db.assessmentDao().insertAssessment(assessment);
            }
        });

    }

    public void deleteAssessment(final AssessmentEntity value) {

        executor.execute(new Runnable() {
            @Override
            public void run() {
                Db.assessmentDao().deleteAssessment(value);
            }
        });

    }


}
