package com.example.currencyconverter

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // Check for internet connection
        if (isInternetConnected()) {
            // Proceed to next activity after a delay
            CoroutineScope(Dispatchers.Main).launch {
                delay(2000) // Simulating a delay for splash screen
                // Start the main activity or your next activity
                startActivity(Intent(this@SplashScreen, MainActivity::class.java))
                finish()
            }
        } else {
            // Show no internet connection dialog
            showNoInternetDialog()
        }
    }

    private fun isInternetConnected(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }

    private fun showNoInternetDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_no_internet, null)
        val retryButton = dialogView.findViewById<Button>(R.id.btnRetry)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)
            .create()

        retryButton.setOnClickListener {
            if (isInternetConnected()) {
                dialog.dismiss()
                // Proceed to next activity
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                // Optionally show a toast message or feedback to the user
            }
        }

        dialog.show()
    }
}
