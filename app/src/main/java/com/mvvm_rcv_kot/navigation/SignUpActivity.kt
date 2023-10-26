package com.mvvm_rcv_kot.navigation

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.mvvm_rcv_kot.FirestoreFile
import com.mvvm_rcv_kot.R
import com.mvvm_rcv_kot.User
import com.mvvm_rcv_kot.databinding.ActivitySignUpBinding

class SignUpActivity : BaseActivity() {
    private var binding: ActivitySignUpBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setupActionBar()

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    private fun setupActionBar(){
        setSupportActionBar(binding?.toolBarSignUp)

        val actionBar = supportActionBar
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_24)
            actionBar.setDisplayShowTitleEnabled(false)  // This line hides the default title
        }

        binding?.toolBarSignUp?.setNavigationOnClickListener { onBackPressed() }

        binding?.signUpFbBtn?.setOnClickListener {
            regUserValidator()
        }
    }

    fun regUserValidator(){
        val name: String = binding?.signUpNameEt?.text.toString().trim() { it <= ' '}
        val email: String = binding?.signUpEmailEt?.text.toString().trim() { it <= ' '}
        val password: String = binding?.signUpPassEt?.text.toString().trim() { it <= ' '}

        if(formValidator(name, email, password)){
            binding?.progressBar?.visibility = View.VISIBLE
            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                        task ->
                    if (task.isSuccessful) {
                        val firebaseUser: FirebaseUser = task.result!!.user!!
                        val registeredEmail = firebaseUser.email!!
                        val user = User(firebaseUser.uid, name, registeredEmail)
                        FirestoreFile().registerUser(this, user)
                    } else {
                        Toast.makeText(this, task.exception!!.message, Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    fun formValidator(name: String, email: String, password: String) : Boolean{
        return when{
            TextUtils.isEmpty(name)->{
                showErrorSnackBar("Put your name")
                false
            }

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

    fun userRegisteredSuccess(){
        binding?.progressBar?.visibility = View.GONE
        Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show()
        FirebaseAuth.getInstance().signOut()
        finish()
    }
}