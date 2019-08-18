package com.tristankirkham.coursemanager.utilities;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.tristankirkham.coursemanager.R;

import static android.content.Context.NOTIFICATION_SERVICE;

public class CourseStartReceiver extends BroadcastReceiver {

    static int notificationID;
    String channel_id = "test";


    @Override
    public void onReceive(Context context, Intent intent) {


        String courseName = intent.getStringExtra("CourseTitle");

        String notificationTitle = null;
        String notificationContents = null;


        if (courseName != null) {


            notificationTitle = "WGU course " + courseName + " starts today!";
            notificationContents = "Please begin reviewing course materials";


            Toast.makeText(context, notificationTitle, Toast.LENGTH_LONG).show();
            createNotificationChannel(context, channel_id);
            Notification n = new NotificationCompat.Builder(context, channel_id)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle(notificationTitle + " - ID: " + Integer.toString(notificationID))
                    .setContentText(notificationContents).build();
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(notificationID++, n);

            intent.getExtras().clear();



        }




    }


    private void createNotificationChannel(Context context, String CHANNEL_ID) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getResources().getString(R.string.channel_name);
            String description = context.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
