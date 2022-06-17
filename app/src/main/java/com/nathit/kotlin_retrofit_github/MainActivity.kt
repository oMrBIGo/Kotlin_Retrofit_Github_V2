package com.nathit.kotlin_retrofit_github

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

const val BASE_URL = "https://api.github.com/"

class MainActivity : AppCompatActivity() {

    private val homeFragment = HomeFragment()
    private val eventFragment = EventFragment()
    private val gistFragment = GistFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)


        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.nav_user -> replaceFragment(homeFragment)
                R.id.nav_event -> replaceFragment(eventFragment)
                R.id.nav_gist -> replaceFragment(gistFragment)
            }
            true
        }
        
        replaceFragment(homeFragment)
    }

    private fun replaceFragment(fragment: Fragment) {
        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frameLayout, fragment)
            transaction.commit()
        }
    }
}