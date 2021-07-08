package com.internshala.activitylifecycle.activities

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.room.Room
import com.internshala.activitylifecycle.R
import com.internshala.activitylifecycle.database.UserDatabase
import com.internshala.activitylifecycle.database.UserEntity
import com.internshala.activitylifecycle.utils.ConnectionManager

class LoginActivity : AppCompatActivity() {

    lateinit var tvNumber: EditText
    lateinit var tvPwd: EditText
    lateinit var btnLogIN: Button
    lateinit var tvForgotPwd: TextView
    lateinit var tvRegistration: TextView
    lateinit var loginToolbar: Toolbar

    lateinit var sharedPreferences: SharedPreferences

    val validMobileNumber = "1234"
    val validPwd = arrayOf("surya", "karn", "bhishma", "arjuna", "abhimanyu", "krishna")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = getSharedPreferences(getString(R.string.preference_file), Context.MODE_PRIVATE)

        setContentView(R.layout.activity_login)

        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)

        if(isLoggedIn) {
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        title = "SkyDroid: Log In"

        tvNumber = findViewById(R.id.etNumber)
        tvPwd = findViewById(R.id.etPwd)
        btnLogIN = findViewById(R.id.btnLogIn)
        tvForgotPwd = findViewById(R.id.tvForgotPwd)
        tvRegistration = findViewById(R.id.tvRegistration)
        loginToolbar = findViewById(R.id.loginToolbar)

        setupLoginToolbar()

        btnLogIN.setOnClickListener {
            if(ConnectionManager().checkConnectivity(this@LoginActivity)) {
                val mobileNumber = tvNumber.text.toString()
                val pwd = tvPwd.text.toString()
                val intent = Intent(this@LoginActivity, MainActivity::class.java)

//                var toolBarName = "SkyDroid"

                val registerUser = LoginActivity.DBAsyncTask(applicationContext, mobileNumber, pwd).execute()
                val isRegistered = registerUser.get()

                if(isRegistered) {
                    savePreferencesTrue(mobileNumber)
                    startActivity(intent)
                    Toast.makeText(
                        this@LoginActivity,
                        "You have been successfully logged in!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else {
                    Toast.makeText(
                        this@LoginActivity,
                        "Invalid credentials!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

//                if (mobileNumber == validMobileNumber && validPwd.contains(pwd)) {
//                    when (pwd) {
//                        validPwd[0] -> {
//                            toolBarName = "Welcome, Surya Dev"
//                            savePreferencesTrue(toolBarName)
//                            startActivity(intent)
//                            Toast.makeText(
//                                this@LoginActivity,
//                                "You have been successfully logged in!",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                        validPwd[1] -> {
//                            toolBarName = "Welcome, Daanveer Karn"
//                            savePreferencesTrue(toolBarName)
//                            startActivity(intent)
//                            Toast.makeText(
//                                this@LoginActivity,
//                                "You have been successfully logged in!",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                        validPwd[2] -> {
//                            toolBarName = "Welcome, Bhishma Pitamah"
//                            savePreferencesTrue(toolBarName)
//                            startActivity(intent)
//                            Toast.makeText(
//                                this@LoginActivity,
//                                "You have been successfully logged in!",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                        validPwd[3] -> {
//                            toolBarName = "Welcome, Arjuna"
//                            savePreferencesTrue(toolBarName)
//                            startActivity(intent)
//                            Toast.makeText(
//                                this@LoginActivity,
//                                "You have been successfully logged in!",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                        validPwd[4] -> {
//                            toolBarName = "Welcome, Abhimanyu"
//                            savePreferencesTrue(toolBarName)
//                            startActivity(intent)
//                            Toast.makeText(
//                                this@LoginActivity,
//                                "You have been successfully logged in!",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                        validPwd[5] -> {
//                            toolBarName = "Welcome, Krishna"
//                            savePreferencesTrue(toolBarName)
//                            startActivity(intent)
//                            Toast.makeText(
//                                this@LoginActivity,
//                                "You have been successfully logged in!",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                    }
//                } else {
//                    Toast.makeText(
//                        this@LoginActivity,
//                        "Invalid credentials!",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
            }
            else {
                showInternetDialogBox()
            }
        }

        tvRegistration.setOnClickListener{
            if(ConnectionManager().checkConnectivity(this@LoginActivity)) {
                val intent = Intent(this@LoginActivity, RegistrationActivity::class.java)
                startActivity(intent)
            }
            else {
                showInternetDialogBox()
            }
        }

        tvForgotPwd.setOnClickListener{
            if(ConnectionManager().checkConnectivity(this@LoginActivity)) {
                val intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
                startActivity(intent)
            }
            else {
                showInternetDialogBox()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        finish()
    }

    fun savePreferencesTrue(title: String) {
        sharedPreferences.edit().putString("Title", title).apply()
        sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()
    }

    override fun onBackPressed() {
        val dialog = AlertDialog.Builder(this@LoginActivity)
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

    fun setupLoginToolbar() {
        setSupportActionBar(loginToolbar)
        supportActionBar?.title = "FoodHub: Log in"
    }

    fun showInternetDialogBox() {
        val dialog = AlertDialog.Builder(this@LoginActivity)
        dialog.setTitle("Error")
        dialog.setMessage("Internet connection is not established on this device!")
        dialog.setPositiveButton("Open Settings"){text, listener ->
            val settingsIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
            startActivity(settingsIntent)
            this.finish()
        }
        dialog.setNegativeButton("Exit"){text, listener ->
            this.finish()
            //ActivityCompat.finishAffinity(activity as Activity)
        }
        dialog.create()
        dialog.show()
    }

    class DBAsyncTask(val context: Context, val number: String, val pwd: String): AsyncTask<Void, Void, Boolean>() {

        val db = Room.databaseBuilder(context, UserDatabase::class.java, "users-db").build()

        override fun doInBackground(vararg params: Void?): Boolean {

            val user: UserEntity? = db.userDao().checkUser(number, pwd)
            db.close()
            return user != null

        }

    }
}