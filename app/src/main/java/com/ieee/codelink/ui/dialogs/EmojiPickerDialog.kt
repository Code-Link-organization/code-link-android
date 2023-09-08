package com.ieee.codelink.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.GridView
import androidx.fragment.app.DialogFragment
import com.ieee.codelink.R

class EmojiPickerDialog : DialogFragment() {

    lateinit var emojiGrid: GridView
    lateinit var adapter: ArrayAdapter<String>

    val emojis = arrayOf(
        "😀", "😃", "😄", "😁", "😆", "😅", "😂", "🤣", "😊", "😍", // Smileys
        "👍", "👎", "👌", "👏", "🙌", "🤝", "👋", "👐", "🤲", "🙏", // Hand gestures
        "🎉", "🎈", "🎁", "🎂", "🍰", "🍾", "🥂", "🎊", "🎆", "🎇", // Celebration
        "❤️", "💔", "❣️", "💕", "💞", "💓", "💗", "💖", "💘", "💝", // Heart emojis
        /* Add more emojis here */
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.emoji_picker_dialog, container, false)
        emojiGrid = rootView.findViewById<GridView>(R.id.emoji_grid)
        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, emojis)
        emojiGrid.adapter = adapter
        return rootView
    }

    fun setEmojiGridWithEditText(et: EditText) {
        emojiGrid.setOnItemClickListener { _, _, position, _ ->
            val selectedEmoji = emojis[position]
            val editText = et
            editText.append(selectedEmoji)
        }
    }

    /*
    to use
    val emojiPickerDialog = EmojiPickerDialogFragment()
emojiPickerDialog.show(supportFragmentManager, "emoji_picker")
     */
}