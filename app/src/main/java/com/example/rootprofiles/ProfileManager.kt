package com.example.rootprofiles

import android.content.Context
import org.json.JSONObject
import java.io.BufferedReader

data class Profile(val name: String, val commands: List<String>)

class ProfileManager(private val context: Context) {
    fun loadProfiles(): Map<String, Profile> {
        val json = readAsset("profiles.json")
        val obj = JSONObject(json)
        val map = mutableMapOf<String, Profile>()
        for (key in obj.keys()) {
            val arr = obj.getJSONArray(key)
            val cmds = mutableListOf<String>()
            for (i in 0 until arr.length()) {
                val s = arr.getString(i)
                if (!s.trim().startsWith("#") && s.trim().isNotEmpty()) cmds.add(s)
            }
            map[key] = Profile(key, cmds)
        }
        return map
    }

    private fun readAsset(name: String): String {
        context.assets.open(name).use { ins ->
            return ins.bufferedReader().use(BufferedReader::readText)
        }
    }
}
