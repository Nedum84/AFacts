package com.hng.afacts.ui.fragment

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.hng.afacts.R
import com.hng.afacts.adapter.AdapterCategory
import com.hng.afacts.utils.CategoryBinder
import com.hng.afacts.utils.ClassUtilities
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_category.*


class FragmentCategory : Fragment() {
    private lateinit var thisContext: Activity
    private var FRAG_POS = 0
    private var CAT_ID = ""

    lateinit var linearLayoutManager: GridLayoutManager
    private var dataList: MutableList<CategoryBinder>? = mutableListOf()

    lateinit var ADAPTER : AdapterCategory



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        thisContext = activity!!
        linearLayoutManager = GridLayoutManager(thisContext,  ClassUtilities().calculateNoOfColumns(thisContext,120f))

        ADAPTER = AdapterCategory(dataList!!, thisContext)
        cat_recycler?.layoutManager = linearLayoutManager
        cat_recycler?.itemAnimator = DefaultItemAnimator()
        cat_recycler?.adapter = ADAPTER

        //checking for already sent argument
        if(arguments != null){
            val bet_day = arguments?.getCharSequence("cat_name").toString()
            CAT_ID = arguments!!.getString("cat_id")!!
            FRAG_POS = arguments!!.getInt("frag_position")


            loadCatDetails()
        }
    }

    private fun loadCatDetails() {
        val allList = ClassUtilities().getAllItems()

        var newList = mutableListOf<CategoryBinder>()
        for (c in allList){
            if (CAT_ID == c.cat_id)
                newList.add(c)
        }
        newList = (newList.distinct()).toMutableList()
        ADAPTER.addItems(newList)
    }







    //Object to bind the datas
    companion object {

        fun newInstance(name: CharSequence, value: String, frag_position: Int): FragmentCategory {

            val args = Bundle().apply {
                putCharSequence("cat_name", name)
                putString("cat_id", value)
                putInt("frag_position", frag_position)
            }
            val fragment = FragmentCategory()
            fragment.arguments = args
            return fragment
        }
    }



}