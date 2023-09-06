package com.ieee.codelink.ui.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import com.ieee.codelink.common.dp
import com.ieee.codelink.databinding.DialogCreatePostBinding
import com.ieee.codelink.domain.CreatePostModel

class CreatePostDialogFragment(
    private val createPost : (CreatePostModel)-> Unit
): DialogFragment() {

    private lateinit var binding: DialogCreatePostBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogCreatePostBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClicks()
    }

    private fun setOnClicks() {
        binding.btnCreate.setOnClickListener {
            val createPostModel = CreatePostModel(
                content = binding.etContent.text.toString()
            )
            createPost(createPostModel)
        }
    }

    override fun onResume() {
        super.onResume()
        initDialog()
    }

    private fun initDialog() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        requireActivity().setFinishOnTouchOutside(false)
        val metrics = resources.displayMetrics
        val width = metrics.widthPixels
        val height = metrics.heightPixels
        this.dialog!!.window!!.setLayout(((9 * width) / 10), (8 * height) / 10)
    }

}