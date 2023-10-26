package com.mvvm_rcv_kot.navigation

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.mvvm_rcv_kot.R
import com.mvvm_rcv_kot.databinding.ActivityBaseBinding

open class BaseActivity : AppCompatActivity() {
    private var binding : ActivityBaseBinding? = null
    private var doublePressBack = false
    private lateinit var progressDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }

    fun progressDialogShower(text: String)
    {
        progressDialog = Dialog(this)

        //progressDialog.setContentView(progressDialog.findViewById(R.layout.dialog_progress))

        // Инфлейт макет
        val inflater = LayoutInflater.from(this)
        val dialogView = inflater.inflate(R.layout.dialog_progress, null)

        // Находим элементы макета
        val tvProgressText = dialogView.findViewById<TextView>(R.id.progress_tv)

        // Устанавливаем текст
        tvProgressText.text = text

        // Устанавливаем макет в прогресс-диалог
        progressDialog.setContentView(dialogView)

        progressDialog.show()
    }

    fun hidePogBar() {

    }

    fun firebaseAuth(): String {
        return FirebaseAuth.getInstance().currentUser!!.uid
    }
    fun doublePressBack(){
        if(doublePressBack){
            super.onBackPressed()
            return
        }
        this.doublePressBack = true

        Handler().postDelayed({
            doublePressBack = false
        }, 2000)
    }

    fun showErrorSnackBar(message: String){
        val snackBar = Snackbar.make(findViewById(android.R.id.content),
            message, Snackbar.LENGTH_SHORT)

        val snackBarView = snackBar.view
        snackBarView.setBackgroundColor(
            ContextCompat.getColor(
            this, com.google.android.material.R.color.design_default_color_error))

        snackBar.show()
    }
}