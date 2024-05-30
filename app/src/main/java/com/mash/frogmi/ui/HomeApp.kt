package com.mash.frogmi.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.mash.frogmi.ui.component.StoreTopAppBar
import com.mash.frogmi.ui.screen.HomePagingScreen
import com.mash.frogmi.ui.screen.HomeScreen
import com.mash.frogmi.ui.screen.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeApp(viewModel: HomeViewModel = hiltViewModel()) {
    val items by viewModel.items.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val lazyPagingItems = viewModel.fetchStoresWithPager().collectAsLazyPagingItems()

    Scaffold(topBar = { StoreTopAppBar(scrollBehavior = scrollBehavior)}) {
        val tabs = listOf("Paging", "Link next")
        var tabIndex by remember { mutableStateOf(0) }
        Column(modifier = Modifier.padding(it)) {
            TabRow(selectedTabIndex = tabIndex, contentColor = MaterialTheme.colorScheme.primary) {
                tabs.forEachIndexed { index, title ->
                    Tab(text = { Text(title) },
                        selected = tabIndex == index,
                        onClick = { tabIndex = index }
                    )
                }
            }
            when (tabIndex) {
                0 -> HomePagingScreen(lazyPagingItems = lazyPagingItems)
                1 -> HomeScreen(items = items, uiState = viewModel.uiState, viewModel = viewModel)
            }
        }
    }
}