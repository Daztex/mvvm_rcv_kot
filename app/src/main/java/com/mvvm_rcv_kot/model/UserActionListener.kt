package com.mvvm_rcv_kot.model

interface UserActionListener {
    fun onUserMove(user: User, moveBy: Int)
    fun onDelete(user: User)
    fun onDetails(user: User)
}