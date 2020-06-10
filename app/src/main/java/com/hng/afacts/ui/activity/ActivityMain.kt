package com.hng.afacts.ui.activity

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.shape.CornerFamily
import com.hng.afacts.R
import com.hng.afacts.ui.fragment.FragmentCategory
import com.hng.afacts.ui.fragment.FragmentWelcome1
import com.hng.afacts.utils.ClassShareApp
import com.hng.afacts.utils.ClassSharedPreferences
import com.hng.afacts.utils.ClassUtilities
import com.hng.afacts.utils.LoadImg
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class ActivityMain : AppCompatActivity() {
    lateinit var thisContext:Activity
    lateinit var viewpagerAdapter: ViewPaggerToggler
    var TAB_POSITION = "POSITION"
    var currentTabPosition = 0
    var tabTitles = mutableListOf<String?>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        thisContext = this


//        img_bg.clipToOutline = true
//        val radius = resources.getDimension(R.dimen.default_corner_radius)
//        img_bg.setShapeAppearanceModel(
//            imageView.getShapeAppearanceModel()
//                .toBuilder()
//                .setAllCorners(CornerFamily.ROUNDED, radius)
//                .build()
//        )

        if(ClassSharedPreferences(thisContext).getOpeningForFirstly()) {//ClassSharedPreferences(thisContext).getOpeningForFirstly()
            ClassSharedPreferences(thisContext).checkOpeningForFirstly(false)
            loadWelcomeScreen()
            addFragmentsAndViewpager()
        }else{
            addFragmentsAndViewpager()
        }

    }


    private fun loadWelcomeScreen() {
        val dialogFragmentWlcm1 = FragmentWelcome1()
        val ft = supportFragmentManager.beginTransaction()
        val prev = supportFragmentManager.findFragmentByTag(FragmentWelcome1::class.java.name)
        if (prev != null) {
            ft.remove(prev)
        }
        ft.addToBackStack(null)
        dialogFragmentWlcm1.show(ft, FragmentWelcome1::class.java.name)
    }


    private fun addFragmentsAndViewpager(){
        var allList = ClassUtilities().getAllItems()
        val item_show= (allList.shuffled().last())
        user_name.text = item_show.user_title
        LoadImg(thisContext, img_bg).execute(item_show.user_profile)

        allList = (allList.distinctBy { it.cat_id }).toMutableList()
        tabTitles = (allList.map { it.cat_name }).toMutableList()

        viewpagerAdapter = ViewPaggerToggler(supportFragmentManager)
        for (c in allList){
            viewpagerAdapter.addFragment(FragmentCategory(), c.cat_name!!, c.cat_id!!)
        }


        // Finally, data bind the view pager widget with pager viewpagerAdapter
        view_pager.adapter = viewpagerAdapter
        view_pager.offscreenPageLimit = viewpagerAdapter.count-1
        tabLayout.setupWithViewPager(view_pager)
        view_pager.currentItem = currentTabPosition


        view_pager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageSelected(position: Int) {
                currentTabPosition = position
                updateTabView()
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }
        })

        //set custom tablayout
        setupTabLayout()
    }
    private fun setupTabLayout(){
        // Iterate over all tabs and set the custom view
        for (i in 0 until tabLayout.tabCount) {
            val tab = tabLayout.getTabAt(i)
            tab!!.customView = viewpagerAdapter.getTabViewAt(i)
        }

        updateTabView()
    }

    fun updateTabView() {
        for (i in 0 until tabLayout.tabCount) {
            val custom_tablayout = LayoutInflater.from(thisContext).inflate(R.layout.custom_tab_selected, null)
            val tab_title = custom_tablayout.findViewById(R.id.cat_title) as TextView
            val tab_bg = custom_tablayout.findViewById(R.id.cat_bg) as LinearLayout
            tab_title.text = tabTitles[i]


            val tab = tabLayout.getTabAt(i)
            if (tab!!.isSelected){
                tab_title.setTextColor(resources.getColor(R.color.colorWhite))
                tab_bg.setBackgroundResource(R.drawable.design_border_radius_selected)
            }else{
                tab_title.setTextColor(ContextCompat.getColor(thisContext, R.color.colorAsh))
                tab_bg.setBackgroundResource(R.drawable.design_border_radius_un_selected)
            }

            tab.customView = null
            tab.customView = custom_tablayout
        }
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(TAB_POSITION, currentTabPosition)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        currentTabPosition = savedInstanceState.getInt(TAB_POSITION)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_activity_home, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_menu_share_app -> {//for news list
                ClassShareApp(this).shareApp()
            }
        }

        return super.onOptionsItemSelected(item)
    }
    override fun onBackPressed() {
        if (view_pager.currentItem == 0) {
            super.onBackPressed()

        } else {
            view_pager.currentItem = 0
        }
    }




    //    class ViewPaggerToggler(manager: FragmentManager,val ctx:Context) : FragmentPagerAdapter(manager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    inner class ViewPaggerToggler(manager: FragmentManager) : FragmentStatePagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        private val fragments: MutableList<Fragment> = ArrayList()
        private val titles: MutableList<String> = ArrayList()
        private val cat_name: MutableList<String> = ArrayList()


        fun getTabViewAt(position: Int): View? {
            // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
            val v = LayoutInflater.from(thisContext).inflate(R.layout.custom_tab_selected, null)
            val tv = v.findViewById(R.id.cat_title) as TextView
            tv.text = tabTitles[position]
            return v
        }

        override fun getItem(position: Int): Fragment {
            return FragmentCategory.newInstance(titles[position], cat_name[position], position)
        }
        override fun getCount(): Int {
            return fragments.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titles[position]
        }


        fun addFragment(fragment: Fragment, title: String, date_val: String) {
            fragments.add(fragment)
            titles.add(title)
            cat_name.add(date_val)
        }
    }


}