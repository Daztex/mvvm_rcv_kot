package com.mvvm_rcv_kot.model

interface UserActionListener {
    fun onUserMove(topUser: TopUser, moveBy: Int)
    fun onDelete(topUser: TopUser)
    fun onDetails(topUser: TopUser)
}