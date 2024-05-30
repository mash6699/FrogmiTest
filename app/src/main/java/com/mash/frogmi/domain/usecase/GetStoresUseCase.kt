package com.mash.frogmi.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mash.frogmi.domain.model.Store
import com.mash.frogmi.domain.model.StoreResponse
import com.mash.frogmi.domain.model.base.BaseResult
import com.mash.frogmi.domain.repository.StoreRepository
import javax.inject.Inject

class GetStoresUseCase @Inject constructor(private val repository: StoreRepository) {

    suspend fun getStores(page: Int): BaseResult<StoreResponse> {
        return repository.getStore(page)
    }

    suspend fun getStores(url: String): BaseResult<StoreResponse> {
        return repository.getStoreByUrl(url)
    }

    fun getStoresWithPager(page: Int): Pager<Int, Store> {
        return Pager(
            config = PagingConfig(pageSize = page, enablePlaceholders = true),
            pagingSourceFactory = { getItemsPagingSource() }
        )
    }

    private fun getItemsPagingSource(): PagingSource<Int, Store> {
        return object : PagingSource<Int, Store>() {
            override fun getRefreshKey(state: PagingState<Int, Store>): Int? =
                state.anchorPosition

            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Store> {
                return try {
                    val page = params.key ?: 1
                    return when (val result = repository.getStore(page)) {
                        is BaseResult.Success -> {
                            LoadResult.Page(
                                data = result.data.stores,
                                prevKey = if (page == 1) null else page - 1,
                                nextKey = if (result.data.stores.isEmpty()) null else page + 1
                            )
                        }

                        is BaseResult.Error -> {
                            return LoadResult.Error(Throwable(result.exception.message))
                        }
                    }
                } catch (exception: Exception) {
                    return LoadResult.Error(exception)
                }
            }
        }
    }
}