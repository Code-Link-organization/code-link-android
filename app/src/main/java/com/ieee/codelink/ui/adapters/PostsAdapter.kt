package com.ieee.codelink.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ieee.codelink.databinding.CardPostBinding
import com.ieee.codelink.domain.models.TempPost


class PostsAdapter(
    var posts: ArrayList<TempPost>,
    var likeClicked: (TempPost) -> Unit,
    var commentsClicked: (TempPost) -> Unit,
    var sharesClicked: (TempPost) -> Unit
) : RecyclerView.Adapter<PostsAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: CardPostBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CardPostBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = posts[position]
        setViews(holder, post)
        setOnClicks(holder, post)
    }

    private fun setViews(holder: PostsAdapter.ViewHolder, post: TempPost) {
        holder.binding.apply {

            ivUserImage.setImageResource(post.userImage)
            ivPostImage.setImageResource(post.postImage)
            tvUserName.text = post.userName
            tvPostTime.text = post.date
            tvDescription.text = post.description
            tvCommertsCounter.text = post.comments.toString()
            tvLikesCounter.text = post.likes.toString()
            tvSharesCounter.text = post.shares.toString()

        }
    }

    private fun setOnClicks(holder: ViewHolder, post: TempPost) {
        holder.binding.btnLike.root.setOnClickListener {
            likeClicked(post)
        }
        holder.binding.btnComment.root.setOnClickListener {
            commentsClicked(post)
        }
        holder.binding.btnShare.root.setOnClickListener {
            sharesClicked(post)
        }

    }


}