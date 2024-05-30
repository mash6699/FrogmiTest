package com.mash.frogmi.domain.model.interceptor

import com.mash.frogmi.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder().apply {
            header("Content-Type", "application/vnd.api+json")
            header("Authorization", "Bearer " + BuildConfig.AUTH_BEARER)
            header("X-Company-UUID", BuildConfig.COMP_UUID)
        }.build()
        return chain.proceed(request)
    }

}