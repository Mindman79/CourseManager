package com.tristankirkham.coursemanager.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

@Database(entities = {TermEntity.class, CourseEntity.class, AssessmentEntity.class}, exportSchema = false, version = 1)

@TypeConverters(DateConverter.class)

public abstract class AppDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "Db.db";
    private static volatile AppDatabase instance;
    private static final Object LOCK = new Object();

    //TODO add all the rest of the Daos when they exist
    public abstract TermDao termDao();


    //Get instance of DB, confirm it's not null, and then build it
    public static AppDatabase getInstance(Context context) {
        if(instance == null) {
            synchronized (LOCK) {
                if(instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME).build();
                }
            }
        }
        return instance;
    }
}
