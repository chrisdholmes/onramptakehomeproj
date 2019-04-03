package com.onramp.android.takehome;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.LinkedList;

public class Converters {

    @TypeConverter
    public static LinkedList<Exercise> fromString(String value) {
        Type listType = new TypeToken<LinkedList<Exercise>>(){}.getType();

        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromLinkedList(LinkedList<Exercise> list) {

        Gson gson = new Gson();

        String json = gson.toJson(list);

        return json;
    }

    //TODO change this.

    @TypeConverter
    public Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public Long dateToTimestamp(Date date) {
        if (date == null) {
            return null;
        } else {
            return date.getTime();
        }
    }
}
