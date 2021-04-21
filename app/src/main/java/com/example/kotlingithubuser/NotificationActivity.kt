package com.example.kotlingithubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.kotlingithubuser.databinding.ActivityNotificationBinding
import com.example.kotlingithubuser.util.NotificationReceiver

class NotificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotificationBinding
    private lateinit var notificationReceiver: NotificationReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Notification"

        notificationReceiver = NotificationReceiver()

        binding.switchButton.setOnCheckedChangeListener { _, isChecked ->
            Log.d("isCheckedStatus", isChecked.toString())

            if (isChecked) {
                // For testing
                notificationReceiver.setNotification(this, "17:06")
            }
            else {

            }
        }
    }
}