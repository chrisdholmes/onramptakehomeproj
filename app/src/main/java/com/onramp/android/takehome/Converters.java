package com.onramp.android.takehome;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.LinkedList;

/**
 * Converters class is for adding a LinkedList of Exercises to the SQLite database.
 *
 */

public class Converters {
    /**
     * Converts String to LinkedList for WorkOut object retrieval
     * @param value
     * @return
     */

    @TypeConverter
    public static LinkedList<Exercise> fromString(String value) {
        Type listType = new TypeToken<LinkedList<Exercise>>(){}.getType();

        return new Gson().fromJson(value, listType);
    }

    /**
     * Converts LinkedList to String for WorkOut object insertion into database
     * @param list
     * @return
     */
    @TypeConverter
    public static String fromLinkedList(LinkedList<Exercise> list) {

        Gson gson = new Gson();

        String json = gson.toJson(list);

        return json;
    }

    /**
     * Converts long object to date, to retrieve date objects from SQLite DB
     * @param timeInMillis
     * @return
     */


    @TypeConverter
    public Date fromTimestamp(Long timeInMillis) {
        if(timeInMillis == null)
        {
            return null;
        } else
        {
            return new Date(timeInMillis);
        }
    }
    /**
     * Converts date objects to long to insert in to SQLite DB.
     */
    @TypeConverter
    public Long dateToTimestamp(Date date) {
        if (date == null) {
            return null;
        } else {
            return date.getTime();
        }
    }
}
