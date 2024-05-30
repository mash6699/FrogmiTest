package com.mash.frogmi.test

import androidx.paging.PagingData
import com.mash.frogmi.domain.repository.StoreRepository
import com.mash.frogmi.domain.model.api.APIStoreResponse
import com.mash.frogmi.domain.model.api.AttributeResponse
import com.mash.frogmi.domain.model.api.DataResponse
import com.mash.frogmi.domain.model.api.LinkResponse
import com.mash.frogmi.domain.model.api.MetaResponse
import com.mash.frogmi.domain.model.api.PaginationData
import com.mash.frogmi.domain.usecase.GetStoresUseCase
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class GetStoresUseCaseTest {

    @RelaxedMockK
    private lateinit var repository: StoreRepository
    @RelaxedMockK
    private lateinit var useCase: GetStoresUseCase

    @Before
    fun setUp() {
        //MockitoAnnotations.openMocks(this)
        MockKAnnotations.init(this, relaxed = true)
        //repository = mock(StoreRepository::class.java)
        useCase = GetStoresUseCase(repository)
    }

    @Test
    fun `invoke returns items from repository`() = runBlocking {
        val mockDataResponse: DataResponse = mockk(relaxed = true)
        //`when`(repository.getStore()).thenReturn(BaseResult.Success(mockData))

        val mockResponse = PagingData.from(listOf(mockDataResponse))

        //whenever(useCase.getItemsPager(1)).thenReturn(flowOf(mockResponse) )
        //whenever(repository.getStore(20)  ).thenReturn(flowOf(mockResponse))

        useCase.getItemsPager(1)

       // assert(result is BaseResult.Success && result.data == mockData)
    }
}