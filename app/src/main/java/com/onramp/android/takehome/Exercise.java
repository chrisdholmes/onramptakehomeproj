package com.onramp.android.takehome;

public class Exercise
{

    private String name;
    private int reps;
    private int sets;

    public Exercise(String name, int reps, int sets)
    {
        this.name = name;
        this.reps = reps;
        this.sets = sets;
    }


    public int getReps()
    {
        return reps;
    }

    public void setReps(int reps)
    {
        this.reps = reps;
    }

    public int getSets()
    {
        return sets;
    }

    public void setSets(int sets)
    {
        this.sets = sets;
    }


    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }



    @Override
    public String toString()
    {
        return name + " " + reps + " X " + sets;
    }

    @Override
    public boolean equals(Object o)
    {
        Exercise other = (Exercise) o;

        String oName = other.getName();
        int oReps = other.getReps();
        int oSets = other.getSets();

        if(( this.name.equals(oName)
                && this.reps == oReps
                && this.sets == oSets)){
            return true;
        } else {
            return false;
        }

    }


}
