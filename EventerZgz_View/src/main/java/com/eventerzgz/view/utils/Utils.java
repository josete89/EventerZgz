package com.eventerzgz.view.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.TypedValue;

import com.eventerzgz.view.application.EventerZgzApplication;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by JavierArroyo on 21/3/15.
 */
public class Utils {

    public static int dpToPx(float dp, Resources resources){
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
        return (int) px;
    }

    public static void saveCategoriesSelectedInPreferences(ArrayList<String> arrayIdsCategories, Context context){
        SharedPreferences prefs = context.getSharedPreferences(EventerZgzApplication.APP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        Set<String> set = new HashSet<String>();
        set.addAll(arrayIdsCategories);

        editor.putStringSet("categories", set);
        editor.commit();

    }

    public static void saveCategoriesPushSelectedInPreferences(ArrayList<String> arrayIdsCategories, Context context){
        SharedPreferences prefs = context.getSharedPreferences(EventerZgzApplication.APP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        Set<String> set = new HashSet<String>();
        set.addAll(arrayIdsCategories);

        editor.putStringSet("categoriesPush", set);
        editor.commit();

    }
}
