package com.internshala.activitylifecycle.activities

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.room.Room
import com.internshala.activitylifecycle.R
import com.internshala.activitylifecycle.database.UserDatabase
import com.internshala.activitylifecycle.database.UserEntity
import com.internshala.activitylifecycle.utils.ConnectionManager

class RegistrationActivity : AppCompatActivity() {

    lateinit var btnRegistration: Button
    lateinit var etxtRegName: EditText
    lateinit var etxtRegMail: EditText
    lateinit var etxtRegNumber: EditText
    lateinit var etxtRegAddress: EditText
    lateinit var etxtRegPwd: EditText
    lateinit var regToolbar1: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        title = "Register Yourself"

        etxtRegName = findViewById(R.id.etxtRegName)
        etxtRegMail = findViewById(R.id.etxtRegMail)
        etxtRegNumber = findViewById(R.id.etxtRegNumber)
        etxtRegAddress = findViewById(R.id.etxtRegAddress)
        etxtRegPwd = findViewById(R.id.etxtRegPwd)
        regToolbar1 = findViewById(R.id.regToolbar1)

        btnRegistration = findViewById(R.id.btnRegistration)

        btnRegistration.setOnClickListener{
//            val regName = etxtRegName.text.toString()
//            val regMail = etxtRegMail.text.toString()
//            val regNumber = etxtRegNumber.text.toString()
//            val regAddress = etxtRegAddress.text.toString()
//            val regPwd = etxtRegPwd.text.toString()
//            intent = Intent(this@RegistrationActivity, RegistrationInfoActivity::class.java)
//            intent.putExtra("displayName", regName)
//            intent.putExtra("displayMail", regMail)
//            intent.putExtra("displayNumber", regNumber)
//            intent.putExtra("displayAddress", regAddress)
//            intent.putExtra("displayPwd", regPwd)
//            startActivity(intent)
//            Toast.makeText(
//                this@RegistrationActivity,
//                "This app is still in its development phase. This feature shall be available shortly!",
//                Toast.LENGTH_SHORT
//            ).show()
//            finish()

            if(ConnectionManager().checkConnectivity(this@RegistrationActivity)) {
                val regName = etxtRegName.text.toString()
                val regMail = etxtRegMail.text.toString()
                val regNumber = etxtRegNumber.text.toString()
                val regAddress = etxtRegAddress.text.toString()
                val regPwd = etxtRegPwd.text.toString()

                val userEntity = UserEntity(
                    regNumber,
                    regName,
                    regMail,
                    regAddress,
                    regPwd
                )

                val registerUser = DBAsyncTask(applicationContext, userEntity).execute()
                val isRegistered = registerUser.get()

                if(isRegistered) {
                    Toast.makeText(
                        this@RegistrationActivity,
                        "You have been registered successfully! (SQLite Database)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else {
                    Toast.makeText(
                        this@RegistrationActivity,
                        "Registration Failed!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            else {
                showInternetDialogBox()
            }
        }

        setSupportActionBar(regToolbar1)
        supportActionBar?.title = "FoodHub: Registration"

        var actionBar = getSupportActionBar()
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onBackPressed() {
        intent = Intent(this@RegistrationActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                intent = Intent(this@RegistrationActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    class DBAsyncTask(val context: Context, val userEntity: UserEntity): AsyncTask<Void, Void, Boolean>() {

        val db = Room.databaseBuilder(context, UserDatabase::class.java, "users-db").build()

        override fun doInBackground(vararg params: Void?): Boolean {

            db.userDao().insertUser(userEntity)

            return true
        }

    }

    fun showInternetDialogBox() {
        val dialog = AlertDialog.Builder(this@RegistrationActivity)
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
}