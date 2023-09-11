package com.ieee.codelink.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ieee.codelink.R
import com.ieee.codelink.common.getTimeDifference
import com.ieee.codelink.common.setImageUsingGlide
import com.ieee.codelink.data.remote.BASE_URL_FOR_IMAGE
import com.ieee.codelink.databinding.CardCommentBinding
import com.ieee.codelink.domain.models.Comment


class CommentsAdapter(
    private val comments: MutableList<Comment>,
) : RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: CardCommentBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CardCommentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = comments.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comment = comments[position]
        setViews(holder, comment)
        setOnClicks(holder, comment)
    }

    private fun setViews(holder: CommentsAdapter.ViewHolder, comment: Comment) {
        holder.binding.apply {
            tvUserName.text = comment.user_name
            tvComment.text = comment.content
            tvPostTime.text = if (comment.created_at != null) {
                getTimeDifference(comment.created_at!!)
            } else "now"

            setImageUsingGlide(
                view = holder.binding.ivUserImage,
                image = BASE_URL_FOR_IMAGE + comment.user_imageUrl)
        }
    }

    private fun setOnClicks(holder: ViewHolder, comment: Comment) {
    }

    fun addComment(comment:Comment){
        comments.add(0, comment)
        this.notifyItemInserted(0)
    }

}