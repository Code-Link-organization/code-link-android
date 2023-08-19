package com.ieee.codelink.common

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import java.io.File

fun getColorFromHex(colorString: String?): Int {
    return try {
        if (colorString == null) return Color.BLACK
        val colorToConvert = if (!colorString.startsWith('#')) "#$colorString"
        else colorString
        Color.parseColor(colorToConvert)
    } catch (e: Exception) {
        Log.e("getColorFromHex", e.localizedMessage ?: "Unknown error")
        Color.BLACK
    }
}

fun showToast(
    context: Context,
    message: String,
    success: Boolean = false,
    hideInRelease: Boolean = false,
) {
    if (hideInRelease) return
    if (success) {
        Toasty.success(
            context, message, Toasty.LENGTH_SHORT, true
        ).show()
    } else {
        Toasty.error(
            context, message, Toasty.LENGTH_SHORT, true
        ).show()
    }
}

fun getShimmerDrawable(): ShimmerDrawable {
    val shimmer = Shimmer.AlphaHighlightBuilder()
        .setDuration(1800)
        .setBaseAlpha(0.7f)
        .setHighlightAlpha(0.6f)
        .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
        .setAutoStart(true)
        .build()

    return ShimmerDrawable().apply {
        setShimmer(shimmer)
    }
}

fun getImageAsMultipartBodyPart(
    context: Context?,
    uri: Uri,
    name: String,
): MultipartBody.Part {
    val path: String = RealPathUtil.getRealPath(context, uri)
    val file = File(path)
    val reqFileSelect = file.asRequestBody("image/*".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData(name, file.name, reqFileSelect)
}

fun setImageUsingGlide(
    view: ImageView,
    image: Any?,
    placeholder: Drawable = getShimmerDrawable(),
    errorImage: Any? = null,
) {
    try {
        Glide.with(view.context)
            .load(image)
            .placeholder(placeholder)
            .error(errorImage)
            .into(view)
    } catch (e: Exception) {
        Log.e("setImageUsingGlide", e.localizedMessage ?: "Unknown error")
    }
}

fun tryNow(
    tag: String = "tryNow",
    error: ((Exception) -> Unit)? = null,
    action: () -> Unit,
) {
    try {
        action()
    } catch (e: Exception) {
        Log.e(tag, e.localizedMessage ?: "Unknown error")
    }
}

fun tryAsyncNow(
    scope: CoroutineScope,
    tag: String = "tryAsyncNow",
    error: (suspend (Exception) -> Unit)? = null,
    finally: (suspend () -> Unit)? = null,
    action: suspend () -> Unit,
) {
    scope.launch {
        try {
            action()
        } catch (e: Exception) {
            error?.invoke(e)
            Log.e(tag, e.localizedMessage ?: "Unknown error")
        } finally {
            finally?.invoke()
        }
    }
}


fun openBrowser(context: Context, url: String?) {
    if (url.isNullOrEmpty()) return
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    try {
        context.startActivity(browserIntent)
    } catch (e: Exception) {
    }
}

fun openFacebook(context: Context, url: String): Intent? {
    val packageManager = context.packageManager
    var uri = Uri.parse(url)
    return try {
        val applicationInfo = packageManager.getApplicationInfo("com.facebook.katana", 0)
        if (applicationInfo.enabled) {
            uri = Uri.parse("fb://facewebmodal/f?href=$url")
        }
        Intent(Intent.ACTION_VIEW, uri)
    } catch (ignored: PackageManager.NameNotFoundException) {
        openBrowser(context, url)
        null
    }
}

fun openPhone(context: Context, phone: String?) {
    val intent = Intent(Intent.ACTION_DIAL)
    intent.data = Uri.parse("tel:$phone")
    try {
        context.startActivity(intent)
    } catch (e: Exception) {
    }
}

fun openWhats(context: Context, phone: String?) {
    val url = "https://api.whatsapp.com/send?phone=$phone"
    try {
        val pm: PackageManager = context.packageManager
        pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES)
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        context.startActivity(i)
    } catch (e: PackageManager.NameNotFoundException) {
        openBrowser(context, url)
    } catch (e: Exception) {
    }
}

fun openEmail(context: Context, email: String?) {
    val intent = Intent(Intent.ACTION_SENDTO)
    intent.data = Uri.parse("mailto: $email")
    try {
        context.startActivity(intent)
    } catch (e: Exception) {
    }
}

fun checkPermissionList(
    fragment: Fragment,
    whenSuccess: (() -> Unit)? = null,
    whenFail: (() -> Unit)? = null,
): ActivityResultLauncher<Array<String>> {
    return fragment.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        val failedToGrant = permissions.entries.any { !it.value }
        if (failedToGrant) {
            whenFail?.invoke()
            return@registerForActivityResult
        }
        whenSuccess?.invoke()
    }
}

fun parseErrorMessage(responseBody: String?): String {
    return try {
        val json = JSONObject(responseBody)
        val message = json.getString("message")
        message
    } catch (e: Exception) {
        "General Error" // Return a default message if parsing fails
    }
}