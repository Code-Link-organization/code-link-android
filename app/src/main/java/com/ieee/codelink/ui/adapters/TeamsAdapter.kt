package com.ieee.codelink.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.ieee.codelink.R
import com.ieee.codelink.common.getImageForGlide
import com.ieee.codelink.common.setImageUsingGlide
import com.ieee.codelink.databinding.CardTeamBinding
import com.ieee.codelink.domain.models.Team
import com.ieee.codelink.domain.models.User


class TeamsAdapter(
    var teams: MutableList<Team>,
    var cachedUserId: Int,
    private val joinTeam: (Team) -> Unit,
    private val openTeam: (Team) -> Unit,
) : RecyclerView.Adapter<TeamsAdapter.ViewHolder>() {

    private var displayList: MutableList<Team> = mutableListOf<Team>()

    init {
        displayList.addAll(teams)
    }


    inner class ViewHolder(val binding: CardTeamBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CardTeamBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = displayList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val team = displayList[position]
        setViews(holder, team)
        setOnClicks(holder, team)
    }

    private fun setViews(holder: ViewHolder, team: Team) {
        holder.binding.apply {
            tvTeamName.text = team.name
            setImageUsingGlide(
                view = holder.binding.ivTeamImage,
                image = getImageForGlide(team.imageUrl),
                errorImage = R.drawable.teamwork
            )
            btnJoin.isGone = isJoinButtonHidden(team)
        }
    }

    private fun isJoinButtonHidden(team: Team): Boolean {
      return team.members.any{it.id == cachedUserId} || team.joinButton.not()
    }

    private fun setOnClicks(holder: ViewHolder, team: Team) {
        holder.binding.root.setOnClickListener {
            openTeam(team)
        }
        holder.binding.btnJoin.setOnClickListener {
            joinTeam(team)
        }

    }

    fun removeJoinButton(id: Int) {
          val index = teams.indexOfFirst { it.id == id }
          teams[index].joinButton = false
          notifyItemChanged(index)
    }

    fun searchFor(query: String = ""){
        if (query.isNullOrEmpty()){
            displayList.clear()
            displayList.addAll(teams)
        }else {
            val searchList = teams.filter { it.name.lowercase().contains(query.lowercase()) }
            displayList.clear()
            displayList.addAll(searchList)
        }
        notifyDataSetChanged()
    }

}