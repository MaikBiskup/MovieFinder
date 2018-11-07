package de.vectron_systems_ag.gethappy.consumer.rest.retrofit.Interceptors

import com.example.maikbiskup.moviefinder.helper.Config
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class InterceptorApiToken : Interceptor {

    private val API_TOKEN = Config.API_TOKEN
    private val API_KEY = "apikey"

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val originalRequest = chain.request()

        val newUrl = originalRequest.url().newBuilder().addQueryParameter(API_KEY, API_TOKEN).build()
        val newRequest = originalRequest.newBuilder().url(newUrl).build()

        return chain.proceed(newRequest)
    }
}
