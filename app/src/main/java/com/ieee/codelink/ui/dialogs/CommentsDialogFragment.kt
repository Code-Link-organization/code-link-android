package com.ieee.codelink.ui.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.ieee.codelink.databinding.DialogPostCommentsBinding
import com.ieee.codelink.domain.models.Comment
import com.ieee.codelink.ui.adapters.CommentsAdapter

class CommentsDialogFragment(
    private val comments: MutableList<Comment>,
    private val postId : Int,
    private val addComment: (Int , String) -> Unit
) : DialogFragment() {

    private lateinit var binding: DialogPostCommentsBinding
    private lateinit var commentsAdapter: CommentsAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogPostCommentsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRv()
        setOnClicks()
    }

    private fun setUpRv() {
        comments.reverse()
        commentsAdapter = CommentsAdapter(
            comments
        )
        binding.rvComments.adapter = commentsAdapter
//        binding.rvComments.layoutManager = LinearLayoutManager(requireActivity()).apply {
//            reverseLayout = true
//        }
    }

    private fun setOnClicks() {
      binding.ivCancel.setOnClickListener {
          this.dismiss()
      }

        binding.tvPost.setOnClickListener {
            binding.etComment.text?.let{
                if (it.toString().isNotBlank()){
                    addComment(postId , it.toString())
                    binding.etComment.setText("")
                   // this.dismiss()
                }
            }
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
        this.dialog!!.window!!.setLayout(((9 * width) / 10), (5 * height) / 10)
     //   dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    fun addCommentToList(newComment: Comment) {
      commentsAdapter.addComment(newComment)
      binding.rvComments.smoothScrollToPosition(0)
    }


}