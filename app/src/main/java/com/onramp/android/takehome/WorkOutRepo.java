package com.onramp.android.takehome;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class WorkOutRepo
{
    private WorkOutDao dao;
    private LiveData<List<WorkOut>> workOutList;
    private LiveData<List<String>> allWorkOutNames;

    WorkOutRepo(Application application)
    {
        WorkOutDatabase db = WorkOutDatabase.getDatabase(application);
        dao = db.workOutDao();
        workOutList = dao.getAllWorkOuts();
        allWorkOutNames = dao.getNames();
    }

    LiveData<List<WorkOut>> getAllWorkOuts()
    {

        return workOutList;
    }

    LiveData<List<String>> getAllWorkOutNames()
    {
        return allWorkOutNames;
    }

    public void insert(WorkOut workOut)
    {
        new insertAsyncTask(dao).execute(workOut);
    }

    public void delete(WorkOut workOut) { new deleteAsyncTask(dao).execute(workOut); }

    private static class insertAsyncTask extends AsyncTask<WorkOut, Void, Void>
    {

        private WorkOutDao workOutDao;

        insertAsyncTask(WorkOutDao dao)
        {
            workOutDao = dao;
        }

        @Override
        protected Void doInBackground(final WorkOut... params)

        {
            workOutDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<WorkOut, Void, Void>
    {
        private WorkOutDao workOutDao;

        deleteAsyncTask(WorkOutDao dao)
        {
            workOutDao = dao;
        }

        @Override
        protected Void doInBackground(final WorkOut... params)
        {
            workOutDao.delete(params[0]);
            return null;
        }
    }

    public void deleteAll()
    {
        Thread removeThread = new Thread( () ->
        {
            dao.deleteAll();
        }

        );

        removeThread.start();

    }

}
