package com.tristankirkham.coursemanager.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.tristankirkham.coursemanager.database.AppRepository;
import com.tristankirkham.coursemanager.database.AssessmentEntity;
import com.tristankirkham.coursemanager.database.CourseEntity;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AssessmentEditorViewModel extends AndroidViewModel {


    public MutableLiveData<AssessmentEntity> assessmentLiveData = new MutableLiveData<>();
    private AppRepository assessmentRepository = AppRepository.getInstance(getApplication());
    private Executor executor = Executors.newSingleThreadExecutor();



    public AssessmentEditorViewModel(@NonNull Application application) {
        super(application);
    }


    public void loadData(final int assessment_id) {
        executor.execute(new Runnable() {
                             @Override
                             public void run() {

                                 AssessmentEntity assessment = assessmentRepository.getAssessmentById(assessment_id);
                                 assessmentLiveData.postValue(assessment);

                             }
                         }

        );

    }


    public void saveAssessment(String assessmentName, int assessmentType, Date assessmentDate, int courseId) {

        AssessmentEntity assessment = assessmentLiveData.getValue();

        if (assessment == null) {
            if (TextUtils.isEmpty(assessmentName.trim())) {

                return;

            }
            assessment = new AssessmentEntity(assessmentName.trim(), assessmentType, assessmentDate, courseId);


        } else {
            assessment.setAssessmentName(assessmentName.trim());
            assessment.setAssessmentType(assessmentType);
            assessment.setAssessmentDate(assessmentDate);
            assessment.setCourseId(courseId);

        }
        assessmentRepository.insertAssessment(assessment);


    }


    public void deleteAssessment() {

        assessmentRepository.deleteAssessment(assessmentLiveData.getValue());
    }




}
