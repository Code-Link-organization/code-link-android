package com.ieee.codelink.ui.adapters.tempAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ieee.codelink.R
import com.ieee.codelink.common.setImageUsingGlide
import com.ieee.codelink.databinding.CardMyTeamBinding
import com.ieee.codelink.databinding.CardTeamBinding
import com.ieee.codelink.domain.tempModels.TempTeam


class MyTeamsAdapter(
    var teams: MutableList<TempTeam>,
    private val openTeam: (TempTeam) -> Unit,
) : RecyclerView.Adapter<MyTeamsAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: CardMyTeamBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CardMyTeamBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = teams.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val team = teams[position]
        setViews(holder, team)
        setOnClicks(holder, team)
    }

    private fun setViews(holder: ViewHolder, team: TempTeam) {
        holder.binding.apply {
            tvTeamName.text = team.name

            setImageUsingGlide(
                view = holder.binding.ivTeamImage,
                image = team.image)
        }
    }

    private fun setOnClicks(holder: ViewHolder, team: TempTeam) {
        holder.binding.root.setOnClickListener {
            openTeam(team)
        }
    }

}