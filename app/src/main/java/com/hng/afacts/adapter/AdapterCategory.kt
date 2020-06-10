package com.hng.afacts.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.hng.afacts.R
import com.hng.afacts.ui.fragment.FragmentItemDetails
import com.hng.afacts.utils.CategoryBinder
import com.hng.afacts.utils.ClassSharedPreferences
import com.hng.afacts.utils.LoadImg
import kotlinx.android.synthetic.main.adapter_item.view.*




class AdapterCategory(val list:MutableList<CategoryBinder>, val context: Context) : RecyclerView.Adapter<AdapterCategory.ViewHolder>() {
    val imgBgArrays = mutableListOf(R.drawable.a_item_bg1, R.drawable.a_item_bg2, R.drawable.a_item_bg3)


    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_item, parent, false)
        return ViewHolder(view)
    }

    fun addItems(items: MutableList<CategoryBinder>) {
        val lastPos = list.size - 1
        list.addAll(items)
        notifyItemRangeInserted(lastPos, items.size)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listDetail = list[position]

//        holder.itemImgBg.setImageResource(imgBgArrays.shuffled().last())
        holder.itmemUserName.text = listDetail.user_name

        LoadImg(context, holder.itemImgBg).execute(listDetail.user_profile)

        holder.itemWrapper.setOnClickListener {
            ClassSharedPreferences(context).setCurrentDataBinder(Gson().toJson(mutableListOf(listDetail)))



            val dialogFragmentItemDetails = FragmentItemDetails()
            val ft = (context as FragmentActivity).supportFragmentManager.beginTransaction()
            val prev = context.supportFragmentManager.findFragmentByTag(FragmentItemDetails::class.java.name)
            if (prev != null) {
                ft.remove(prev)
            }
            ft.addToBackStack(null)
            dialogFragmentItemDetails.show(ft, FragmentItemDetails::class.java.name)
        }
    }

    open inner class ViewHolder(v: View): RecyclerView.ViewHolder(v){
        val itemWrapper = v.itemWrapper!!
        val itemImgBg = v.itemImgBg!!
        val itmemUserName = v.itmemUserName!!
    }


}