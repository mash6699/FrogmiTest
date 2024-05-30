package com.mash.frogmi.di

import android.content.Context
import com.mash.frogmi.BuildConfig
import com.mash.frogmi.StringResourceProvider
import com.mash.frogmi.StringResourceProviderImpl
import com.mash.frogmi.domain.model.interceptor.ErrorInterceptor
import com.mash.frogmi.domain.model.interceptor.HeaderInterceptor
import com.mash.frogmi.domain.repository.StoreRepositoryImpl
import com.mash.frogmi.domain.repository.StoreRepository
import com.mash.frogmi.network.APIService
import com.mash.frogmi.util.NetworkStatus
import com.mash.frogmi.util.NetworkUtil
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideBaseUrl() = BuildConfig.API_URL

    @Provides
    @Singleton
    fun provideMoshi(): Moshi =
        Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

    @Provides
    @Singleton
    fun provideClient(): OkHttpClient =
        OkHttpClient().newBuilder().apply {
            addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            addInterceptor(HeaderInterceptor())
            addInterceptor(ErrorInterceptor())
        }.readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String, client: OkHttpClient, moshi: Moshi): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): APIService = retrofit.create(APIService::class.java)

    @Provides
    @Singleton
    fun provideNetworkUtil(@ApplicationContext context: Context): NetworkStatus {
        return NetworkUtil(context)
    }

    @Provides
    @Singleton
    fun provideStringResourceProvider(@ApplicationContext context: Context): StringResourceProvider {
        return StringResourceProviderImpl(context.resources)
    }

    @Provides
    @Singleton
    fun provideStoreRepository(
        apiService: APIService,
        networkStatus: NetworkStatus,
        stringResourceProvider: StringResourceProvider
    ): StoreRepository =
        StoreRepositoryImpl(apiService, networkStatus, stringResourceProvider)

}