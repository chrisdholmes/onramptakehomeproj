package com.onramp.android.takehome;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Adapter for RecyclerView in NewWorkOutFragment
 *
 * This has not been implemented. Necessary for editing and deleting exercises in a workout.
 */

public class NewWorkFragListAdapter extends RecyclerView.Adapter
{
    public static class WorkOutViewHolder extends RecyclerView.ViewHolder
    {

        public WorkOutViewHolder(View v)
        {
            super(v);

        }
    }
    private List<Exercise> exerciseList;
    private int position;
    private LayoutInflater inflater;
    private Application application;

    public NewWorkFragListAdapter(Context con, Application application)
    {
        //initiliazed object used to inflate every cardview in the context passed through
        // constructor. In this case the MainActivity's context
        inflater = LayoutInflater.from(con);
        this.application = application;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int pos)
    {
        this.position = pos;

    }

    @Override
    public int getItemCount()
    {
        return exerciseList.size();
    }

    void setExerciseList(List<Exercise> list)
    {
        exerciseList = list;
    }
}
