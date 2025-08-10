package com.example.rootprofiles

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.rootprofiles.databinding.ActivityMainBinding
import androidx.preference.PreferenceManager

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pm = ProfileManager(this)
        val profiles = pm.loadProfiles()

        binding.btnBattery.setOnClickListener { apply("battery", profiles) }
        binding.btnBalanced.setOnClickListener { apply("balanced", profiles) }
        binding.btnGaming.setOnClickListener { apply("gaming", profiles) }

        binding.txtStatus.text = if (ShellRunner.isRootAvailable()) "Root OK" else "Root NOT available"
    }

    private fun apply(name: String, profiles: Map<String, Profile>) {
        val p = profiles[name] ?: return
        ShellRunner.run(p.commands)
        binding.txtStatus.text = "Applied: ${p.name}"
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        prefs.edit().putString("last_profile", name).apply()
    }
}
