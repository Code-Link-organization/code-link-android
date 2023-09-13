package com.ieee.codelink.ui.adapters.tempAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ieee.codelink.common.setImageUsingGlide
import com.ieee.codelink.databinding.ItemInboxChatBinding
import com.ieee.codelink.domain.tempModels.TempChatUser
import kotlin.random.Random


class InboxAdapter(
    var chats: MutableList<TempChatUser>,
    private val openChat: (TempChatUser) -> Unit,
) : RecyclerView.Adapter<InboxAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemInboxChatBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemInboxChatBinding.inflate(
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
            tvMessage.text = chat.lastMessage
            tvTimeOfMessage.text = chat.time
            tvNum.text = (Random.nextInt(8)+1).toString()

            setImageUsingGlide(
                view = holder.binding.ivUserImage,
                image = chat.image
            )
        }
    }

    private fun setOnClicks(holder: ViewHolder, like: TempChatUser) {
        holder.binding.root.setOnClickListener {
            openChat
        }
    }


}