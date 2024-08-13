package com.exronin.mobiletest.data.repository


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.exronin.mobiletest.data.model.User
import com.exronin.mobiletest.databinding.ItemUserBinding
import com.squareup.picasso.Picasso

class UserAdapter(
    private val users: MutableList<User>,
    private val onUserClick: (User) -> Unit
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int = users.size

    fun addUsers(newUsers: List<User>) {
        users.addAll(newUsers)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            binding.userName.text = "${user.first_name} ${user.last_name}"
            binding.userEmail.text = user.email
            Picasso.get().load(user.avatar).into(binding.avatarImage)

            binding.root.setOnClickListener {
                onUserClick(user)
            }
        }
    }

}