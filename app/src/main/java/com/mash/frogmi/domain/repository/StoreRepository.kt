package com.mash.frogmi.domain.repository

import com.mash.frogmi.domain.model.StoreResponse
import com.mash.frogmi.domain.model.base.BaseResult

interface StoreRepository {
    suspend fun getStore(page: Int): BaseResult<StoreResponse>
    suspend fun getStoreByUrl(url: String): BaseResult<StoreResponse>
}