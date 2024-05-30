package com.mash.frogmi.domain.model.interceptor

import com.mash.frogmi.domain.model.ex.ApiException
import okhttp3.Interceptor
import okhttp3.Response

class ErrorInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        if(!response.isSuccessful) {
            val errorBody = response.body.string()
            val errorMessage = "code [${response.code}] \n $errorBody"
            throw ApiException(response.code, errorMessage)
        }
        return response
    }
}