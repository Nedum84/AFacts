package com.hng.afacts.utils

import android.content.Context
import com.google.gson.Gson


class  ClassSharedPreferences(val context: Context?){

    private val PREFERENCE_NAME = "a_facts_preference"
    private val PREFERENCE_CURRENT_DATA_BINDER = "current_data_binder"
    private val PREFERENCE_CURRENT_LIST_BINDER = "current_list_binder"
    private val PREFERENCE_OPENING_FOR_THE_FIRST_TIME = "opening_for_the_first_time"


    private val preference = context?.getSharedPreferences(PREFERENCE_NAME,Context.MODE_PRIVATE)!!

    //opening app for the first time
    fun checkOpeningForFirstly(data:Boolean){
        val editor = preference.edit()
        editor.putBoolean(PREFERENCE_OPENING_FOR_THE_FIRST_TIME,data)
        editor.apply()
    }
    fun getOpeningForFirstly():Boolean{
        return  preference.getBoolean(PREFERENCE_OPENING_FOR_THE_FIRST_TIME, true)
    }

    //current data list binder
    fun setCurrentDataBinder(data:String){
        val editor = preference.edit()
        editor.putString(PREFERENCE_CURRENT_DATA_BINDER,data)
        editor.apply()
    }
    fun getCurrentDataBinder():String?{
        return  preference.getString(PREFERENCE_CURRENT_DATA_BINDER, Gson().toJson(mutableListOf<CategoryBinder>()))
    }


    //current data list binder
    fun setCurrentListBinder(data:String){
        val editor = preference.edit()
        editor.putString(PREFERENCE_CURRENT_LIST_BINDER,data)
        editor.apply()
    }
    fun getCurrentListBinder():String?{
        return  preference.getString(PREFERENCE_CURRENT_LIST_BINDER, Gson().toJson(mutableListOf<CategoryBinder>()))
    }


}