package com.hng.afacts.ui.fragment

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.google.gson.Gson
import com.hng.afacts.R
import com.hng.afacts.utils.*
import kotlinx.android.synthetic.main.fragment_item_details.*


class FragmentItemDetails : DialogFragment() {
    lateinit var thisContext: Activity
    lateinit var listDataBinder: CategoryBinder



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_item_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        thisContext = activity!!


        val data = Gson().fromJson(ClassSharedPreferences(thisContext).getCurrentDataBinder(), Array<CategoryBinder>::class.java).asList()

        listDataBinder = data[0]
        user_title.text = listDataBinder.user_title
        user_desc.text = listDataBinder.user_desc
        LoadImg(thisContext, detail_id).execute(listDataBinder.user_profile)

        save_fact.setOnClickListener {
            val savedList = (Gson().fromJson(ClassSharedPreferences(thisContext).getCurrentListBinder(), Array<CategoryBinder>::class.java).asList()).toMutableList()
            if (listDataBinder !in savedList){
                savedList.add(listDataBinder)
                savedList.distinct()

                ClassSharedPreferences(context).setCurrentListBinder(Gson().toJson(savedList))
                ClassAlertDialog(thisContext).toast("Fact Saved...")
            }else{
                ClassAlertDialog(thisContext).toast("Already Saved...")
            }

        }



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity!!.window.statusBarColor = ContextCompat.getColor(activity!!, R.color.black_overlay)
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
            setStyle(STYLE_NO_TITLE, android.R.style.Theme_Material_Light_NoActionBar_Fullscreen)
        } else {
            setStyle(STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_NoActionBar)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.attributes.windowAnimations = R.style.Animation_WindowSlideUpDown
//        isCancelable = false
        return dialog
    }
}