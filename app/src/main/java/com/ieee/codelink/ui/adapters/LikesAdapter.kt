package com.ieee.codelink.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ieee.codelink.R
import com.ieee.codelink.common.getImageForGlide
import com.ieee.codelink.common.setImageUsingGlide
import com.ieee.codelink.common.showToast
import com.ieee.codelink.data.remote.BASE_URL_FOR_IMAGE
import com.ieee.codelink.databinding.CardLikePersonBinding
import com.ieee.codelink.databinding.CardUserStoryBinding
import com.ieee.codelink.domain.models.LikeData
import com.ieee.codelink.domain.tempModels.TempUserStory


class LikesAdapter(
    var likes: MutableList<LikeData>,
    private val openProfile: (LikeData) -> Unit,
    private val followAction: (LikeData) -> Unit
) : RecyclerView.Adapter<LikesAdapter.ViewHolder>() {

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

    override fun getItemCount(): Int = likes.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val like = likes[position]
        setViews(holder, like)
        setOnClicks(holder, like)
    }

    private fun setViews(holder: LikesAdapter.ViewHolder, like: LikeData) {
        holder.binding.apply {
            tvUserName.text = like.user_name
            tvUserTrack.text = like.user_name

            setImageUsingGlide(
                view = holder.binding.ivUserImage,
                image = getImageForGlide(like.user_imageUrl)
            )
        }
    }

    private fun setOnClicks(holder: ViewHolder, like: LikeData) {
        holder.binding.root.setOnClickListener {
            openProfile(like)
        }
        holder.binding.btnFollow.setOnClickListener {
            followAction(like)
        }

    }


}