package com.onramp.android.takehome;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import java.util.Calendar;

public class WorkOutReminderReceiver extends BroadcastReceiver
{
    private static final String CHANNEL_ID = "51605";

    private String time;
    private int hour;
    private int minute;
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Intent notificationService = new Intent(context, NotificationService.class);

        context.startService(notificationService);
    }



}
