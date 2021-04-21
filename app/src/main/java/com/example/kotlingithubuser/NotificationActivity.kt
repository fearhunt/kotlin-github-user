package com.example.kotlingithubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kotlingithubuser.databinding.ActivityNotificationBinding
import com.example.kotlingithubuser.util.NotificationPreferenceFragment

class NotificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Notification"

        supportFragmentManager.beginTransaction().add(R.id.settings_preference, NotificationPreferenceFragment()).commit()
    }
}