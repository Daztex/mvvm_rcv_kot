package com.mvvm_rcv_kot.navigation

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.mvvm_rcv_kot.R
import com.mvvm_rcv_kot.databinding.ActivitySignInBinding

class SignInActivity : BaseActivity() {

    private var binding : ActivitySignInBinding? = null
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        auth = FirebaseAuth.getInstance()

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setupActionBar()
    }

    override fun onStart() {
        super.onStart()
        binding?.signInFbBtn?.setOnClickListener {
            signInRegisteredUser()
        }
    }

    private fun setupActionBar(){
        setSupportActionBar(binding?.toolBarSignIn)

        val actionBar = supportActionBar
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_24)
            actionBar.setDisplayShowTitleEnabled(false)  // This line hides the default title
        }
        binding?.toolBarSignIn?.setNavigationOnClickListener { onBackPressed() }
    }

    private fun signInRegisteredUser(){
        val email: String = binding?.signInEmailEt?.text.toString().trim() { it <= ' '}
        val password: String = binding?.signInPassEt?.text.toString().trim() { it <= ' '}

        if(formValidator(email, password)){
            binding?.progressBar?.visibility = View.VISIBLE
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) {task ->
                binding?.progressBar?.visibility = View.GONE
                if(task.isSuccessful){
                    val  user = auth.currentUser
                    Toast.makeText(this, "Welcome again!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    Toast.makeText(this, "Auth failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun formValidator(email: String, password: String) : Boolean{
        return when{

            TextUtils.isEmpty(email)->{
                showErrorSnackBar("Put your email")
                false
            }

            TextUtils.isEmpty(password)->{
                showErrorSnackBar("Put password")
                false
            } else -> {
                return true
            }
        }
    }
}