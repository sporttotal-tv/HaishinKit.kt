package com.haishinkit.studio

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private var fragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navigation = findViewById(R.id.navigation) as BottomNavigationView
        navigation.setOnNavigationItemSelectedListener(this)

        fragment = CameraTabFragment.newInstance()
        val transaction = supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.content, fragment!!)?.commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.navigation_home -> {
                fragment = CameraTabFragment.newInstance()
            }
            R.id.navigation_dashboard -> {
                fragment = PreferenceFragment.newInstance()
            }
            else -> {

            }
        }
        val transaction = supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.content, fragment!!)?.commit()
        return true
    }
}
