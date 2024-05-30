package com.mash.frogmi.ui.screen

import com.mash.frogmi.domain.model.ex.ApiException

sealed interface HomeUiState {
    object Idle : HomeUiState
    object Downloading: HomeUiState
    object Success: HomeUiState
    object Finish : HomeUiState
    class Error(val apiException: ApiException) : HomeUiState
}