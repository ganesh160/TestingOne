package com.example.testingone.workingwithAdunits

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import com.example.testingone.R
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_admobs_testing.*

class AdmobsTestingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admobs_testing)

        //adView.adSize= AdSize.BANNER
        //adView.adUnitId = getString(R.string.banner_home_footer)

        val device_ID = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID)
        Log.d("deciceId",""+device_ID)
        MobileAds.initialize(this)

        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
        AdRequest.Builder().addTestDevice("56262FFB61B16A19CFA6E9CAEBFC81F0").build()

        adView.adListener= object : AdListener (){


            override fun onAdLoaded() {

                Toast.makeText(applicationContext, "Ad Loaded", Toast.LENGTH_SHORT).show()
            }

            override fun onAdFailedToLoad(errorCode: Int) {
                Toast.makeText(applicationContext, "Ad Failed To Load", Toast.LENGTH_SHORT).show()
            }

            override fun onAdClosed() {
                Toast.makeText(applicationContext, "Ad Closed", Toast.LENGTH_SHORT).show()
            }
        }


    }
}
