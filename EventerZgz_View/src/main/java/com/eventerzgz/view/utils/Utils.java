package com.eventerzgz.view.utils;

import android.content.res.Resources;
import android.util.TypedValue;

/**
 * Created by JavierArroyo on 21/3/15.
 */
public class Utils {

    public static int dpToPx(float dp, Resources resources){
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
        return (int) px;
    }
}
