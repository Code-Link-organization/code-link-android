package com.ieee.codelink.ui.adapters.tempAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ieee.codelink.R
import com.ieee.codelink.databinding.CardLikePersonBinding
import com.ieee.codelink.domain.tempModels.TempUserSearch


class UsersAdapter(
    var users: MutableList<TempUserSearch>,
    private val openProfile: (TempUserSearch) -> Unit,
    private val followAction: (TempUserSearch) -> Unit
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
        val like = users[position]
        setViews(holder, like)
        setOnClicks(holder, like)
    }

    private fun setViews(holder: ViewHolder, user: TempUserSearch) {
        holder.binding.apply {
            tvUserName.text = user.name
            tvUserTrack.text = user.track
            Glide.with(holder.binding.ivUserImage)
                .load(user.image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerInside()
                .placeholder(R.drawable.ic_profile)
                .error(R.drawable.ic_profile)
                .into(holder.binding.ivUserImage)
        }
    }

    private fun setOnClicks(holder: ViewHolder, like: TempUserSearch) {
        holder.binding.root.setOnClickListener {
            openProfile(like)
        }
        holder.binding.btnFollow.setOnClickListener {
            followAction(like)
        }
    }


}