package com.onramp.android.takehome;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;


public class WorkOutViewModel extends AndroidViewModel
{
    private static final String TAG ="VIEWMODEL: ";

    private WorkOutRepo workOutRepo;

    private LiveData<List<WorkOut>> allWorkOuts;

    private List<Exercise> exercises;

    private LiveData<List<String>> allNames;

    private WorkOut workOutX;

    public WorkOutViewModel(@NonNull Application application)
    {
        super(application);

        workOutRepo = new WorkOutRepo(application);
        allWorkOuts = workOutRepo.getAllWorkOuts();
        allNames = workOutRepo.getAllWorkOutNames();
        exercises = new LinkedList<>();

    }

    LiveData<List<String>> getAllNames(){ return allNames; }

    LiveData<List<WorkOut>> getAllWorkOuts()
    {
        return allWorkOuts;
    }

    public void insert(WorkOut workOut)
    {
        workOutRepo.insert(workOut);
    }

    public void remove(WorkOut workOut) { workOutRepo.delete(workOut); }

    public void removeAll()
    {
        workOutRepo.deleteAll();
    }

    public void setWorkOutName(String workOutName)
    {
        workOutX = new WorkOut("", new Date());
        workOutX.setName(workOutName);
    }


    public boolean addExercises(Exercise ex) {
        Exercise checkEmpty = new Exercise("", 0, 0);

        if(ex.equals(checkEmpty) || ex.getReps() == 0 || ex.getSets() == 0)
        {
            return false;
        } else
        {
            exercises.add(ex);
        }
        return true;
    }

    public String[] getExerciseList()
    {
        String[] exerciseArray = new String[exercises.size()];

        for(int i = 0; i < exercises.size(); i++)
        {
            exerciseArray[i] = exercises.get(i).toString();
        }


        return exerciseArray;
    }


    public boolean workOutComplete()
    {
        if (exercises.size() > 0)
        {

            workOutX.setDate(new Date());
            workOutX.setExerciseList(exercises);

            insert(workOutX);
            exercises.clear();

        }
        else
        {
            exercises.clear();
            return false;
        }
        return true;
    }




}
