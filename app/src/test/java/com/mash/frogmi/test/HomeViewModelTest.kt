package com.mash.frogmi.test

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mash.frogmi.domain.model.api.APIStoreResponse
import com.mash.frogmi.domain.model.api.LinkResponse
import com.mash.frogmi.domain.model.api.MetaResponse
import com.mash.frogmi.domain.model.api.PaginationData
import com.mash.frogmi.domain.model.base.BaseResult
import com.mash.frogmi.domain.model.ex.ApiException
import com.mash.frogmi.domain.repository.StoreRepository
import com.mash.frogmi.ui.screen.HomeViewModel
import com.mash.frogmi.domain.usecase.GetStoresUseCase
import com.mash.frogmi.ui.screen.HomeUiState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.unmockkAll
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @RelaxedMockK
    lateinit var getStoresUseCase: GetStoresUseCase

    @RelaxedMockK
    private lateinit var repository: StoreRepository

    private lateinit var viewModel: HomeViewModel

    private val dispatcher by lazy { StandardTestDispatcher() }

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        Dispatchers.setMain(dispatcher)
        getStoresUseCase = mock(GetStoresUseCase::class.java)
        viewModel = spyk(HomeViewModel(getStoresUseCase))
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `when fetchStores is successful`() = runBlocking {
        //given
        val storeResponseMock: APIStoreResponse = mockk(relaxed = true)
        coEvery { repository.getStore(1) } returns
                BaseResult.Success(storeResponseMock)

        //when
        viewModel.getStore()

        //then
        assertFalse(viewModel.viewState.value is HomeUiState.Error)
    }

    @Test
    fun `when fetchStores is unsuccessful`() = runBlocking {
        //given
        coEvery { repository.getStore(1)} returns
                BaseResult.Error(ApiException(500, "Internal Server Error"))

        //when
        viewModel.getStore()

        //then
        //assert(viewModel.uiState .value == "Error: Internal Server Error")
        assertFalse(viewModel.viewState.value is HomeUiState.Success)
    }
}