package com.mash.frogmi.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mash.frogmi.R
import com.mash.frogmi.domain.model.Store
import com.mash.frogmi.ui.component.AnimateLoaderComponent
import com.mash.frogmi.ui.component.CircularIndicator
import com.mash.frogmi.ui.component.ErrorApiExceptionScreen
import com.mash.frogmi.ui.component.ItemRowStoreComponent
import com.mash.frogmi.ui.theme.FrogmiTestTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged


@Composable
fun HomeScreen(
    uiState: HomeUiState,
    items: List<Store>,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {

    val listState = rememberLazyListState()
    var firstVisibleItemIndex by remember { mutableStateOf(0) }
    var firstVisibleItemScrollOffset by remember { mutableStateOf(0) }

    LaunchedEffect(items) {
        listState.scrollToItem(firstVisibleItemIndex, firstVisibleItemScrollOffset)
    }

    HomeScrollLazyColumn(
        items = items,
        uiState = uiState,
        modifier = modifier,
        contentPadding = contentPadding,
        onLoadMore = {
            firstVisibleItemIndex = listState.firstVisibleItemIndex
            firstVisibleItemScrollOffset = listState.firstVisibleItemScrollOffset
            viewModel.fetchStores()
        }
    )
}

@Composable
fun HomeScrollLazyColumn(
    items: List<Store>,
    uiState: HomeUiState,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onLoadMore: () -> Unit,
) {

    val listState = rememberLazyListState()
    val loadMore by rememberUpdatedState(onLoadMore)

    LaunchedEffect(listState, items) {
        snapshotFlow { listState.layoutInfo }
            .distinctUntilChanged()
            .collectLatest { layoutInfo ->
                if (layoutInfo.visibleItemsInfo.isNotEmpty()) {
                    val lastVisibleItem = layoutInfo.visibleItemsInfo.last()
                    if (lastVisibleItem.index == items.size - 1) {
                        loadMore()
                    }
                }
            }
    }

    Box(Modifier.fillMaxSize()) {
        LazyColumn(
            state = listState,
            modifier = modifier,
            contentPadding = contentPadding,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            items(items) { item ->
               ItemRowStoreComponent(modifier, item)
            }

            item {
                when (uiState) {
                    is HomeUiState.Idle -> {
                        Column(
                            modifier = Modifier.fillParentMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            AnimateLoaderComponent()
                        }
                    }

                    is HomeUiState.Downloading -> {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            CircularIndicator()
                        }
                    }

                    is HomeUiState.Error -> {
                        ErrorApiExceptionScreen(
                            uiState.apiException,
                            { loadMore() },
                            modifier.fillMaxWidth()
                        )
                    }

                    is HomeUiState.Finish -> {
                        Box(
                            modifier = modifier.padding(8.dp)
                        ) {
                            Card {
                                Column(
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center,
                                ) {
                                    Image(
                                        modifier = modifier.size(65.dp),
                                        painter = painterResource(id = R.drawable.ic_black_cat),
                                        contentDescription = "Image not found",
                                        contentScale = ContentScale.Fit,
                                    )
                                    Text(stringResource(id = R.string.there_are_no_more_records))
                                }
                            }
                        }
                    }

                    else -> {}

                }
            }
        }
    }
}

@Preview(apiLevel = 33)
@Composable
fun HomeScreenPreview() {
    FrogmiTestTheme {
        val mockStoreData = List(10) { Store("Store $it", "S001", false, "Address") }
        HomeScrollLazyColumn(
            uiState = HomeUiState.Success,
            items = mockStoreData,
            onLoadMore = {})
    }
}