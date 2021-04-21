package com.example.kotlingithubuser.util

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.example.kotlingithubuser.R

class NotificationPreferenceFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var NOTIFICATION: String
    private lateinit var isActiveNotificationPreference: SwitchPreference

    override fun onCreatePreferences(bundle: Bundle?, s: String?) {
        addPreferencesFromResource(R.xml.preferences)
        init()
        setNotificationSettings()
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        if (key == NOTIFICATION) {
            isActiveNotificationPreference.isChecked = sharedPreferences.getBoolean(NOTIFICATION, DEFAULT_VALUE)
            setNotificationSettings()
        }
    }

    private fun init() {
        NOTIFICATION = resources.getString(R.string.key_notification)

        isActiveNotificationPreference = findPreference<SwitchPreference>(NOTIFICATION) as SwitchPreference
    }

    private fun setNotificationSettings() {
        val sh = preferenceManager.sharedPreferences
        isActiveNotificationPreference.isChecked = sh.getBoolean(NOTIFICATION, DEFAULT_VALUE)

        val notificationReceiver = NotificationReceiver()
        val context = activity
        if (isActiveNotificationPreference.isChecked) {
            if (context != null) {
                notificationReceiver.setNotification(context, "09:00")
            }
        }
        else {
            if (context != null) {
                notificationReceiver.cancelNotification(context)
            }
        }
    }

    companion object {
        private const val DEFAULT_VALUE = true
    }
}