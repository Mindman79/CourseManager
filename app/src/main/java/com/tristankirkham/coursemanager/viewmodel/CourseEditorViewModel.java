package com.tristankirkham.coursemanager.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.tristankirkham.coursemanager.database.AppRepository;
import com.tristankirkham.coursemanager.database.CourseEntity;
import com.tristankirkham.coursemanager.database.TermEntity;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class CourseEditorViewModel extends AndroidViewModel {


    public MutableLiveData<CourseEntity> courseLiveData = new MutableLiveData<>();
    private AppRepository courseRepository = AppRepository.getInstance(getApplication());
    private Executor executor = Executors.newSingleThreadExecutor();



    public CourseEditorViewModel(@NonNull Application application) {
        super(application);

    }


    public void loadData(final int course_id) {
        executor.execute(new Runnable() {
                             @Override
                             public void run() {

                                 CourseEntity course = courseRepository.getCourseByID(course_id);
                                 courseLiveData.postValue(course);

                             }
                         }

        );

    }


  public void saveCourse(String courseName, Date startDate, Date endDate, int status, String mentorName, String mentorPhone, String mentorEmail, int term_id) {

      CourseEntity course = courseLiveData.getValue();

      if (course == null) {
          if (TextUtils.isEmpty(courseName.trim())) {

              return;

          }
          course = new CourseEntity(courseName.trim(), startDate, endDate, status, mentorName, mentorPhone, mentorEmail, term_id);


      } else {
          course.setCourseName(courseName.trim());
          course.setStartDate(startDate);
          course.setEndDate(endDate);
          course.setStatus(status);
          course.setMentorName(mentorName);
          course.setMentorPhone(mentorPhone);
          course.setMentorEmail(mentorEmail);
          course.setTerm_id(term_id);

      }
      courseRepository.insertCourse(course);


  }


  public void deleteCourse() {

        courseRepository.deleteCourse(courseLiveData.getValue());
  }

}
