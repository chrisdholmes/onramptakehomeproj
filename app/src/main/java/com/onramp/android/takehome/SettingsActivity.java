package com.onramp.android.takehome;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Settings Activity uses a Switch and TimePicker to set daily notifications at
 * a user's requested time to remind them to workout.
 */
public class SettingsActivity extends AppCompatActivity implements View.OnClickListener
{
    private Switch switchBtn;
    private TimePicker timePicker;
    private Button saveBtn;
    private Intent notificationServiceIntent;
    private String time;
    private boolean notifyBool;
    private final String NOTIFY_BOOL = "notifyBool";
    private final String SHARED_PREFERENCES = "sharedPrefs";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        switchBtn = findViewById(R.id.reminderSwitch);
        saveBtn = findViewById(R.id.saveBtn);
        timePicker = findViewById(R.id.timePicker);
        notificationServiceIntent = new Intent(this, NotificationService.class);

        loadPrefs();

        Log.d("SETTINGSACTIVITY: ", "" + notifyBool);

    }

    /**
     * Return to main activity when back button is pressed
     */

    @Override
    public void onBackPressed()
    {
        Intent workOutIntent = new Intent(this, MainActivity.class);
        startActivity(workOutIntent);
        finish();
    }

    /**
     * Use SharedPreferences to save the state of the View. Ensure switch and back status
     * of Notifications are the same.
     */

    private void savePrefs()
    {
        // get SharedPreferences file
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        // get SharedPreferences editor object
        SharedPreferences.Editor sharedPrefsEditor = sharedPreferences.edit();

        // write to file timePicker state and switchBtn state
        sharedPrefsEditor.putBoolean(NOTIFY_BOOL, switchBtn.isChecked());
        sharedPrefsEditor.putInt("hour", timePicker.getHour());
        sharedPrefsEditor.putInt("minute", timePicker.getMinute());

        // save preferences
        sharedPrefsEditor.apply();
    }

    /**
     * Sets the switch button and time picker View objects to their saved state
     */

    private void loadPrefs()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        notifyBool = sharedPreferences.getBoolean(NOTIFY_BOOL, false);
        int prefHour = sharedPreferences.getInt("hour", 5);
        int prefMinute = sharedPreferences.getInt("minute", 16);

        timePicker.setMinute(prefMinute);
        timePicker.setHour(prefHour);
        switchBtn.setChecked(notifyBool);
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.saveBtn:
                handleSaveBtn(v);
                break;

        }
    }

    /**
     * if switch button is set to Off, cancel alarms and save preferences. Else
     * update alarm manager with the current time picker hour and minute selected by User.
     * @param v
     */

    private void handleSaveBtn(View v)
    {
        if(!switchBtn.isChecked())
        {
            Log.d("SettingsActivity: ", "Switch Button off");
            cancelAlarm(this);
            Snackbar.make(v, "Daily Notifications have been turned off", Snackbar.LENGTH_SHORT).show();
            savePrefs();
        }
        else
        {
            int hour = timePicker.getHour();
            int minute = timePicker.getMinute();
            time = getTimeFromPicker();
            initAlarmManager(this, hour, minute);
            Snackbar.make(v,"Workout Reminder set for " + time + " daily.", Snackbar.LENGTH_SHORT).show();
            savePrefs();

        }
    }

    /**
     * Passes context and Intents passed to an AlarmManager objec to cancel any alarms that
     * were previously set.
     * @param con
     */

    private void cancelAlarm(Context con)
    {
        AlarmManager alarmManager = (AlarmManager) con.getSystemService(Context.ALARM_SERVICE);

        Intent alarmIntent = new Intent(con, WorkOutReminderReceiver.class);
        PendingIntent cancelIntent = PendingIntent.getBroadcast(con, 0,
                alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        alarmManager.cancel(cancelIntent);
    }

    /**
     * First cancels any current alarms to ensure multiple alarms are not set.
     *
     * Then sets an alarm using a WorkOurReminderReciever which is a BroadcastReceiver
     * that will call the NotificationService
     * @param con
     * @param hour
     * @param minute
     */

    private void initAlarmManager(Context con, int hour, int minute)
    {
        AlarmManager alarmManager = (AlarmManager) con.getSystemService(Context.ALARM_SERVICE);

        Intent alarmIntent = new Intent(con, WorkOutReminderReceiver.class);

        alarmIntent.putExtra("hour", hour);
        alarmIntent.putExtra("minute", minute);
        alarmIntent.putExtra("time","");

        Log.d("SETTINGSACTIVITY: ", "" +  time);

        PendingIntent cancelIntent = PendingIntent.getBroadcast(con, 0,
                alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        alarmManager.cancel(cancelIntent);


        PendingIntent pendingIntent = PendingIntent.getBroadcast(con ,0,
                alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR, hour);
        cal.set(Calendar.MINUTE, minute);
        /*
        if(cal.before(now))
        {
            cal.add(Calendar.DATE, 1);
        }
        */

        if(pendingIntent != null && cal.getTimeInMillis() != 0)
        {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                    cal.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent);

            Log.d("ALARM", "Alarm manager called reciever");
        }



    }

    /**
     * Simple helper method to retrieve the current Time in a String format from the Picker.
     * @return
     */

    private String getTimeFromPicker()
    {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
        cal.set(Calendar.MINUTE, timePicker.getMinute());

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.US);
        return sdf.format(cal.getTime());
    }
}
