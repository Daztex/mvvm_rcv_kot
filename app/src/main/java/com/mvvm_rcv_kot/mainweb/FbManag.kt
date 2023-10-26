package com.mvvm_rcv_kot.mainweb

import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.mvvm_rcv_kot.R

class FbManag(
    private val context: Context,
    private val prefs: SharedPreferences,
    private val serv: Serv,
    private val callback: () -> Unit // Callback function
) {

    fun initFirebase() {
        val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    var buster = remoteConfig.getBoolean(Const.KER)
                    val value = remoteConfig.getString(Const.KONF)
                    val ones = remoteConfig.getString(Const.EWRW)
                    val server = remoteConfig.getString(Const.SERVASD)
                    val send = remoteConfig.getString(Const.IOTTE)

                    val editor = prefs.edit()
                    editor.putString(Const.KONF, value)
                    editor.putString(Const.EWRW, ones)
                    editor.putString(Const.SERVASD, server)
                    editor.putString(Const.IOTTE, send)
                    editor.apply()

                    Log.d(ContentValues.TAG, "my url $value")
                    Log.d(ContentValues.TAG, "my url bool $buster")


                    if (buster) {
                        serv.initializeService()
                    } else {
                        callback.invoke()
                    }
                } else {
                    callback.invoke()
                }
            }.addOnFailureListener {
                Log.d(ContentValues.TAG, "my url error ${it.message}")

            }
    }
}
