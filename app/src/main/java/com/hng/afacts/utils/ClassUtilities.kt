package com.hng.afacts.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import com.hng.afacts.UrlHolder
import org.json.JSONException
import org.json.JSONObject


class ClassUtilities() {

    fun calculateNoOfColumns(context:Context, columnWidthDp:Float):Int { // For example columnWidthdp=180
        val displayMetrics = context.resources.displayMetrics
        val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
        val noOfColumns = (screenWidthDp / columnWidthDp + 0.5).toInt() // +0.5 for correct rounding to int.
        return noOfColumns
    }

    @SuppressLint("SourceLockedOrientationActivity")
    fun lockScreen(context: Context?){
        //lock screen
        val orientation = context!!.resources.configuration.orientation//activity!!.requestedOrientation(to get 10 more values)
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            // code for portrait mode
            try {
                (context as Activity).requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            } catch (e: Exception) {}
        } else {
            // code for landscape mode
            try {
                (context as Activity).requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            } catch (e: Exception) {}
        }
        //lock screen
//        OR
//        activity!!.requestedOrientation = orientation
        //OR
//        activity!!.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LOCKED
    }
    fun unlockScreen(context: Context?){
        try {
            (context as Activity).requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        } catch (e: Exception) {}
    }



    fun getAllItems():MutableList<CategoryBinder>{
        val return_ = mutableListOf<CategoryBinder>()
        try {
            val obj = JSONObject(UrlHolder.allData)
            if (!obj.getBoolean("error")) {
                val jsonResponse = obj.getJSONArray("itemsz_arraysz")

                if ((jsonResponse.length()!=0)){

                    for (i in 0 until jsonResponse.length()) {
                        val jsonObj = jsonResponse.getJSONObject(i)
                        val subject = CategoryBinder(
                            jsonObj.getString("cat_id"),
                            jsonObj.getString("cat_name"),
                            jsonObj.getString("user_title"),
                            jsonObj.getString("user_name"),
                            jsonObj.getString("user_desc"),
                            jsonObj.getString("user_profile")
                        )
                        return_.add(subject)
                    }
                }
            }

        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return return_.distinct().toMutableList()
    }
}