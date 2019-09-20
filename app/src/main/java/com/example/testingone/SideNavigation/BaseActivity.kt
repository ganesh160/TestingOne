package com.example.testingone.SideNavigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.testingone.R
import com.example.testingone.Util.Utils
import com.example.testingone.fragments.Fragment_One
import com.example.testingone.fragments.Second_Fragment
import com.example.testingone.fragments.Third_Fragment
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.internal.NavigationMenuView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_home.*

open class BaseActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    

    //open lateinit var drawer_layout : DrawerLayout
    open  var navHeader: View? = null

    lateinit var fragment: Fragment
    lateinit var fragmentManager: FragmentManager
    lateinit var transaction: FragmentTransaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {

        drawer_layout.closeDrawer(GravityCompat.END)

        when(p0.itemId){
            R.id.menu_tem1 ->
            {
                fragment = Fragment_One()
                loadFragment()
            }  R.id.menu_second ->{
                fragment = Second_Fragment()
                loadFragment()
            }  R.id.menu_third ->{
                fragment = Third_Fragment()
                loadFragment()
            }
        }
        return true
    }

    fun loadFragment() {
        transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.frame_container, fragment)
        transaction.addToBackStack(null)
        transaction.commitAllowingStateLoss()
    }

    fun createDrawer(className : String){


        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        Utils.screenHeight = displayMetrics.heightPixels
        Utils.screenWidth = displayMetrics.widthPixels

        setupActionBar("Dashboard", false)
        navHeader = navigationView.getHeaderView(0)
        navigationView.itemIconTintList = null
        val navMenuView: NavigationMenuView = navigationView.getChildAt(0) as NavigationMenuView
        navMenuView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        navigationView.setNavigationItemSelectedListener(this)

        val user_name = navigationView.getHeaderView(0).findViewById(R.id.user_name) as TextView
        user_name.setText("First")

        val usr_code = navigationView.getHeaderView(0).findViewById(R.id.usr_code) as TextView
        usr_code.setText("Second")

        val dealer_branch = navigationView.getHeaderView(0).findViewById(R.id.dealer_branch) as TextView
        dealer_branch.setText("Third")

        //onNavigationItemSelected(navigationView.menu.getItem(0))
        fragmentManager = supportFragmentManager
        transaction = fragmentManager.beginTransaction()
        transaction.setCustomAnimations(
            R.anim.enter_from_right_frag,
            R.anim.exit_to_left_frag,
            R.anim.enter_from_left_frag,
            R.anim.exit_to_right_frag
        )


        actionBarDrawerToggle = object : ActionBarDrawerToggle(this, drawer_layout, mttol_bar, R.string.openDrawer, R.string.closeDrawer) {

            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
            }
        }

        drawer_layout.addDrawerListener(actionBarDrawerToggle)
        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState()


        bottom_navigation.setOnNavigationItemSelectedListener( object :BottomNavigationView.OnNavigationItemSelectedListener{
            override fun onNavigationItemSelected(p0: MenuItem): Boolean {
                when(p0.itemId){
                    R.id.menu_tem1 ->
                    {
                        fragment = Fragment_One()
                        loadFragment()
                        return true
                    }  R.id.menu_second ->{
                    fragment = Second_Fragment()
                    loadFragment()
                    return true
                }  R.id.menu_third ->{
                    fragment = Third_Fragment()
                    loadFragment()
                    return true
                }
                }
                return false
            }

        })


    }

    fun setupActionBar(title: String, isbackEnable: Boolean) {
        //setSupportActionBar(mttol_bar)
        if (supportActionBar != null)
            supportActionBar!!.setTitle("")

       // mttol_bar_title.setText(title)
        if (isbackEnable) {
            supportActionBar!!.setDisplayShowHomeEnabled(true)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setHomeButtonEnabled(true)
        }
    }
}
