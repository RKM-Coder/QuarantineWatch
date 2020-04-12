package com.goalsr.homequarantineTracker.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;


import com.goalsr.homequarantineTracker.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private String key_CUSTOMER="408";
    private String key_VISIT="706";
    private String key_TASK="806";
    private String key_MASTER="1301";
    private String key_EXPENSE="902";
    private String key_LEAVE="418";
    private String key_USER_BLOCK="3";//
    private String key_USER_ACTIVE="1";


    @Override
    public void onMessageReceived(RemoteMessage message) {
        super.onMessageReceived(message);
        displayNotification(message.getNotification().getTitle(),message.getNotification().getBody());

    }

    private void displayNotification(String title, String task) {
        /*Intent intent = new Intent(this, Announcements.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);*/
        /*PendingIntent pendingIntent = PendingIntent.getActivity(this, 1410,
                intent, PendingIntent.FLAG_ONE_SHOT);
*/
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);



        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, "Arygo_mitra")
                .setContentTitle(title)
                .setContentText(task)
                /*.setContentIntent(pendingIntent)*/
                .setSmallIcon(R.mipmap.ic_launcher)
                .setColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(title))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(task).setSummaryText(""))
                .setShowWhen(true)
                .setAutoCancel(true);;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("Arygo_mitra", "Arogya Mitra", NotificationManager.IMPORTANCE_HIGH);
            notification.setChannelId("Arygo_mitra");
            notification.setPriority(NotificationCompat.PRIORITY_HIGH);

            notificationManager.createNotificationChannel(channel);
        }
        NotificationManagerCompat mgrCompat= NotificationManagerCompat.from(this);

        mgrCompat.notify(991, notification.build());
    }

    @Override
    public void onNewToken(@NonNull String token) {
        Log.d(TAG, "Refreshed token: " + token);
       // sendRegistrationToServer(token);
    }
}
