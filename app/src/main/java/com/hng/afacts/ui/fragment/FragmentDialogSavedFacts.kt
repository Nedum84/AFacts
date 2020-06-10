package com.hng.afacts.ui.fragment

import android.app.Activity
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.hng.afacts.R
import com.hng.afacts.adapter.AdapterCategory
import com.hng.afacts.adapter.AdapterSavedFacts
import com.hng.afacts.utils.CategoryBinder
import com.hng.afacts.utils.ClassSharedPreferences
import com.hng.afacts.utils.ClassUtilities
import com.hng.afacts.utils.LoadImg
import kotlinx.android.synthetic.main.fragment_dialog_saved_facts.*
import kotlinx.android.synthetic.main.fragment_item_details.*


class FragmentDialogSavedFacts : DialogFragment() {
    private lateinit var thisContext: Activity
    private var FRAG_POS = 0
    private var CAT_ID = ""

    val linearLayoutManager = LinearLayoutManager(activity)
    private var dataList: MutableList<CategoryBinder>? = mutableListOf()

    lateinit var ADAPTER : AdapterSavedFacts

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dialog_saved_facts, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        thisContext = activity!!

        ADAPTER = AdapterSavedFacts(dataList!!, thisContext)
        saved_recycler?.layoutManager = linearLayoutManager
        saved_recycler?.itemAnimator = DefaultItemAnimator()
        saved_recycler?.adapter = ADAPTER


        loadCatDetails()
    }
    private fun loadCatDetails() {
        val data = Gson().fromJson(ClassSharedPreferences(thisContext).getCurrentListBinder(), Array<CategoryBinder>::class.java).asList()

        if (data.isEmpty()){
            no_saved_item_show.visibility = View.VISIBLE
            saved_recycler.visibility = View.GONE
        }else{
            no_saved_item_show.visibility = View.GONE
            saved_recycler.visibility = View.VISIBLE
            ADAPTER.addItems((data.distinct()).toMutableList())
        }
    }





    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity!!.window.statusBarColor = ContextCompat.getColor(activity!!, R.color.colorPrimaryDark)
        }
    }


    override fun onResume() {
        super.onResume()
        ClassUtilities().lockScreen(context)//Screen rotation...
    }

    override fun onPause() {
        super.onPause()
        ClassUtilities().unlockScreen(context)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setStyle(STYLE_NORMAL, android.R.style.Theme_Material_Light_NoActionBar_Fullscreen)
        } else {
            setStyle(STYLE_NORMAL, android.R.style.Theme_DeviceDefault_Light_NoActionBar)
        }
    }
}