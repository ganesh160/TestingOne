package com.example.testingone.gpsLocation

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.testingone.R
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.PendingResult
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.location.*

import java.io.IOException
import java.util.*

class GpsActivity : AppCompatActivity(), GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener {

    lateinit var mGoogleApiClient:GoogleApiClient
    var locationRequest: LocationRequest? = null

    var locationManager: LocationManager? =null
    var addresses: List<Address> = emptyList()


    lateinit var result : PendingResult<LocationSettingsResult>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gps)

        Handler().postDelayed({ checkGPS() }, 5000)

    }

    override fun onConnected(p0: Bundle?) {
        val builder =  LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest!!)
        builder.setAlwaysShow(true);
        result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient,builder.build())
        result.setResultCallback(ResultCallback { this })
    }

    override fun onConnectionSuspended(p0: Int) {

    }

    override fun onConnectionFailed(p0: ConnectionResult) {

    }

    fun performLogics(){
        try {
            val gps= LocationServicess(this).getLOcation()!!

            val geocoder = Geocoder(this, Locale.getDefault())
            addresses = geocoder!!.getFromLocation(gps.latitude, gps.longitude, 1)

            Toast.makeText(this,""+gps.latitude,Toast.LENGTH_SHORT).show()
            Log.d("errss",""+addresses)
        }catch (ioException: Exception){
            Log.d("except",""+ioException)
        }
    }


    fun checkGPS(){
            // Todo Location Already on  ... start
            val manager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && hasGPSDevice(this)) {
                //Toast.makeText(LocationOnOff_Similar_To_Google_Maps.this,"Gps already enabled",Toast.LENGTH_SHORT).show();
                //finish();
                performLogics()
            }
            // Todo Location Already on  ... end

            if (!hasGPSDevice(this)) {
                //Toast.makeText(LoginActivity.this,"Gps not Supported",Toast.LENGTH_SHORT).show();
            }

            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && hasGPSDevice(this)) {
                Log.e("GPS", "Gps already enabled")
                //Toast.makeText(LocationOnOff_Similar_To_Google_Maps.this,"Gps not enabled",Toast.LENGTH_SHORT).show();
                enableLoc()
            } else {
                performLogics()
                Log.e("GPS", "Gps already enabled")
                //Toast.makeText(LocationOnOff_Similar_To_Google_Maps.this,"Gps already enabled",Toast.LENGTH_SHORT).show();
            }
    }

    private fun hasGPSDevice(context: Context): Boolean {
        val mgr = context
            .getSystemService(Context.LOCATION_SERVICE) as LocationManager ?: return false
        val providers = mgr.allProviders ?: return false
        return providers.contains(LocationManager.GPS_PROVIDER)
    }

    private fun enableLoc() {
        mGoogleApiClient = GoogleApiClient.Builder(this)
            .addApi(LocationServices.API!!)
            .addConnectionCallbacks(object : GoogleApiClient.ConnectionCallbacks {
                override fun onConnected(p0: Bundle?) {
                    Log.i("connection...", "connected")
                }

                override fun onConnectionSuspended(i: Int) {
                    mGoogleApiClient.connect()
                }
            })
            .addOnConnectionFailedListener(object : GoogleApiClient.OnConnectionFailedListener {
                override fun onConnectionFailed(connectionResult: ConnectionResult) {
                    Log.d("Location error", "Location error " + connectionResult.getErrorCode())
                }
            }).build()
        mGoogleApiClient.connect()

        val locationRequest = LocationRequest.create()
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        locationRequest.setInterval(30 * 1000)
        locationRequest.setFastestInterval(5 * 1000)
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        builder.setAlwaysShow(true)

        val result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build())
        result.setResultCallback(object : ResultCallback<LocationSettingsResult> {
            override fun onResult(result: LocationSettingsResult) {
                val status = result.getStatus()
                when (status.getStatusCode()) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        status.startResolutionForResult(this@GpsActivity, 199)
                        Handler().postDelayed({ performLogics() }, 10000)
                        Log.d("Location enabled", "Enabled.....")
                    } catch (e: IntentSender.SendIntentException) {
                        // Ignore the error.

                    }
                }
            }
        })
    }
}
