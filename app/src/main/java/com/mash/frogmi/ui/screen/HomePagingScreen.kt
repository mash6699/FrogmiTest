package com.mash.frogmi.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.mash.frogmi.domain.model.Store
import com.mash.frogmi.domain.model.api.AttributeResponse
import com.mash.frogmi.domain.model.api.Coordinates
import com.mash.frogmi.domain.model.api.DataResponse
import com.mash.frogmi.domain.model.api.LinkData
import com.mash.frogmi.domain.model.api.RelationData
import com.mash.frogmi.domain.model.api.RelationShipResponse
import com.mash.frogmi.ui.component.AnimateLoaderComponent
import com.mash.frogmi.ui.component.CircularIndicator
import com.mash.frogmi.ui.component.ErrorScreen
import com.mash.frogmi.ui.component.ItemRowStoreComponent
import com.mash.frogmi.ui.theme.FrogmiTestTheme
import kotlinx.coroutines.flow.flowOf

@Composable
fun HomePagingScreen(
    modifier: Modifier = Modifier,
    lazyPagingItems: LazyPagingItems<Store>,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        items(lazyPagingItems.itemCount) { index ->
            val item = lazyPagingItems[index]
            item?.let { ItemRowStoreComponent(modifier, it) }
        }

        lazyPagingItems.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item {
                        Column(
                            modifier = Modifier.fillParentMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            AnimateLoaderComponent()
                        }

                    }
                }

                loadState.append is LoadState.Loading -> {
                    item {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            CircularIndicator()
                        }
                    }
                }

                loadState.refresh is LoadState.Error -> {
                    val e = lazyPagingItems.loadState.refresh as LoadState.Error
                    item {
                        ErrorScreen(
                            e.error.localizedMessage ?: "",
                            { retry() },
                            modifier.fillMaxWidth()
                        )
                    }
                }

                loadState.append is LoadState.Error -> {
                    val e = lazyPagingItems.loadState.append as LoadState.Error
                    item {
                        ErrorScreen(
                            e.error.localizedMessage ?: "",
                            { retry() },
                            modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

@Preview(apiLevel = 33)
@Composable
fun HomeScreenPagingPreview() {
    FrogmiTestTheme {
        val mockStoreData = List(10) { Store("Store $it", "S001", false, "Address") }
        val mockPagingItem = flowOf(PagingData.from(mockStoreData)).collectAsLazyPagingItems()
        HomePagingScreen(lazyPagingItems = mockPagingItem)
    }
}