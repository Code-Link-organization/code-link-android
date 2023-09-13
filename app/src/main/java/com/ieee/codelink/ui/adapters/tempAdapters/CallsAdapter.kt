package com.ieee.codelink.ui.adapters.tempAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ieee.codelink.R
import com.ieee.codelink.common.setImageUsingGlide
import com.ieee.codelink.databinding.ItemCallChatBinding
import com.ieee.codelink.databinding.ItemInboxChatBinding
import com.ieee.codelink.domain.tempModels.TempChatUser
import kotlin.random.Random


class CallsAdapter(
    var chats: MutableList<TempChatUser>,
    private val openChat: (TempChatUser) -> Unit,
) : RecyclerView.Adapter<CallsAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemCallChatBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCallChatBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = chats.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chat = chats[position]
        setViews(holder, chat)
        setOnClicks(holder, chat)
    }

    private fun setViews(holder: ViewHolder, chat: TempChatUser) {
        holder.binding.apply {
            tvName.text = chat.name
            tvMessage.text = chat.time
            setImageUsingGlide(
                view = holder.binding.ivUserImage,
                image = chat.image
            )
        }
    }

    private fun setOnClicks(holder: ViewHolder, chat: TempChatUser) {
        holder.binding.root.setOnClickListener {
            openChat(chat)
        }
    }


}