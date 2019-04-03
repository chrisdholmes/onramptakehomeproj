package com.onramp.android.takehome;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LauncherActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        new LauncherAsyncTask().execute();

    }

    public class LauncherAsyncTask extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected Void doInBackground(Void... voids)
        {
            try
            {
                Thread.sleep(3000);
            }catch(InterruptedException e){System.exit(0);}

            Intent mainActivityIntent = new Intent(LauncherActivity.this, MainActivity.class);

            startActivity(mainActivityIntent);


            return null;
        }
    }

}
