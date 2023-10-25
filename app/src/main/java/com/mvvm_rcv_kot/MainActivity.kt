package com.mvvm_rcv_kot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.mvvm_rcv_kot.databinding.ActivityMainBinding
import com.mvvm_rcv_kot.model.User
import com.mvvm_rcv_kot.model.UserActionListener
import com.mvvm_rcv_kot.model.UserService
import com.mvvm_rcv_kot.model.UsersListener

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UserAdapter
    private val usersService: UserService
        get() = (applicationContext as App).userService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = UserAdapter(object :UserActionListener{
            override fun onUserMove(user: User, moveBy: Int) {
                TODO("Not yet implemented")
            }

            override fun onDelete(user: User) {
                TODO("Not yet implemented")
            }

            override fun onDetails(user: User) {
                Toast.makeText(this@MainActivity, "User ${user.name}", Toast.LENGTH_SHORT).show()
            }

        })
        val linearLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = linearLayoutManager
        binding.recyclerView.adapter = adapter

        usersService.addListener(userListener)

    }

    override fun onDestroy() {
        super.onDestroy()
        usersService.removeListener(userListener)
    }

    private val userListener: UsersListener = {
        adapter.users = it
    }
}