package com.example.composemovieapp.di

import com.example.composemovieapp.BuildConfig
import com.example.composemovieapp.network.MovieApiService
import com.example.composemovieapp.constants.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @BaseURL
    @Provides
    fun provideBaseUrl(): String = "https://api.themoviedb.org/"

    @BasePosterURL
    @Provides
    fun provideBasePosterUrl(): String = "https://image.tmdb.org/t/p/w185"

    @BaseBackDropURL
    @Provides
    fun provideBaseBackDropUrl(): String = "https://image.tmdb.org/t/p/w780"

    @BaseProfileURL
    @Provides
    fun provideBaseProfileUrl(): String = "https://image.tmdb.org/t/p/w185"

    @BaseYTIMGURL
    @Provides
    fun provideBaseYTIMGUrl(): String = "https://img.youtube.com/vi/"

    @BaseYTWatchURL
    @Provides
    fun provideBaseYTWatchUrl(): String = "https://www.youtube.com/watch?v="


    /**
     * provides [Retrofit] client instance
     */
    @Provides
    @Singleton
    fun getRetrofitInstance(
        @BaseURL baseURL: String,
        okHttpClient: OkHttpClient
    ): MovieApiService {
        return Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(MovieApiService::class.java)
    }

    /**
     * This will return [Interceptor] and it will be called both if the network is
     * available and if the network is not available
     */
    @Provides
    fun provideOnOrOffLineInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            // prevent caching when network is on. For that we use the "networkInterceptor"
            val url = request.url.newBuilder()
                .addQueryParameter("language", "en-US")
                .addQueryParameter("api_key", "fe0dc93631e2af997efd5ebf2b188c44")
                .build()
            val newRequest = request.newBuilder().url(url).build()
            chain.proceed(newRequest)
        }
    }

    /**
     * provide okhttpClient
     */
    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        networkInterceptor: Interceptor
    ): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(networkInterceptor)
            .connectTimeout(AppConstants.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
            .writeTimeout(AppConstants.WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
            .readTimeout(AppConstants.READ_TIMEOUT, TimeUnit.MILLISECONDS)
        if (BuildConfig.DEBUG) okHttpClient.addInterceptor(loggingInterceptor)
        return okHttpClient.build()
    }

    /**
     * provide logging interceptor
     */
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)


    /**
     * Base URL
     */
    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class BaseURL

    /**
     * Base Poster URL
     */
    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class BasePosterURL

    /**
     * Base Back Drop URL
     */
    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class BaseBackDropURL

    /**
     * Base Profile URL
     */
    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class BaseProfileURL

    /**
     * Base YT IMG URL
     */
    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class BaseYTIMGURL

    /**
     * Base YT Watch URL
     */
    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class BaseYTWatchURL

}

