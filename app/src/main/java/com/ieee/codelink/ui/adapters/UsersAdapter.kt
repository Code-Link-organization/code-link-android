package com.ieee.codelink.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ieee.codelink.common.getImageForGlide
import com.ieee.codelink.common.setImageUsingGlide
import com.ieee.codelink.databinding.CardLikePersonBinding
import com.ieee.codelink.domain.models.User


class UsersAdapter(
    var users: MutableList<User>,
    private val openProfile: (User) -> Unit,
    private val followAction: (User) -> Unit
) : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: CardLikePersonBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CardLikePersonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = users.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users[position]
        setViews(holder, user)
        setOnClicks(holder, user)
    }

    private fun setViews(holder: ViewHolder, user: User) {
        holder.binding.apply {
            tvUserName.text = user.name
            tvUserTrack.text = user.track

            setImageUsingGlide(
                view = holder.binding.ivUserImage,
                image = getImageForGlide(user.imageUrl)
            )
        }
    }

    private fun setOnClicks(holder: ViewHolder, user: User) {
        holder.binding.root.setOnClickListener {
            openProfile(user)
        }
        holder.binding.btnFollow.setOnClickListener {
            followAction(user)
        }
    }


}