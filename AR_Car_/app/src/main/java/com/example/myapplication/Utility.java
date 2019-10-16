package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Utility
 {

    public static void setTheme(Context context, int theme)
     {
         SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
         prefs.edit().putInt(context.getString(R.string.prefs_theme_key), theme).apply();
     }

    public static int getTheme(Context context)
     {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt(context.getString(R.string.prefs_theme_key), -1);
     }

     public static void setPoints(Context context, int points)
     {
         SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
         prefs.edit().putInt(context.getString(R.string.prefs_points_key), points).apply();
     }

     public static int getPoints(Context context)
     {
         SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
         return prefs.getInt(context.getString(R.string.prefs_points_key), 0);
     }


     public static void setChoosedCar(Context context, int points)
     {
         SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
         prefs.edit().putInt(context.getString(R.string.prefs_choosed_car), points).apply();
     }

     public static int getChoosedCar(Context context)
     {
         SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
         return prefs.getInt(context.getString(R.string.prefs_choosed_car), 1);
     }

     public static void setMusicVolume(Context context, float music_volume)
     {
         SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
         prefs.edit().putFloat(context.getString(R.string.prefs_music_key), music_volume).apply();
     }

     public static float getMusicVolume(Context context)
     {
         SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
         return prefs.getFloat(context.getString(R.string.prefs_music_key), 1.0f);
     }

     public static void setCarsStatus(Context context, String car2_status, String car3_status)
     {
         SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
         prefs.edit().putString(context.getString(R.string.prefs_car2_key),car2_status).putString(context.getString(R.string.prefs_car3_key),car3_status).apply();
     }

     public static String getCarsStatus(Context context)
     {
         SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
         return prefs.getString(context.getString(R.string.prefs_car2_key),"closed") + "//" +prefs.getString(context.getString(R.string.prefs_car3_key),"closed");
     }
 }
