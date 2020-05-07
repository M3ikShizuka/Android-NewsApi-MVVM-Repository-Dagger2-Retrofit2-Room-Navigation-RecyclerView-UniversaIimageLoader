package com.m3ikshizuka.worldnews.common

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.m3ikshizuka.worldnews.LOG_NETOWRK_REST_ACTION
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import com.nostra13.universalimageloader.core.assist.FailReason
import com.nostra13.universalimageloader.core.assist.ImageScaleType
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener as ImageLoadingListener1

class LoadImage(context: Context) {
    private val imageLoader: ImageLoader
    private val options: DisplayImageOptions

    companion object {
        private lateinit var instance: LoadImage

        fun getInstance(): LoadImage {
            return instance
        }
    }

    init {
        instance = this
        // Create global configuration and initialize ImageLoader with this config
        val config: ImageLoaderConfiguration = ImageLoaderConfiguration.Builder(context)
        .build();
        ImageLoader.getInstance().init(config);
        this.imageLoader = ImageLoader.getInstance()
        this.options = DisplayImageOptions.Builder()
            .resetViewBeforeLoading(true)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .considerExifParams(true)
            .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
//            .bitmapConfig(Bitmap.Config.RGB_565)
//            .displayer(RoundedBitmapDisplayer(20))
            .displayer(SimpleBitmapDisplayer())
            .build();
    }

    fun loadImage(url: String, imageView: ImageView, postFunc: () -> Unit) {
        this.imageLoader.displayImage(url, imageView, this.options, object : ImageLoadingListener1 {
            override fun onLoadingComplete(imageUri: String?, view: View?, loadedImage: Bitmap?) {
                Log.d(
                    LOG_NETOWRK_REST_ACTION,
                    "[INFO] LoadImage.loadImage() callback onLoadingComplete(imageUri: $imageUri)"
                )
                postFunc()
            }

            override fun onLoadingStarted(imageUri: String?, view: View?) {
                Log.d(
                    LOG_NETOWRK_REST_ACTION,
                    "[INFO] LoadImage.loadImage() callback onLoadingStarted(imageUri: $imageUri)"
                )
            }

            override fun onLoadingCancelled(imageUri: String?, view: View?) {
                Log.d(
                    LOG_NETOWRK_REST_ACTION,
                    "[INFO] LoadImage.loadImage() callback onLoadingCancelled(imageUri: $imageUri)"
                )
                postFunc()
            }

            override fun onLoadingFailed(imageUri: String?, view: View?, failReason: FailReason?) {
                Log.d(
                    LOG_NETOWRK_REST_ACTION,
                    "[INFO] LoadImage.loadImage() callback onLoadingFailed(imageUri: $imageUri)"
                )
                postFunc()
            }

        }
        ) { imageUri, view, current, total ->
            Log.d(
                LOG_NETOWRK_REST_ACTION,
                "[INFO] LoadImage.loadImage() callback onProgressUpdate(imageUri: $imageUri $current/$total)"
            )
        }
    }
}