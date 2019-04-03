package com.onramp.android.takehome;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;


/**
 * NewWorkOutFragment takes user input of exercise and reps and sets.
 * Creates a LinkedList of type Exercise. Submits the list of exercises
 * as a workout object to the room sqlite database via ViewModel.
 */
public class NewWorkOutFragment extends DialogFragment
{
    private TextInputEditText repsEditText;
    private TextInputEditText setsEditText;

    private AutoCompleteTextView exerciseName;
    private WorkOutViewModel workOutViewModel;
    private NewWorkOutListener mListener;

    public NewWorkOutFragment(){ }

    public static NewWorkOutFragment newInstance()
    {
        NewWorkOutFragment fragment = new NewWorkOutFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NORMAL, R.style.MY_DIALOG);


    }

    @Override
    public void onStart()
    {
        super.onStart();
        // In order to inflate the fragment view to full screen
        // get instance of the Dialog
        Dialog d = getDialog();
        if(d != null)
        {
            //Get width of this fragment's parent from layout parameters
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            //Get height of this fragment's parent from layout parameters
            int height = ViewGroup.LayoutParams.MATCH_PARENT;

            //Set layout parameters of this DialogFragment with retrieved values
            d.getWindow().setLayout(width, height);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle b)
    {

        // instance of the via fragment view
        View v = inflater.inflate(R.layout.fragment_new_workout, container, false);
        Bundle bun = getArguments();
        String name = bun.getString("name");

        //view model retrieved for submitting workouts to database and following MVVM pattern
        workOutViewModel = ViewModelProviders.of(getActivity()).get(WorkOutViewModel.class);

        repsEditText = v.findViewById(R.id.exerciseReps);
        setsEditText = v.findViewById(R.id.exerciseSets);
        exerciseName = v.findViewById(R.id.exerciseNameEditText);


        Button addExerciseBtn = v.findViewById(R.id.materialButton);
        Button finishWorkoutBtn = v.findViewById(R.id.finishworkout);
        ListView listView =  v.findViewById(R.id.listView);

        addExerciseBtn.setOnClickListener((onClickView) -> {

            //retrieve exercise argument from user input
            Exercise exercise = exerciseFromInput();

            //add exercise to database and retrieve true if exercise added successfully
            boolean exerciseAdded = workOutViewModel.addExercises(exercise);

            workOutViewModel.setWorkOutName(name);

            //if exercise added successfully reset EditText's text values
            if(exerciseAdded)
            {
                repsEditText.setText("");
                setsEditText.setText("");
                exerciseName.setText("");
            }
            else
            {
                //if exercise not submitted successfully display snackbar to reminder user to fill
                // all fields
                Snackbar sb = Snackbar.make(v, "Enter an exercise, reps and sets", Snackbar.LENGTH_LONG);
                sb.show();
            }

            String[] exercisesArray = workOutViewModel.getExerciseList();
            ArrayAdapter<String> adapter = new ArrayAdapter<>(v.getContext(),
                    android.R.layout.simple_list_item_1,
                   exercisesArray);

            listView.setAdapter(adapter);
        });

        finishWorkoutBtn.setOnClickListener((onClickView) ->
        {
            workOutViewModel.workOutComplete();
            dismiss();
        });
        // Inflate the layout for this fragment
        return v;
    }


    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof NewWorkOutListener)
        {
            mListener = (NewWorkOutListener) context;
        } else
        {
            throw new RuntimeException(context.toString()
                    + " must implement NewWorkOutListener");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

    public interface NewWorkOutListener
    {

        void newWorkOutInteraction(WorkOut workOut);
    }

    /**
     * Helper method retrieves data from user input if input fields are filled.
     * @return Exercise object
     */
    private Exercise exerciseFromInput()
    {
        String name = "";
        int reps = 0;
        int sets = 0;

        if (repsEditText.getText().toString().equals("")) {
            reps = 0;
        } else {
            reps = Integer.parseInt(repsEditText.getText().toString());
        }

        if (setsEditText.getText().toString().equals("")) {
            sets = 0;
        } else {
            sets = Integer.parseInt(setsEditText.getText().toString());
        }

        if(exerciseName.getText().toString().equals("")) {
            name = "";
        } else {
            name = exerciseName.getText().toString();
            name = name.toUpperCase();
        }

        return new Exercise(name, reps, sets);

    }
}
