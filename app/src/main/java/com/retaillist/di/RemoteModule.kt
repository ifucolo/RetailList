package com.retaillist.di

import android.os.Build
import com.retaillist.data.remote.api.PRODUCT_BASE_URL
import com.retaillist.data.remote.api.PRODUCT_REVIEW_BASE_URL
import com.retaillist.data.remote.api.ProductReviewApi
import com.retaillist.data.remote.api.ProductsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RemoteModule {

    @Singleton
    @Provides
    fun provideOkHttpClient() : OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor {  chain ->
                val request: Request = chain.request()
                val builder = request.newBuilder()
                    .addHeader("Accept","application/json")
//                    .addHeader("Connection","close")
//                    .addHeader("Cache-Control","no-cache")
                    .build()

                chain.proceed(builder)
            }
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

    @Provides
    @Singleton
    fun provideRetailApi(okHttpClient: OkHttpClient) = createWebService<ProductsApi>(
        okHttpClient,
        PRODUCT_BASE_URL
    )

    @Provides
    @Singleton
    fun provideReviewApi(okHttpClient: OkHttpClient) = createWebService<ProductReviewApi>(
        okHttpClient,
        PRODUCT_REVIEW_BASE_URL
    )
}

inline fun <reified T> createWebService(okHttpClient: OkHttpClient , url: String): T {
    return Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(url)
        .client(okHttpClient)
        .build()
        .create(T::class.java)
}
