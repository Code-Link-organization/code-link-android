package com.ieee.codelink.ui.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ieee.codelink.R
import com.ieee.codelink.common.showToast
import com.ieee.codelink.databinding.DialogCreatePostBinding
import com.ieee.codelink.domain.CreatePostModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreatePostDialogFragment(
    private val createPost : (CreatePostModel)-> Unit
): DialogFragment() {

    private lateinit var binding: DialogCreatePostBinding
    private var imgUri : Uri? = null


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

            val images = if (imgUri != null){
                listOf<Uri>(imgUri!!)
            }else{
                null
            }

            val createPostModel = CreatePostModel(
                content = binding.etContent.text.toString(),
                images = images
            )
            createPost(createPostModel)
        }

        binding.btnCancel.setOnClickListener {
            this.dismiss()
        }

        binding.ivGallery.setOnClickListener {
            openGallery()
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

    private fun openGallery() {
        singlePhotoPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    val singlePhotoPicker =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                CoroutineScope(Dispatchers.Main).launch {
                   imgUri = uri

                    Glide.with(binding.ivImg)
                        .load(uri)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerInside()
                        .placeholder(R.drawable.ic_profile)
                        .error(R.drawable.ic_profile)
                        .into(binding.ivImg)


                    binding.ivImg.visibility = View.VISIBLE
                }
            }
        }

}