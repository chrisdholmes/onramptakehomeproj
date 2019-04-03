package com.onramp.android.takehome;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

/**
 * Database uses singleton pattern to return one instance of itself.
 * AsyncTask is used to run insertions and queries on a background thread for
 * optimal performance.
 *
 */

@Database(entities = {WorkOut.class}, version = 4, exportSchema = false)
// Converters used for "Serializing" objects in to the database
@TypeConverters({Converters.class})
public abstract class WorkOutDatabase extends RoomDatabase
{
    public abstract WorkOutDao workOutDao();

    private static volatile WorkOutDatabase INSTANCE;

    static WorkOutDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (WorkOutDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WorkOutDatabase.class, "graffitimapsdb")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<WorkOut, Void, Void>
    {

        private final WorkOutDao mDao;

        PopulateDbAsync(WorkOutDatabase db) {
            mDao = db.workOutDao();
        }

        @Override
        protected Void doInBackground(final WorkOut... params) {

            WorkOut workOut = params[0];

            mDao.insert(workOut);
            return null;
        }
    }


}
