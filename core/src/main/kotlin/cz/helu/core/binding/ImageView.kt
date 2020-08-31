package cz.helu.core.binding

import android.annotation.SuppressLint
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.cinnamon.core.media.graphic.GlideApp
import com.cinnamon.core.media.graphic.GlideRequest
import cz.helu.core.CoreConfig
import cz.helu.core.R
import cz.helu.core.extension.isAvailable
import cz.helu.core.graphic.BlurTransformation
import timber.log.Timber

@BindingAdapter("src")
@Suppress("TooGenericExceptionCaught")
fun ImageView.src(@DrawableRes drawableRes: Int?) {
    if (drawableRes != null && drawableRes != 0) {
        try {
            setImageDrawable(ContextCompat.getDrawable(context, drawableRes))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

@SuppressLint("CheckResult")
@Suppress("LongParameterList", "ComplexMethod")
@BindingAdapter(
    value = ["imageUrl", "imageCircular", "imageBlurred", "roundedCorners", "withTransition", "asGif", "imagePlaceholder", "imageError", "withErrorBackground", "isBlackAndWhite"],
    requireAll = false
)
fun ImageView.loadImage(
    url: String?,
    circular: Boolean = false,
    blurred: Boolean = false,
    roundedCorners: Float? = null,
    withTransition: Boolean = true,
    asGif: Boolean = false,
    placeholder: Drawable? = null,
    error: Drawable? = null,
    withErrorBackground: Boolean = false,
    isBlackAndWhite: Boolean = false
) {
    val imageView = this

    if (url.isNullOrEmpty()) {
        if (error != null)
            imageView.setImageDrawable(error)
        return
    }

    Timber.v("[GLIDE] Load URL: $url")

    // BlackAndWhite check
    setGlideImageViewBlackAndWhite(imageView, isBlackAndWhite)

    // GIF Check
    if (asGif)
        imageView.scaleType = ImageView.ScaleType.CENTER

    // Init Glide load
    if (!imageView.context.isAvailable())
        return

    val image = GlideApp.with(imageView.context).load(url)
    image.apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))

    // Transition
    if (withTransition)
        image.transition(DrawableTransitionOptions().crossFade())
    else
        image.transition(DrawableTransitionOptions().dontTransition())

    // Rounded corners
    if (roundedCorners != null)
        image.apply(RequestOptions().transform(CenterCrop(), RoundedCorners(roundedCorners.toInt())))

    // Placeholder
    if (placeholder != null)
        image.apply(RequestOptions.placeholderOf(placeholder))

    // Error or Null Placeholder
    if (error != null)
        image.apply(RequestOptions.errorOf(error))

    // Circular Image
    if (circular)
        image.apply(RequestOptions.circleCropTransform())

    // Apply blur
    if (blurred)
        image.apply(RequestOptions.bitmapTransform(BlurTransformation(5, 3)))

    setupGlideRequestListener(imageView, image, url, asGif, roundedCorners, withErrorBackground)

    // Load image
    image.into(imageView)

    // Animated Placeholder check - must be done after loading!
    if (placeholder != null && placeholder is AnimationDrawable)
        placeholder.start()
}

@Suppress("LongParameterList")
private fun setupGlideRequestListener(
    imageView: ImageView,
    image: GlideRequest<Drawable>,
    url: String,
    asGif: Boolean,
    roundedCorners: Float?,
    withErrorBackground: Boolean
) {
    image.listener(object : RequestListener<Drawable> {
        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            // GIF Check
            if (asGif) {
                imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            }

            if (CoreConfig.DEBUG_BUILD_TYPE) {
                Timber.v("[GLIDE] Successfully loaded: $url with resolution: ${resource?.intrinsicWidth}x${resource?.intrinsicHeight}")
            }

            return false
        }

        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean
        ): Boolean {
            if (CoreConfig.DEBUG_BUILD_TYPE) {
                Timber.e("[GLIDE] Error loading: $url: ${e?.causes}")
            }

            if (withErrorBackground) {
                val shape = GradientDrawable().apply {
//                    setColor(ContextCompat.getColor(imageView.context, R.color.color_primary))
                }
                if (roundedCorners != null) {
                    shape.cornerRadius = roundedCorners
                }
                imageView.background = shape
                imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
            }
            return false
        }
    })
}

private fun setGlideImageViewBlackAndWhite(imageView: ImageView, isBlackAndWhite: Boolean) {
    if (isBlackAndWhite) {
        val c = 1.2f
        val b = -80f
        val cm = ColorMatrix(
            floatArrayOf(
                c, 0f, 0f, 0f,
                b, 0f, c, 0f,
                0f, b, 0f, 0f,
                c, 0f, b, 0f,
                0f, 0f, 1f, 0f
            )
        )

        val matrix = ColorMatrix()
        matrix.setSaturation(0f)
        matrix.postConcat(cm)
        imageView.colorFilter = ColorMatrixColorFilter(matrix)
        imageView.alpha = 1f
    } else {
        imageView.clearColorFilter()
    }
}
