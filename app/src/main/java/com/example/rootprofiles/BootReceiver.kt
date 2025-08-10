package com.example.rootprofiles

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.preference.PreferenceManager

class BootReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            val profile = prefs.getString("last_profile", null) ?: return
            val pm = ProfileManager(context).loadProfiles()
            pm[profile]?.let { ShellRunner.run(it.commands) }
        }
    }
}
