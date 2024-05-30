package com.mash.frogmi.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mash.frogmi.R
import com.mash.frogmi.domain.model.Store


@Composable
fun ItemRowStoreComponent(
    modifier: Modifier = Modifier,
    store: Store
) {
    val storeIcon = if (store.active) R.drawable.ic_store_open else R.drawable.ic_store_timings
    Box(modifier = modifier
        .background(MaterialTheme.colorScheme.background)
        .padding(8.dp)) {
        Card(
            shape = RoundedCornerShape(6.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            )) {
            ListItem(
                colors = ListItemDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                ),
                overlineContent = {  },
                headlineContent = { Text(store.name) },
                supportingContent = { Text(store.fullAddress) },
                trailingContent = { Text(store.code) },
                leadingContent = {
                    Icon(
                        painter = painterResource(id = storeIcon),
                        contentDescription = "Store icon",
                        modifier = Modifier
                            .width(25.dp)
                            .height(25.dp)
                    )
                },
            )
        }
    }
}