package com.example.testingone.stepper

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.testingone.R
import com.example.testingone.Util.UImportant.Companion.showAlert
import com.example.testingone.fragments.Fragment_One
import com.example.testingone.fragments.Second_Fragment
import com.example.testingone.fragments.Third_Fragment
import com.karvy.atwl.sessionmanager.TimezOut
import kotlinx.android.synthetic.main.activity_work_with_stepper.*

class WorkWithStepperAct : AppCompatActivity() {

    lateinit var view:View
    lateinit var sAdapter:FragmentAdapter

     val list= arrayOf("Step 1","Step 2","Step 3")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work_with_stepper)
        sAdapter= FragmentAdapter(supportFragmentManager)

        stepper_indicatoior.setLabels(list)
        view=layoutInflater.inflate(R.layout.first_lyt_for_stepper,null)
        first_lyt.addView(view)
        //stepper_indicatoior.currentStep=1


        view.findViewById<Button>(R.id.button2).setOnClickListener {
           first_lyt.addView(layoutInflater.inflate(R.layout.first_lyt_for_stepper,null))
            stepper_indicatoior.currentStep=1

        }


//        mview_pager.adapter=sAdapter
//
//
//        //mview_pager.beginFakeDrag()
//
//
//        stepper_indicatoior.showLabels(true);
//        stepper_indicatoior.setViewPager(mview_pager);
//        // or keep last page as "end page"
//        stepper_indicatoior.setViewPager(mview_pager, mview_pager.getAdapter()!!.getCount() - 1)

    }


    class FragmentAdapter(fm:FragmentManager): FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {

            when(position){
                0 ->{
                    return Fragment_One()
                }
                1 ->{
                    return Second_Fragment()
                }
                3 ->{
                    return Third_Fragment()
                }
            }
            return Fragment_One()
        }

        override fun getCount(): Int {
            return 3
        }

    }

    internal var broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            // internet lost alert dialog method call from here...
            showAlert(context)
        }
    }

    override fun onStart() {
        super.onStart()

        registerReceiver(broadcastReceiver, IntentFilter("TimeOut"))
        //TimezOut.register(this)
        Log.d("Timer Registered", "True")
        TimezOut.startTimer()
        Log.d("Timer Started", "True")
    }

    override fun onResume() {
        TimezOut.startTimer()
        Log.d("Timer Re-Started", "True")
        super.onResume()
    }

    public override fun onPause() {
        super.onPause()
        TimezOut.unregister()
        //Log.d("Timer Un-Registered","false");
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(broadcastReceiver)
    }

}
