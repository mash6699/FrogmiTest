package com.mash.frogmi.test

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mash.frogmi.domain.model.StoreResponse
import com.mash.frogmi.domain.model.base.BaseResult
import com.mash.frogmi.domain.model.ex.ApiException
import com.mash.frogmi.domain.repository.StoreRepository
import com.mash.frogmi.domain.usecase.GetStoresUseCase
import com.mash.frogmi.ui.screen.HomeUiState
import com.mash.frogmi.ui.screen.HomeViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.unmockkAll
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val dispatcher by lazy { StandardTestDispatcher() }

    @RelaxedMockK
    lateinit var getStoresUseCase: GetStoresUseCase
    @RelaxedMockK
    private lateinit var repository: StoreRepository

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(dispatcher)
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
        coEvery { viewModel.uiState } returns (HomeUiState.Success)
        val storeResponseMock: StoreResponse = mockk(relaxed = true)
        coEvery { repository.getStore(1) } returns
                BaseResult.Success(storeResponseMock)

        //when
        viewModel.fetchStores()

        //then
        assertTrue(viewModel.uiState is HomeUiState.Success)
    }

    @Test
    fun `when fetchStores is unsuccessful`() = runBlocking {
        val mockError = ApiException(500, "Internal Server Error")
        coEvery { viewModel.uiState } returns (HomeUiState.Error(mockError))

        //given
        coEvery { repository.getStore(1)} returns
                BaseResult.Error(mockError)

        //when
        viewModel.fetchStores()

        //then
        assertTrue(viewModel.uiState is HomeUiState.Error)
    }
}
