package com.onramp.android.takehome;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

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
