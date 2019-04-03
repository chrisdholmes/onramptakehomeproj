package com.onramp.android.takehome;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;


public class WorkOutListAdapter extends RecyclerView.Adapter<WorkOutListAdapter.WorkOutViewHolder>
{
    private List<WorkOut> workOutList;
    private final LayoutInflater inflater;

    /**
     * ViewHolder grabs reference to each CardView that is dispalyed in the
     * RecyclerView
     */
    public static class WorkOutViewHolder extends RecyclerView.ViewHolder
    {
        public TextView workOutNameText;
        public TextView workOutDateText;
        public TextView slotOne;
        public TextView menuOptionsView;

        public WorkOutViewHolder(View v)
        {
            super(v);
            //Reference to title textView within CardView
            workOutNameText = v.findViewById(R.id.workOutNameText);
            workOutDateText = v.findViewById(R.id.workOutDateText);


            //Reference location in CardView where exercises are displayed
             slotOne = v.findViewById(R.id.slotOne);

            menuOptionsView = v.findViewById(R.id.menuOptionsTextView);


        }
    }


    Context con;
    public int position;
    private WorkOutViewModel workOutViewModel;
    public WorkOutRepo workOutRepo;
    private Application application;

    public WorkOutListAdapter(Context con, Application application)
    {
        //initiliazed object used to inflate every cardview in the context passed through
        // constructor. In this case the MainActivity's context
        inflater = LayoutInflater.from(con);
        this.application = application;

    }

    /**
     * This is where the CardView is inflated and allows to grab references to the CardView's children
     * in the onBindView method for updating the UI.
     * @param viewGroup
     * @param i
     * @return
     */

    @NonNull
    @Override
    public WorkOutListAdapter.WorkOutViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        //inflates the CardView in to it's parent

        con = viewGroup.getContext();

        CardView cv = (CardView) (inflater
                .inflate(R.layout.workout_recycler_cardview,viewGroup, false));

        WorkOutViewHolder holder = new WorkOutViewHolder(cv);

        workOutRepo = new WorkOutRepo(application);


        return holder;
    }

    /**
     * All the interaction in the RecyclerView displayed in the MainActivity occurs here.
     * Every workout is represented in a cardview to the user. The data from the list of
     * workouts collected from the database are looped through via this callback method.
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(WorkOutViewHolder holder, int position) {

        //gets a reference of the workOut object from workOutList
        this.position = position;
        WorkOut workout = workOutList.get(this.position);

        //format date to appear in title of CardView
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy hh:mm a");
        String workOutDate = sdf.format(workout.getDate());
        String workOutName = workout.getName();

        //retrieves exerceList from workOut object
        List<Exercise> exercises = workout.getExerciseList();

        holder.menuOptionsView.setOnClickListener((v) ->
        {

                holder.menuOptionsView.setTag(position);
                showPopUpMenu(holder.menuOptionsView);
        });

        StringBuilder displayExercises = new StringBuilder("");

        for(Exercise exercise : exercises)
        {
           displayExercises.append(exercise + " \n");
        }

        exercises.clear();

        holder.slotOne.setMovementMethod(new ScrollingMovementMethod());

        holder.workOutDateText.setText(workOutDate);
        holder.workOutNameText.setText(workOutName);
        holder.slotOne.setText(displayExercises.toString().toUpperCase());

    }

    private void showPopUpMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(con, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.card_tool_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new WorkOutMenuItemClickListener(view));
        popup.show();
    }

    /**
     * When called will update the RecyclerView by updating the list of items it is being
     * told to display
     * @param list
     */
    void setWorkOuts(List<WorkOut> list){

        workOutList = list;
        Collections.reverse(workOutList);
        notifyDataSetChanged();
    }

    /**
     * CallBack allows the RecyclerView to know how many items are in the List
     * so it can display them properly.
     * @return
     */

    @Override
    public int getItemCount()
    {
        if(workOutList == null)
        {
            return 0;
        }
        else

        {
            return workOutList.size();
        }
    }

    class WorkOutMenuItemClickListener implements PopupMenu.OnMenuItemClickListener
    {
        View v;
        WorkOutMenuItemClickListener(View v){
            this.v = v;
        }
        @Override
        public boolean onMenuItemClick(MenuItem menuItem)
        {
            switch(menuItem.getItemId())
            {
                case R.id.deleteWorkout:
                    int pos = (Integer) v.getTag();
                    WorkOut workOut = workOutList.get(pos);
                    workOutRepo.delete(workOut);

                    notifyDataSetChanged();
                    break;
            }
            return false;
        }
    }



}
