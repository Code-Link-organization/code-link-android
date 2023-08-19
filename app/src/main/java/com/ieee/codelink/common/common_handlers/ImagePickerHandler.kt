package com.ieee.codelink.common.common_handlers

import android.Manifest
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.ieee.codelink.R
import com.ieee.codelink.common.showToast
import gun0912.tedimagepicker.builder.TedImagePicker

class ImagePickerHandler(
    private val fragment: Fragment,
    val onGrant: (imageUri: Uri) -> Unit,
) {

    private val activityResultLauncher =
        fragment.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val failedToGrant = permissions.entries.any { !it.value }
            if (failedToGrant) {
                val context = fragment.requireContext()
                showToast(context, context.getString(R.string.can_t_use_camera_without_permission))
                return@registerForActivityResult
            }
            pickImage { imageUrl ->
                onGrant(imageUrl)
            }
        }

    private fun pickImage(action: (Uri) -> Unit) {
        val context = fragment.requireContext()
        TedImagePicker.with(context)
            .title(R.string.choose_image)
            .backButton(gun0912.tedimagepicker.R.drawable.ic_arrow_back_black_24dp)
            .showCameraTile(true)
            .buttonBackground(gun0912.tedimagepicker.R.drawable.btn_done_button)
            .buttonTextColor(R.color.color_background)
            .buttonText(context.getString(R.string.choose_image))
            .errorListener { }
            .start { uri ->
                action(uri)
            }
    }

    fun pickImageWithPermissions() {
        activityResultLauncher.launch(arrayOf(Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE))
    }
}
