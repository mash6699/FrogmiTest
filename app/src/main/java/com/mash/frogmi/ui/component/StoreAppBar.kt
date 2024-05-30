package com.mash.frogmi.ui.component

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mash.frogmi.R
import com.mash.frogmi.ui.theme.FrogmiTestTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White
            )
        },
        modifier = modifier,
        colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(apiLevel = 33)
@Composable
fun StoreTopAppBarPreview() {
    FrogmiTestTheme {
       StoreTopAppBar(TopAppBarDefaults.enterAlwaysScrollBehavior())
    }
}