package com.mash.frogmi.network

import com.mash.frogmi.domain.model.api.APIStoreResponse
import com.mash.frogmi.util.Constants.PER_PAGE
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface APIService {

    @GET("api/v3/stores")
    suspend fun getStore(
        @Query("page") currentPage: Int,
        @Query("per_page") pageSize: Int = PER_PAGE
    ): APIStoreResponse

    @GET
    suspend fun getStoreByUrl(@Url url: String): APIStoreResponse
}