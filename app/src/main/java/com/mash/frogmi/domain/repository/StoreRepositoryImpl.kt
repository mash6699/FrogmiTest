package com.mash.frogmi.domain.repository

import com.mash.frogmi.R
import com.mash.frogmi.StringResourceProvider
import com.mash.frogmi.domain.mapper.toStore
import com.mash.frogmi.domain.model.StoreResponse
import com.mash.frogmi.domain.model.base.BaseResult
import com.mash.frogmi.domain.model.ex.ApiException
import com.mash.frogmi.network.APIService
import com.mash.frogmi.util.NetworkStatus
import javax.inject.Inject


class StoreRepositoryImpl @Inject constructor(
    private val apiService: APIService,
    private val networkStatus: NetworkStatus,
    private val stringResourceProvider: StringResourceProvider
) : StoreRepository {

    override suspend fun getStore(page: Int): BaseResult<StoreResponse> {
        val connectionError = stringResourceProvider.getString(R.string.network_error)
        return if (networkStatus.isNetworkAvailable()) {
            try {
                val response = apiService.getStore(page)
                val next = response.links.next
                val storesResponse = StoreResponse(
                    stores = response.data.map { it.toStore() },
                    linkNext = next)
                BaseResult.Success(storesResponse)
            } catch (e: ApiException) {
                BaseResult.Error(e)
            }
        } else {
            BaseResult.Error(ApiException(0, connectionError))
        }
    }

    override suspend fun getStoreByUrl(url: String): BaseResult<StoreResponse> {
        val connectionError = stringResourceProvider.getString(R.string.network_error)
        return if (networkStatus.isNetworkAvailable()) {
            try {
                val response = apiService.getStoreByUrl(url)
                val next = response.links.next
                val storesResponse = StoreResponse(
                    stores = response.data.map { it.toStore() },
                    linkNext = next)
                BaseResult.Success(storesResponse)
            } catch (e: ApiException) {
                BaseResult.Error(e)
            }
        } else {
            BaseResult.Error(ApiException(0, connectionError))
        }
    }

}