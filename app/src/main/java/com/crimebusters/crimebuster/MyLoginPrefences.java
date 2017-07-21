package com.crimebusters.crimebuster;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by awais on 6/9/2017.
 */


    public class MyLoginPrefences {

        private static final String MY_PREFERENCES = "my_preferences";

        public static boolean isFirst(Context context){
            final SharedPreferences reader = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);

            final boolean first = reader.getBoolean("is_first", true);

//            if(first){
//                final SharedPreferences.Editor editor = reader.edit();
//                editor.putBoolean("is_first", false);
//                editor.commit();
//            }
            return first;
        }

    }
