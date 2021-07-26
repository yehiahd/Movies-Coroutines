package com.yehia.movies.application

import android.app.Application
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.soloader.SoLoader
import com.yehia.movies.BuildConfig
import com.yehia.movies.util.FlipperNetworkInterceptor
import timber.log.Timber

class MoviesApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(this)) {


            Timber.plant(Timber.DebugTree())
            SoLoader.init(this, false)
            val client = AndroidFlipperClient.getInstance(this)
            client.addPlugin(InspectorFlipperPlugin(this, DescriptorMapping.withDefaults()))
            client.addPlugin(FlipperNetworkInterceptor.getFlipperNetworkPlugin())
            client.start()
        }
    }
}