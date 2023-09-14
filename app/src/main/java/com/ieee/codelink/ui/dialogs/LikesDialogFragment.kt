package com.ieee.codelink.ui.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.ieee.codelink.databinding.DialogPostLikesBinding
import com.ieee.codelink.domain.models.responseData.LikeData
import com.ieee.codelink.ui.adapters.LikesAdapter

class LikesDialogFragment(
    private val likes: MutableList<LikeData>,
    private val openProfile: (LikeData) -> Unit,
    private val followAction : (LikeData) -> Unit
) : DialogFragment() {

    private lateinit var binding: DialogPostLikesBinding
    private lateinit var likesAdapter: LikesAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogPostLikesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRv()
        setOnClicks()
    }

    private fun setUpRv() {
        likesAdapter = LikesAdapter(
            likes,
            openProfile = {
                openProfile(it)
                this.dismiss()
            },
            followAction
        )
        binding.rvLikes.adapter = likesAdapter
    }

    private fun setOnClicks() {
      binding.ivCancel.setOnClickListener {
          this.dismiss()
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