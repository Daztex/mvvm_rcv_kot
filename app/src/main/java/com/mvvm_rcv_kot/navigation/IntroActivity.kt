package com.mvvm_rcv_kot.navigation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.mvvm_rcv_kot.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {
    private var binding : ActivityIntroBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

    }

    override fun onStart() {
        super.onStart()
        SignUp()
        SignIn()
        skipRegistration()
    }

    private fun SignUp(){
        binding?.signUpBtn?.setOnClickListener{
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    private fun SignIn(){
        binding?.signInBtn?.setOnClickListener{
            startActivity(Intent(this, SignInActivity::class.java))
        }
    }

    private fun skipRegistration(){
        binding?.skipRegOrSignBtn?.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}