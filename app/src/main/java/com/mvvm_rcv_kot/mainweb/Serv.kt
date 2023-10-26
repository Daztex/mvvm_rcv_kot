package com.mvvm_rcv_kot.mainweb

import android.content.Context
import android.content.SharedPreferences
import android.util.Base64
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
    fun initializeService() {

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
                if (body == "\"Bot\"") {

                    val editor = playerPrefs.edit()
                    editor.putBoolean(Const.PWPER, true)
                    editor.apply()
                    loadGame()
                } else {
                    mainActivity.LoadWv()
                }
            }
        }
    }

    private fun loadGame() {
        mainActivity.Game()
    }
}


