package com.mash.frogmi.test

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mash.frogmi.util.StringResourceProvider
import com.mash.frogmi.domain.mapper.toStore
import com.mash.frogmi.domain.model.StoreResponse
import com.mash.frogmi.domain.model.api.APIStoreResponse
import com.mash.frogmi.domain.model.base.BaseResult
import com.mash.frogmi.domain.model.ex.ApiException
import com.mash.frogmi.domain.repository.StoreRepositoryImpl
import com.mash.frogmi.network.APIService
import com.mash.frogmi.util.NetworkStatus
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class StoreRepositoryImpTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @RelaxedMockK
    private lateinit var apiService: APIService
    @RelaxedMockK
    private lateinit var networkStatus: NetworkStatus
    @RelaxedMockK
    private lateinit var stringResourceProvider: StringResourceProvider

    private lateinit var repository: StoreRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = spyk(StoreRepositoryImpl(apiService, networkStatus, stringResourceProvider))
    }

    @Test
    fun `getStores returns Success when API call is successful`() = runBlocking {
        //given
        val responseMock: APIStoreResponse = mockk(relaxed = true)
        val storesMock = StoreResponse(stores = responseMock.data.map { it.toStore() }, linkNext = null)
        coEvery { networkStatus.isNetworkAvailable()} returns (true)
        coEvery { apiService.getStore(any()) } returns (responseMock)
        coEvery { repository.getStore(any()) } returns (BaseResult.Success(storesMock))

        //when
        val result = repository.getStore(1)

        //then
        assert(result is BaseResult.Success && result.data == storesMock)
    }

    @Test
    fun `getStores returns Error when API call fails`() = runTest{
        //given
        val mockException = BaseResult.Error(ApiException(500, "Internal Server Error"))
        coEvery {networkStatus.isNetworkAvailable()} returns (false)
        coEvery {repository.getStore(any())} returns (mockException)

        //when
        val result = repository.getStore(1)

        //then
        assert(result is BaseResult.Error && result.exception.message == mockException.exception.message)
    }

}