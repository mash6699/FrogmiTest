package com.mash.frogmi.test

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mash.frogmi.StringResourceProvider
import com.mash.frogmi.domain.model.ex.ApiException
import com.mash.frogmi.domain.model.base.BaseResult
import com.mash.frogmi.domain.repository.StoreRepositoryImpl
import com.mash.frogmi.domain.model.api.APIStoreResponse
import com.mash.frogmi.domain.model.api.LinkResponse
import com.mash.frogmi.domain.model.api.MetaResponse
import com.mash.frogmi.domain.model.api.PaginationData
import com.mash.frogmi.network.APIService
import com.mash.frogmi.util.NetworkStatus
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class StoreRepositoryImpTest {

    @get:Rule
    //var mockitoRule: MockitoRule = MockitoJUnit.rule()
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @RelaxedMockK
    private lateinit var repository: StoreRepositoryImpl

    private val apiService: APIService = mock()
    private val networkStatus: NetworkStatus = mock()
    private val stringResourceProvider: StringResourceProvider = mock()

    @Before
    fun setUp() {
        //MockKAnnotations.init(this, relaxed = true)
        MockKAnnotations.init(this)
        /*apiService = mock(APIService::class.java)
        networkStatus = mock(NetworkStatus::class.java)
        stringResourceProvider = mock(StringResourceProvider::class.java)*/
        repository = StoreRepositoryImpl(apiService, networkStatus, stringResourceProvider)
    }

    @Test
    fun `getStores returns Success when API call is successful`() = runBlocking {
        //given
        val storeResponseMock: APIStoreResponse = mockk(relaxed = true)
        every { networkStatus.isNetworkAvailable()} returns (true)
        coEvery { apiService.getStore(1) } returns (storeResponseMock)

        //given
        val result = repository.getStore(1)

        //then
        assert(result is BaseResult.Success && result.data == storeResponseMock)
    }

    @Test
    fun `getStores returns Error when API call fails`() = runTest{
        val mockException = BaseResult.Error(ApiException(500, "Internal Server Error"))
        whenever(networkStatus.isNetworkAvailable()).thenReturn(false)
        whenever(repository.getStore(1)).thenReturn(mockException)
        val result = repository.getStore(1)
        println("RESULT ----> $result")
        assert(result is BaseResult.Error )//&& result.exception.message == mockException.exception.message
    }

}