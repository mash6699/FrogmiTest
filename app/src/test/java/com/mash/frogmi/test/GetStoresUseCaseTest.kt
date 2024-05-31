package com.mash.frogmi.test

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.mash.frogmi.domain.model.StoreResponse
import com.mash.frogmi.domain.repository.StoreRepository
import com.mash.frogmi.domain.model.api.APIStoreResponse
import com.mash.frogmi.domain.model.api.AttributeResponse
import com.mash.frogmi.domain.model.api.DataResponse
import com.mash.frogmi.domain.model.api.LinkResponse
import com.mash.frogmi.domain.model.api.MetaResponse
import com.mash.frogmi.domain.model.api.PaginationData
import com.mash.frogmi.domain.model.base.BaseResult
import com.mash.frogmi.domain.usecase.GetStoresUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.anyString
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class GetStoresUseCaseTest {

    @RelaxedMockK
    private lateinit var repository: StoreRepository
    private lateinit var useCase: GetStoresUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = GetStoresUseCase(repository)
    }

    @Test
    fun `getStores by id returns items from repository`() = runBlocking {
        //given
        val mockDataResponse: StoreResponse = mockk(relaxed = true)
        coEvery { repository.getStore(any()) } returns (BaseResult.Success(mockDataResponse))

        //when
        val result = useCase.getStores(1)

        //then
        coVerify(exactly = 1) { repository.getStore(any()) }
        assert(result is BaseResult.Success && result.data == mockDataResponse)
    }

    @Test
    fun `getStores by url returns items from repository`() = runBlocking {
        //given
        val mockDataResponse: StoreResponse = mockk(relaxed = true)
        coEvery { repository.getStoreByUrl(any()) } returns (BaseResult.Success(mockDataResponse))

        //when
        val result = useCase.getStores("url")

        //then
        coVerify(exactly = 1) { repository.getStoreByUrl(any()) }
        assert(result is BaseResult.Success && result.data == mockDataResponse)
    }
}