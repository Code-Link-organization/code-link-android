package com.ieee.codelink.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.ieee.codelink.common.getImageForGlide
import com.ieee.codelink.common.setImageUsingGlide
import com.ieee.codelink.common.setImageUsingGlideCenterCrop
import com.ieee.codelink.databinding.CardPostProfileBinding


class ProfilePostsAdapter(
    var posts: MutableList<String>,
    private val opemImage: (String, ImageView) -> Unit
) : RecyclerView.Adapter<ProfilePostsAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: CardPostProfileBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CardPostProfileBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = posts.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = posts[position]
        setViews(holder, post)
        setOnClicks(holder, post)
    }

    private fun setViews(holder: ViewHolder, post: String) {
        holder.binding.apply {
            setImageUsingGlideCenterCrop(
                view = holder.binding.imageView,
                image = getImageForGlide(post)
            )
        }
    }

    private fun setOnClicks(holder: ViewHolder, post: String) {
        holder.binding.root.setOnClickListener {
            val img = getImageForGlide(post)
            img?.let {imgUrl ->
                opemImage(imgUrl, holder.binding.imageView)
            }
        }
    }


}