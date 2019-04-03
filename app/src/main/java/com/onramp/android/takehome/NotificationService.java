package com.onramp.android.takehome;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Designed to notify user to remind them to workout and
 * track work outs via notification banner and audible sound.
 */
public class NotificationService extends IntentService
{

    private static final String CHANNEL_ID = "51605";

    public NotificationService()
    {
        super("NotificationService");
    }

    /**
     * Callback runs when service is started from Broadcast Reciever
     * @param intent
     */
    @Override
    protected void onHandleIntent(Intent intent)
    {
        Log.d("NOTIFICATION SERVICE: ", "service is running");
        if(intent == null)
        {
            Log.d("FAITH: " , "PASSION");
        }

        if(intent != null)
        {
            String time = intent.getStringExtra("time");

            initNotificationChannel(this);
            initNotifier(this, time);


        }
    }


    /**
     * Initializes notificaiton channel fo default important. Also
     * initilization notification manager which are necessary steps to display notifications.
     */
    private void initNotificationChannel(Context con) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = con.getString(R.string.channel_name);

            String description = con.getString(R.string.channel_description);

            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);

            channel.setDescription(description);

            NotificationManager notificationManager = con.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    /**
     * Initialization Notification that is launched via notificaiton manager.
     * Uses builder pattern to generator a Notification Object.
     * Notificaiton manager takes parameter of notification object and sends it
     * android system.
     */
    private void initNotifier(Context context, String time)
    {
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_fitness_center_black_24dp)
                .setContentTitle("VIGOR")
                .setContentText("Workout Reminder set for "  + time + " daily. \n" +
                        "Enjoy the process")
                .setSound(uri)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(123, mBuilder.build());
    }

}
