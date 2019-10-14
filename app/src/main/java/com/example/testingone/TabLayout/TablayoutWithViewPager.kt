package com.example.testingone.TabLayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.testingone.R
import com.example.testingone.fragments.Fragment_One
import com.example.testingone.fragments.Second_Fragment
import com.example.testingone.fragments.Third_Fragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_tablayout_with_view_pager.*

class TablayoutWithViewPager : AppCompatActivity() {

    lateinit var tabLyt:TabLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_tablayout_with_view_pager)

        tabLyt=findViewById(R.id.tabLayout)
        tabLyt.addTab(tabLayout!!.newTab().setText("Home"))
        tabLyt.addTab(tabLayout!!.newTab().setText("He"))
        tabLyt.addTab(tabLayout!!.newTab().setText("Ho"))


        val adapter = PageAdapter(supportFragmentManager,tabLayout.tabCount,tabLyt)
        viewPager!!.adapter = adapter
        //tabLyt.setupWithViewPager(viewPager)//this will shows the current position with indicator

        tabLyt.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                viewPager.currentItem=p0!!.position
            }

        })

    }

    inner class PageAdapter(var fm: FragmentManager,var tabCount:Int,var tbs:TabLayout) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            when (position) {
                0 -> {

                    tbs.getTabAt(position)!!.setIcon(R.drawable.user)
                    //  val homeFragment: HomeFragment = HomeFragment()
                    return Fragment_One()
                }
                1 -> {

                    tbs.getTabAt(position)!!.setIcon(R.drawable.user)
                    return Second_Fragment()
                }
                2 -> {
                    tbs.getTabAt(position)!!.setIcon(R.drawable.user)
                    // val movieFragment = MovieFragment()
                    return Third_Fragment()
                }
                else -> return Fragment_One()
            }
        }

        override fun getCount(): Int {
            return tabCount
        }
    }
}

