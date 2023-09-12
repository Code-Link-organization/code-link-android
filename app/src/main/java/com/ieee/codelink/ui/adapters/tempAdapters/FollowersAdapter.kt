package com.ieee.codelink.ui.adapters.tempAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ieee.codelink.R
import com.ieee.codelink.common.getImageForGlide
import com.ieee.codelink.common.setImageUsingGlide
import com.ieee.codelink.databinding.CardFollowerBinding
import com.ieee.codelink.databinding.CardLikePersonBinding
import com.ieee.codelink.domain.tempModels.TempUserSearch


class FollowersAdapter(
    var users: MutableList<TempUserSearch>,
    private val openProfile: (TempUserSearch) -> Unit
) : RecyclerView.Adapter<FollowersAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: CardFollowerBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CardFollowerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = users.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val like = users[position]
        setViews(holder, like)
        setOnClicks(holder, like)
    }

    private fun setViews(holder: ViewHolder, user: TempUserSearch) {
        holder.binding.apply {
            tvUserNamer.text = user.name

            setImageUsingGlide(
                view = holder.binding.ivUserImage,
                image = getImageForGlide(user.image)
            )
        }
    }

    private fun setOnClicks(holder: ViewHolder, like: TempUserSearch) {
        holder.binding.root.setOnClickListener {
            openProfile(like)
        }
    }


}