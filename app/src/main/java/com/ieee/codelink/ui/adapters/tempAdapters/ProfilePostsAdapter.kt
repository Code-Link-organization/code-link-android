package com.ieee.codelink.ui.adapters.tempAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ieee.codelink.R
import com.ieee.codelink.common.setImageUsingGlide
import com.ieee.codelink.databinding.CardFollowerBinding
import com.ieee.codelink.databinding.CardPostProfileBinding
import com.ieee.codelink.domain.tempModels.TempUserSearch


class ProfilePostsAdapter(
    var posts: MutableList<Int>,
    private val opemImage: (Int) -> Unit
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

    private fun setViews(holder: ViewHolder, post: Int) {
        holder.binding.apply {

            setImageUsingGlide(
                view = holder.binding.imageView,
                image = post
            )
        }
    }

    private fun setOnClicks(holder: ViewHolder, post: Int) {
        holder.binding.root.setOnClickListener {
            opemImage(post)
        }
    }


}