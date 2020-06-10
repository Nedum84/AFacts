package com.hng.afacts.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat.startActivity



class ClassShareApp(val context: Context) {

    private val appPackageName: String? = context.packageName // getPackageName() from Context or Activity object
    val intent: Intent = Intent()
    private val shareAppMsg: String = "Get \"${context.getString(com.hng.afacts.R.string.app_name)}\" via ${context.getString(
        com.hng.afacts.R.string.app_url)} & Enjoy..."
    init {
        intent.action = Intent.ACTION_SEND
        intent.type = "text/plain"
    }

    fun shareApp() {
        intent.putExtra(Intent.EXTRA_TEXT, shareAppMsg)
        startActivity(context, Intent.createChooser(intent, "Share on: "), Bundle())
    }



}