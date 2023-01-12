package com.com.akshaykumar.ui

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.com.akshaykumar.R


import com.com.akshaykumar.data.db.SqliteHelper
import com.com.akshaykumar.data.home.DbRepository
import com.google.firebase.auth.FirebaseAuth


class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val sharedPreferences: SharedPreferences? =
            getSharedPreferences("SESSION", Context.MODE_PRIVATE)
        val isUser = sharedPreferences?.getBoolean("USER", false) == true
        if (isUser) {
            menuInflater.inflate(R.menu.main_menu, menu)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.action_logout -> {
                showConfirmation()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showConfirmation() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this@BaseActivity)
        builder.setCancelable(false)
        builder.setTitle("Logout")
        builder.setMessage("Are you sure you want to logout?")
        builder.setPositiveButton(
            "Yes"
        ) { dialog, p1 ->
            run {
                logout()
                dialog.dismiss()
            }
        }
        builder.setNegativeButton(
            "No"
        ) { dialog, p1 ->
            run {
                dialog.dismiss()
            }
        }

        DialogInterface.OnCancelListener { }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun logout() {
        FirebaseAuth.getInstance().signOut()
        //initialise sqlite
        val dbHelper = SqliteHelper(this@BaseActivity)
        val repository = DbRepository(dbHelper)
        repository.reset()

        val sharedPreferences: SharedPreferences? =
            getSharedPreferences("SESSION", Context.MODE_PRIVATE)
        sharedPreferences?.edit()?.putBoolean("USER", false)?.apply()
        startActivity(Intent(this@BaseActivity, BaseActivity::class.java))
        finish()
    }

}