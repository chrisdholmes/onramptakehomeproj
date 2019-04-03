package com.onramp.android.takehome;

/**
 * Author: Christopher Holmes
 * Objective: Fitness application that is designed to store body weight focused workouts and
 * also motivate the user to continue to workout.
 */

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

// implement View.OnClickListener to use switch case statement in OnClick

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        NewWorkOutFragment.NewWorkOutListener, NameWorkOutDialogFragment.NameWorkOutListener,
        NameWorkOutDialogFragment.OnDismissListener
{

    //Global variable references.
    private RecyclerView recyclerView;
    private FloatingActionButton newWorkOutFab;
    private WorkOutListAdapter workOutListAdapter;
    private WorkOutViewModel workOutViewModel;
    private CoordinatorLayout coordinatorLayout;
    private LinearLayoutManager layoutManager;

    private final String TAG = "Main Activity: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        newWorkOutFab = findViewById(R.id.newWorkOutFab);
        newWorkOutFab.setOnClickListener(this);

        coordinatorLayout = findViewById(R.id.coordinator);


        //RecyclerView initialization
        //reference to recyclerview
        recyclerView = findViewById(R.id.previousWorkoutsRecyclerView);
        recyclerView.hasFixedSize();
        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //WorkOutListAdapter initilization and set to recyclerview so cardviews properly appear
        workOutListAdapter = new WorkOutListAdapter(this, getApplication());
        recyclerView.setAdapter(workOutListAdapter);

        //Use ViewModelProviders to get reference to viewmodel and allow for observables
        workOutViewModel = ViewModelProviders.of(this).get(WorkOutViewModel.class);


        //Observing LiveData and populating RecyclerView when new data added to database.
        workOutViewModel.getAllWorkOuts().observe(this, new Observer<List<WorkOut>>() {
            @Override
            public void onChanged(@Nullable final List<WorkOut> workOuts) {
                // Update the copy of the list of words in the adapter.
                Log.d(TAG, "onChanged: " + workOuts.size());
                workOutListAdapter.setWorkOuts(workOuts);
            }
        });

        //intent created to start Settings Activity when menu options clicked


    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            //Display fragment to start a new workout upon click

            case R.id.newWorkOutFab:
                Log.d("showname:", "show name called");
                showNameWorkOutFragment();
                break;
        }
    }

    public void showNameWorkOutFragment()
    {

        FragmentManager fm = getSupportFragmentManager();
        NameWorkOutDialogFragment nameWorkOutDialogFragment = NameWorkOutDialogFragment.newInstance();
        nameWorkOutDialogFragment.setDissmissListener(this);


        nameWorkOutDialogFragment.show(fm, "fragment_new_workout");
    }

    /**
     * Display fragment for adding new workout.
     *
     */
    public void showAddExercisesFragment(String name)
    {
        //Get an instance of fragment manager.
        FragmentManager fm = getSupportFragmentManager();
        //Create instance of fragment
        NewWorkOutFragment newWorkOutFragment = NewWorkOutFragment.newInstance();
        // Pass fragment manager and tag to fragment to display to screen.
        Bundle bundleFrag = new Bundle();

        bundleFrag.putString("name",name);
        newWorkOutFragment.setArguments(bundleFrag);
        newWorkOutFragment.show(fm, "fragment_add_exercises");
    }

    /**
     * Callback inflates the views in the R.menu.menu_main xml file
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Callback listens for click in the menu and performs actions based upon which menu item
     * was clicked.
     * @param item
     * @return
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId())
        {
            // Settings menu option clicked, start Settings Activity
            case R.id.action_settings:
                Intent  settingsIntent = new Intent(this, SettingsActivity.class);
                Snackbar.make(coordinatorLayout, "FUN FAITH DISCIPLINE", Snackbar.LENGTH_SHORT).show();
                startActivity(settingsIntent);
                finish();
                 break;
             // Delete all clears the database of all works, important for testing
            case R.id.delete_all:
                workOutViewModel.removeAll();
                Snackbar.make(coordinatorLayout, "YOU CLEARED THE SCREEN", Snackbar.LENGTH_SHORT).show();
                break;

        }


        return super.onOptionsItemSelected(item);
    }

    /**
     * Listener for newWorkOut Fragment
     * @param workOut
     */
    @Override
    public void newWorkOutInteraction(WorkOut workOut)
    {

    }

    @Override
    public void nameWorkOutInteraction(String workOutName)
    {

    }

    @Override
    public void onDismiss(NameWorkOutDialogFragment myDialogFragment)
    {
        Bundle b = myDialogFragment.getArguments();
        String name = b.getString("name");
        if(name != null)
        {
            showAddExercisesFragment(name);
        }

    }
}
