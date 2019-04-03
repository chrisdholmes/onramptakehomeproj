package com.onramp.android.takehome;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.util.Log;

import java.time.Duration;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity(tableName = "work_outs", indices = {@Index(value="name")})
public class WorkOut
{


    //public Duration duration;
    @PrimaryKey(autoGenerate = true)
    private int id;

    public LinkedList<Exercise> exerciseList;
    private String name;
    private Date date;

    public WorkOut(String name, Date date)
    {
        this.name = name;
        this.date = date;
        exerciseList = new LinkedList<>();

    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public List<Exercise> getExerciseList()
    {
        return exerciseList;
    }

    public void setExerciseList(List<Exercise> exerciseList)
    {
        for(Exercise exercise : exerciseList)
        {
            this.exerciseList.add(exercise);
        }

        Log.d("WORKOUTCLASS: " ,"this.exerciseList size: " + this.exerciseList.size());
    }

    @Override
    public String toString()
    {

        return name + " " + date;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }





}
