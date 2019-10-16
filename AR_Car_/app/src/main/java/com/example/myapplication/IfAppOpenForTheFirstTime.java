package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

public class IfAppOpenForTheFirstTime
 {

    private static final String MY_PREFERENCES = "my_preferences";

    public static boolean isFirst(Context context)
    {
        final SharedPreferences reader = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        final boolean first = reader.getBoolean("is_first", true);
        if(first )
        {
            final SharedPreferences.Editor editor = reader.edit();
            editor.clear();
            editor.putBoolean("is_first", false);
            editor.commit();
        }
        return first;
    }
 }