package com.internshala.activitylifecycle.activities

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.internshala.activitylifecycle.R
import com.internshala.activitylifecycle.fragment.FaqFragment
import com.internshala.activitylifecycle.fragment.FavoriteFragment
import com.internshala.activitylifecycle.fragment.HomeFragment
import com.internshala.activitylifecycle.fragment.ProfileFragment

class MainActivity : AppCompatActivity() {

    var titleName: String? = "SkyDroid"

    lateinit var sharedPreferences: SharedPreferences

    lateinit var mainToolbar: Toolbar

    lateinit var mainDrawerLayout: DrawerLayout
    lateinit var mainNavView: NavigationView
//    lateinit var btnSignOut: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = getSharedPreferences(getString(R.string.preference_file), Context.MODE_PRIVATE)

        setContentView(R.layout.activity_main)

        mainToolbar = findViewById(R.id.mainToolbar)
        mainDrawerLayout = findViewById(R.id.mainDrawerLayout)
        mainNavView = findViewById(R.id.mainNavView)
        openHome()
        setupMainToolbar()

        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this@MainActivity,
            mainDrawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )

        mainDrawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        mainNavView.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.menuHome -> {
                    openHome()
                    mainDrawerLayout.closeDrawers()
                }
                R.id.menuProfile -> {
                    supportFragmentManager.beginTransaction().replace(R.id.mainFrameLayout, ProfileFragment()).commit()
                    supportActionBar?.title = "User Profile"
                    mainDrawerLayout.closeDrawers()
                }
                R.id.menuFav -> {
                    supportFragmentManager.beginTransaction().replace(R.id.mainFrameLayout, FavoriteFragment()).commit()
                    supportActionBar?.title = "Favorites"
                    mainDrawerLayout.closeDrawers()
                }
                R.id.menuFAQS -> {
                    supportFragmentManager.beginTransaction().replace(R.id.mainFrameLayout, FaqFragment()).commit()
                    supportActionBar?.title = "FAQ: Frequently Asked Questions"
                    mainDrawerLayout.closeDrawers()
                }
                R.id.menuLogout -> {
                    sharedPreferences.edit().clear().apply()
                    val intent = Intent(this@MainActivity, LoginActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(
                        this@MainActivity,
                        "You have been successfully logged out!",
                        Toast.LENGTH_SHORT
                    ).show()
                    this.finish()
                }
            }
            return@setNavigationItemSelectedListener true
        }

//        btnSignOut = findViewById(R.id.btnSignOut)
//
//        btnSignOut.setOnClickListener{
//            sharedPreferences.edit().clear().apply()
//            val intent = Intent(this@MainActivity, LoginActivity::class.java)
//            startActivity(intent)
//            Toast.makeText(
//                this@MainActivity,
//                "You have been successfully logged out!",
//                Toast.LENGTH_SHORT
//            ).show()
//            this.finish()
//        }
    }

    override fun onBackPressed() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.mainFrameLayout)

        when(currentFragment) {
            is HomeFragment -> {
                val dialog = AlertDialog.Builder(this@MainActivity)
                dialog.setTitle("Confirmation")
                dialog.setMessage("Exit the app?")
                dialog.setPositiveButton("Yes"){text, listener ->
                    this.finish()
                }
                dialog.setNegativeButton("No"){text, listener ->
                }
                dialog.create()
                dialog.show()
            }
            !is HomeFragment -> {
                openHome()
            }
            else -> {
                finish()
            }
        }
    }

    fun setupMainToolbar() {
        setSupportActionBar(mainToolbar)
        supportActionBar?.title = "FoodHub"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun openHome() {
        supportFragmentManager.beginTransaction().replace(R.id.mainFrameLayout, HomeFragment()).commit()
        supportActionBar?.title = "Home"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mainNavView.setCheckedItem(R.id.menuHome)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if(id == android.R.id.home) {
            mainDrawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }
}