package com.ieee.codelink.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ieee.codelink.databinding.CardUserStoryBinding
import com.ieee.codelink.domain.tempModels.TempUserStory


class StoriesAdapter(
    var stories: ArrayList<TempUserStory>,
    var storyClicked: (TempUserStory) -> Unit,
) : RecyclerView.Adapter<StoriesAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: CardUserStoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CardUserStoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = stories.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val story = stories[position]
        setViews(holder, story)
        setOnClicks(holder, story)
    }

    private fun setViews(holder: StoriesAdapter.ViewHolder, story: TempUserStory) {
        holder.binding.apply {
            ivUserImage.setImageResource(story.userImage)
            storyThumbnail.setImageResource(story.thunbnail)
        }
    }

    private fun setOnClicks(holder: ViewHolder, story: TempUserStory) {
        holder.binding.root.setOnClickListener {
            storyClicked(story)
        }

    }


}