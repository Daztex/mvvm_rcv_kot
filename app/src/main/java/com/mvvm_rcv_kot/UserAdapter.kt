package com.mvvm_rcv_kot

import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mvvm_rcv_kot.databinding.ItemUserBinding
import com.mvvm_rcv_kot.model.User
import com.mvvm_rcv_kot.model.UserActionListener

class UserAdapter(val userActionListener: UserActionListener):RecyclerView.Adapter<UserAdapter.UserViewHolder>(), View.OnClickListener {

    var users: List<User> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class UserViewHolder(
        val binding: ItemUserBinding
    ):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUserBinding.inflate(inflater, parent, false)

        with(binding){
            root.setOnClickListener(this@UserAdapter)
            moreImageViewButton.setOnClickListener(this@UserAdapter)
        }
        return UserViewHolder(binding)
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        with(holder.binding){
            holder.itemView.tag = user
            moreImageViewButton.tag = user
            userNameTextView.text = user.name
            if(user.photo.isNotBlank()){
                Glide.with(photoImageView.context)
                    .load(user.photo)
                    .circleCrop()
                    .placeholder(R.drawable.baseline_supervised_user_circle_24)
                    .error(R.drawable.baseline_supervised_user_circle_24)
                    .into(photoImageView)
            }else{
                photoImageView.setImageResource(R.drawable.baseline_supervised_user_circle_24)
            }

        }
    }

    override fun onClick(view: View) {
        val user = view.tag as User
        when(view.id){
            R.id.moreImageViewButton ->{
                showPopUp(view)
            }
            else ->{
                userActionListener.onDetails(user)
            }
        }
    }

    private fun showPopUp(view: View) {
        val popupMenu = PopupMenu(view.context, view)
        popupMenu.menu.add(0, UP, Menu.NONE, "Move up")
        popupMenu.menu.add(0, DOWN, Menu.NONE, "Move down")
        popupMenu.menu.add(0, DELETE, Menu.NONE, "Delete")

        //todo
        //popupMenu.setOnMenuItemClickListener {  }
    }

    companion object{
        private const val UP = 1
        private const val DOWN = 2
        private const val DELETE = 3
    }
}