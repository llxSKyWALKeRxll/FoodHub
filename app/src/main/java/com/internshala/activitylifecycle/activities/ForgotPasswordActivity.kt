package com.internshala.activitylifecycle.activities

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.internshala.activitylifecycle.R
import com.internshala.activitylifecycle.utils.ConnectionManager

class ForgotPasswordActivity : AppCompatActivity() {

    lateinit var txtForgotPwdNumber: EditText
    lateinit var txtForgotPwdMail: EditText
    lateinit var btnForgotSubmit: Button
    lateinit var pwdToolbar1: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        title = "Forgot Password"

        txtForgotPwdNumber = findViewById(R.id.txtForgotPwdNumber)
        txtForgotPwdMail = findViewById(R.id.txtForgotPwdMail)
        btnForgotSubmit = findViewById(R.id.btnForgotSubmit)
        pwdToolbar1 = findViewById(R.id.pwdToolbar1)

        setSupportActionBar(pwdToolbar1)
        supportActionBar?.title = "FoodHub: Forgot Password"

        var actionbar = getSupportActionBar()
        if(actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true)
        }

        btnForgotSubmit.setOnClickListener {
//            val displayPwdNumber = txtForgotPwdNumber.text.toString()
//            val displayPwdMail = txtForgotPwdMail.text.toString()
//            intent = Intent(this@ForgotPasswordActivity, ForgotPasswordDetailsActivity::class.java)
//            intent.putExtra("NumberInfo", displayPwdNumber)
//            intent.putExtra("MailInfo", displayPwdMail)
//            startActivity(intent)
//            Toast.makeText(
//                this@ForgotPasswordActivity,
//                "This app is still in its development phase. This feature shall be available shortly!",
//                Toast.LENGTH_SHORT
//            ).show()
//            finish()

            if(ConnectionManager().checkConnectivity(this@ForgotPasswordActivity)) {
                Toast.makeText(
                    this@ForgotPasswordActivity,
                    "This app is still in its development phase. This feature shall be available shortly!",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else {
                showInternetDialogBox()
            }
        }
    }

    override fun onBackPressed() {
        intent = Intent(this@ForgotPasswordActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                intent = Intent(this@ForgotPasswordActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun showInternetDialogBox() {
        val dialog = AlertDialog.Builder(this@ForgotPasswordActivity)
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