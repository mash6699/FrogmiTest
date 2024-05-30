package com.mash.frogmi.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.mash.frogmi.domain.model.Store
import com.mash.frogmi.domain.model.StoreResponse
import com.mash.frogmi.domain.model.base.BaseResult
import com.mash.frogmi.domain.usecase.GetStoresUseCase
import com.mash.frogmi.util.Constants.PER_PAGE
import com.mash.frogmi.util.Constants.START_PAGE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getStoresUseCase: GetStoresUseCase) :
    ViewModel() {

    var uiState: HomeUiState by mutableStateOf(HomeUiState.Idle)
        private set

    private val _viewState: MutableStateFlow<HomeUiState> = MutableStateFlow(
        HomeUiState.Idle
    )
    val viewState: StateFlow<HomeUiState> = _viewState

    private var nextLink: String? = null

    private val _items = MutableStateFlow<List<Store>>(emptyList())
    val items: StateFlow<List<Store>> = _items

    init {
        fetchStores()
    }

    fun fetchStoresWithPager() =
        getStoresUseCase.getStoresWithPager(PER_PAGE).flow.cachedIn(viewModelScope)

    fun fetchStores() = viewModelScope.launch {
        delay(1000)
        if (uiState == HomeUiState.Downloading) return@launch
        val checkState = uiState != HomeUiState.Idle && !(uiState is HomeUiState.Error)
        if (checkState && nextLink.isNullOrBlank()) {
            uiState = HomeUiState.Finish
            return@launch
        }
        uiState = checkFirstLoading()
        uiState = when (val result = resolveStore()) {
            is BaseResult.Success -> {
                nextLink = result.data.linkNext
                delay(1000)
                _items.value += result.data.stores
                HomeUiState.Success
            }

            is BaseResult.Error -> {
                HomeUiState.Error(result.exception)
            }
        }
    }

    private fun checkFirstLoading(): HomeUiState =
        if (uiState == HomeUiState.Idle) uiState else HomeUiState.Downloading

    private suspend fun resolveStore(): BaseResult<StoreResponse> {
        return nextLink?.let { getStoresUseCase.getStores(it) } ?: getStoresUseCase.getStores(START_PAGE)
    }

}