package dev.pegasus.kleanbot.data.dataSources.retrofit

import dev.pegasus.kleanbot.data.dataSources.retrofit.api.ApiServiceOpenAI
import dev.pegasus.kleanbot.utilities.ConstantUtils
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by: Sohaib Ahmed
 * Date: 5/7/2025
 *
 * Links:
 * - LinkedIn: https://linkedin.com/in/epegasus
 * - GitHub: https://github.com/epegasus
 */

object RetrofitInstanceOpenAI {

    private const val BASE_URL = "https://api.openai.com/v1/"

    // Auth interceptor to add Bearer token and content-type headers
    private val authInterceptor = Interceptor { chain ->
        val bearerToken = ConstantUtils.OPEN_AI_API_KEY
        val request = chain.request().newBuilder()
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer $bearerToken")
            .build()
        chain.proceed(request)
    }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY // Log EVERYTHING: URL, headers, body, etc.
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addInterceptor(loggingInterceptor)
        .writeTimeout(1, TimeUnit.MINUTES)
        .readTimeout(1, TimeUnit.MINUTES)
        .connectTimeout(1, TimeUnit.MINUTES)
        .build()

    val api: ApiServiceOpenAI by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // plain string response
            .client(client)
            .build()
            .create(ApiServiceOpenAI::class.java)
    }
}