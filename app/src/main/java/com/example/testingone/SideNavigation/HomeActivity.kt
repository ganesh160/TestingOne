package com.example.testingone.SideNavigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testingone.R
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        super.createDrawer(this.getLocalClassName())
        mttol_bar.setNavigationIcon(null)
    }
}
