package com.example.maikbiskup.moviefinder.model.data_access.remote

import android.util.Log
import com.example.maikbiskup.moviefinder.BuildConfig
import com.example.maikbiskup.moviefinder.helper.Config
import com.google.gson.GsonBuilder
import de.vectron_systems_ag.gethappy.consumer.rest.retrofit.Interceptors.InterceptorApiToken
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

abstract class BaseController {

    companion object {
        val LOG_KEY = "HttpLogging"
    }

    protected var API_URL = Config.API_URL

    // Is for the Interceptors
    private var client: OkHttpClient? = null
    // Builds Client with Interceptors
    private val builder = OkHttpClient().newBuilder()

    protected fun getRetroFit(ENDPOINT: String): Retrofit {
        val gson = GsonBuilder()
                .serializeNulls()
                .create()

        //has to be called before the retrofit gets created
        addInterceptors()

        return Retrofit.Builder()
                .baseUrl(API_URL + ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client!!)
                .build()
    }

    private fun generateLogger(): HttpLoggingInterceptor {
        // HttpLoggingInterceptro for logging outgoing requests and logging incoming responses
        val loggingInterceptor = HttpLoggingInterceptor { message -> Log.d(LOG_KEY, "OkHttp: $message") }
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return loggingInterceptor
    }

    private fun addInterceptors() {
        builder.addInterceptor(InterceptorApiToken())

        // Insert new Interceptors here
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(generateLogger())
        }

        client = builder.build()
    }
}
