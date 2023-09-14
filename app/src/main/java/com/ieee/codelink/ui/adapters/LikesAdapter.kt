package com.ieee.codelink.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ieee.codelink.common.getImageForGlide
import com.ieee.codelink.common.setImageUsingGlide
import com.ieee.codelink.databinding.CardLikePersonBinding
import com.ieee.codelink.domain.models.responseData.LikeData


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