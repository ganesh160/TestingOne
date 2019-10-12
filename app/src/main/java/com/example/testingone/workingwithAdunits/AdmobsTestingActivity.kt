package com.example.testingone.workingwithAdunits

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.example.testingone.R
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.MobileAds
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_admobs_testing.*

class AdmobsTestingActivity : AppCompatActivity() {


      var ediText: EditText? = null

    var text_input: TextInputLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admobs_testing)

        ButterKnife.bind(this)
        ediText=findViewById(R.id.edittext1) as EditText
        text_input=findViewById(R.id.text_input1)
        //adView.adSize= AdSize.BANNER
        //adView.adUnitId = getString(R.string.banner_home_footer)

        ediText!!.addTextChangedListener(MyWacther(ediText!!))

        val device_ID =
            Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID)
        Log.d("deciceId", "" + device_ID)
        MobileAds.initialize(this)

        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
        AdRequest.Builder().addTestDevice("56262FFB61B16A19CFA6E9CAEBFC81F0").build()

        adView.adListener = object : AdListener() {


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

        button1.setOnClickListener {
            performData()
            //ediText
        }

    }

    fun performData(){
        if (ediText?.text.toString().equals("")){
            text_input?.error= "Enter valid name"
            ediText?.requestFocus()
        }else{
            text_input?.error= null
        }
    }

}
class MyWacther(view:View) :TextWatcher{
    var vieee=view
    override fun afterTextChanged(p0: Editable?) {

        when(vieee.id){
            R.id.edittext1 ->{
                AdmobsTestingActivity().performData()
            }
        }
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

}





