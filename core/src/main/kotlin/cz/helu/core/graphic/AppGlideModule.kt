package com.cinnamon.core.media.graphic

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Parcel
import android.os.Parcelable
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.Options
import com.bumptech.glide.load.ResourceDecoder
import com.bumptech.glide.load.engine.Resource
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.resource.SimpleResource
import com.bumptech.glide.load.resource.transcode.ResourceTranscoder
import com.bumptech.glide.module.AppGlideModule
import java.io.File
import java.io.IOException

@GlideModule
class AppGlideModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        val memoryCacheSizeBytes = 1024 * 1024 * 50 // 50MB
        builder.setMemoryCache(LruResourceCache(memoryCacheSizeBytes.toLong()))
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        registry.prepend(File::class.java, BitmapFactory.Options::class.java, BitmapSizeDecoder())
        registry.register(
            BitmapFactory.Options::class.java,
            GlideImageSize::class.java,
            OptionsSizeResourceTranscoder()
        )
    }
}

class BitmapSizeDecoder : ResourceDecoder<File, BitmapFactory.Options> {
    @Throws(IOException::class)
    override fun handles(file: File, options: Options): Boolean {
        return true
    }

    override fun decode(file: File, width: Int, height: Int, options: Options): Resource<BitmapFactory.Options>? {
        val bmOptions: BitmapFactory.Options = BitmapFactory.Options()
        bmOptions.inJustDecodeBounds = true
        BitmapFactory.decodeFile(file.absolutePath, bmOptions)
        return SimpleResource(bmOptions)
    }
}

class OptionsSizeResourceTranscoder : ResourceTranscoder<BitmapFactory.Options, GlideImageSize> {
    override fun transcode(resource: Resource<BitmapFactory.Options>, options: Options): Resource<GlideImageSize> {
        val bmOptions = resource.get()
        val size = GlideImageSize(bmOptions.outWidth, bmOptions.outHeight)
        return SimpleResource(size)
    }
}

data class GlideImageSize(val width: Int, val height: Int) : Parcelable {
    constructor(source: Parcel) : this(
        source.readInt(),
        source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(width)
        writeInt(height)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<GlideImageSize> = object : Parcelable.Creator<GlideImageSize> {
            override fun createFromParcel(source: Parcel): GlideImageSize = GlideImageSize(source)
            override fun newArray(size: Int): Array<GlideImageSize?> = arrayOfNulls(size)
        }
    }
}
